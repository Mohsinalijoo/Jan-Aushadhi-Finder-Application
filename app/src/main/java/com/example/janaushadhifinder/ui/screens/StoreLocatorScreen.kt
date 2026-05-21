package com.example.janaushadhifinder.ui.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Directions
import androidx.compose.material.icons.rounded.LocationOn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.janaushadhifinder.data.JanAushadhiStore
import com.example.janaushadhifinder.data.SampleData
import com.example.janaushadhifinder.navigation.Routes
import com.example.janaushadhifinder.ui.components.AppHeader
import com.example.janaushadhifinder.ui.components.BottomDashboardBar
import com.example.janaushadhifinder.ui.theme.AppColors
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun StoreLocatorScreen(navController: NavHostController) {
    val stores = rememberStoresFromFirestore()

    Scaffold(
        bottomBar = { BottomDashboardBar(navController, Routes.Stores) },
        containerColor = AppColors.Background
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding = androidx.compose.foundation.layout.PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                AppHeader(
                    title = "Store Locator",
                    subtitle = "Jan-Aushadhi Kendras near you",
                    showBack = true,
                    onBack = { navController.popBackStack() }
                )
            }

            item {
                StoreMap(stores = stores)
            }

            items(stores) { store ->
                StoreCard(store = store)
            }
        }
    }
}

@Composable
private fun rememberStoresFromFirestore(): List<JanAushadhiStore> {
    val context = LocalContext.current
    var stores by remember { mutableStateOf(SampleData.stores) }

    DisposableEffect(context) {
        val hasFirebase = runCatching { FirebaseApp.getApps(context).isNotEmpty() }.getOrDefault(false)
        if (!hasFirebase) {
            onDispose { }
        } else {
            val registration = runCatching {
                FirebaseFirestore.getInstance()
                    .collection("stores")
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) return@addSnapshotListener

                        val firestoreStores = snapshot
                            ?.documents
                            ?.mapNotNull { document -> document.toObject(JanAushadhiStore::class.java) }
                            ?.filter { store -> store.name.isNotBlank() }
                            .orEmpty()

                        if (firestoreStores.isNotEmpty()) {
                            stores = firestoreStores
                        }
                    }
            }.getOrNull()

            onDispose {
                registration?.remove()
            }
        }
    }

    return stores
}

@Composable
private fun StoreMap(stores: List<JanAushadhiStore>) {
    val mapStores = stores.filter { store -> store.hasMapLocation() }
    val firstStoreLocation = mapStores.firstOrNull()?.let { store ->
        LatLng(store.latitude, store.longitude)
    } ?: LatLng(19.0760, 72.8777)

    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(firstStoreLocation, 11.5f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(260.dp)
            .padding(horizontal = 16.dp)
            .clip(RoundedCornerShape(24.dp))
            .background(Color(0xFFE9F0FF)),
        cameraPositionState = cameraPositionState
    ) {
        mapStores.forEach { store ->
            Marker(
                state = MarkerState(position = LatLng(store.latitude, store.longitude)),
                title = store.name,
                snippet = store.address
            )
        }
    }
}

@Composable
private fun StoreCard(store: JanAushadhiStore) {
    val context = LocalContext.current

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    Icons.Rounded.LocationOn,
                    contentDescription = null,
                    tint = if (store.isOpen) AppColors.HealthyGreen else AppColors.TextMuted,
                    modifier = Modifier.size(34.dp)
                )
                Column(Modifier.padding(start = 12.dp)) {
                    Text(store.name, fontWeight = FontWeight.Bold, fontSize = 17.sp)
                    Text(store.address, color = AppColors.TextMuted)
                    Spacer(Modifier.height(4.dp))
                    Text(
                        "${store.distanceKm} km away | ${if (store.isOpen) "Open Now" else "Closed"}",
                        color = if (store.isOpen) AppColors.HealthyGreen else AppColors.Danger,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            Spacer(Modifier.height(12.dp))

            Button(
                onClick = { openDirections(context, store) },
                enabled = store.hasMapLocation(),
                modifier = Modifier.align(Alignment.End),
                colors = ButtonDefaults.buttonColors(containerColor = AppColors.PrimaryBlue),
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp)
            ) {
                Icon(
                    Icons.Rounded.Directions,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(6.dp))
                Text("Directions")
            }
        }
    }
}

private fun JanAushadhiStore.hasMapLocation(): Boolean {
    return latitude != 0.0 || longitude != 0.0
}

private fun openDirections(context: Context, store: JanAushadhiStore) {
    if (!store.hasMapLocation()) return

    val encodedStoreName = Uri.encode(store.name)
    val navigationUri = Uri.parse("google.navigation:q=${store.latitude},${store.longitude}($encodedStoreName)")
    val mapsIntent = Intent(Intent.ACTION_VIEW, navigationUri).apply {
        setPackage("com.google.android.apps.maps")
    }

    try {
        context.startActivity(mapsIntent)
    } catch (_: ActivityNotFoundException) {
        val browserUri = Uri.parse(
            "https://www.google.com/maps/dir/?api=1&destination=${store.latitude},${store.longitude}&travelmode=driving"
        )
        context.startActivity(Intent(Intent.ACTION_VIEW, browserUri))
    }
}
