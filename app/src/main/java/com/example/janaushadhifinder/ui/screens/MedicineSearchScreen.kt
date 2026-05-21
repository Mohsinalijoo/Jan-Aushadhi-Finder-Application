package com.example.janaushadhifinder.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.janaushadhifinder.data.Medicine
import com.example.janaushadhifinder.data.SampleData
import com.example.janaushadhifinder.navigation.Routes
import com.example.janaushadhifinder.ui.components.AppHeader
import com.example.janaushadhifinder.ui.components.BottomDashboardBar
import com.example.janaushadhifinder.ui.components.MedicineCard
import com.example.janaushadhifinder.ui.theme.AppColors
import com.example.janaushadhifinder.utils.FuzzySearch
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MedicineSearchScreen(navController: NavHostController) {
    var query by remember { mutableStateOf("") }
    val medicines = rememberMedicinesFromFirestore()
    val results = FuzzySearch.search(query, medicines)

    Scaffold(
        bottomBar = { BottomDashboardBar(navController, Routes.Search) },
        containerColor = AppColors.Background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(AppColors.Background)
        ) {
            AppHeader(
                title = "Medicine Search",
                subtitle = "Brand to generic translator",
                showBack = true,
                onBack = { navController.popBackStack() }
            )

            Column(Modifier.padding(horizontal = 16.dp)) {
                OutlinedTextField(
                    value = query,
                    onValueChange = { query = it },
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Enter branded medicine name") },
                    leadingIcon = { Icon(Icons.Rounded.Search, contentDescription = null) },
                    singleLine = true
                )

                Spacer(Modifier.height(10.dp))

                AssistChip(
                    onClick = { query = "Pan 40" },
                    label = { Text("Try: Pan 40, Dolo 650, Augmntin") }
                )

                Spacer(Modifier.height(10.dp))
                Text(
                    "${results.size} matching medicines",
                    color = AppColors.TextMuted,
                    fontWeight = FontWeight.SemiBold
                )
            }

            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(results) { medicine ->
                    MedicineCard(medicine)
                }
            }
        }
    }
}

@Composable
private fun rememberMedicinesFromFirestore(): List<Medicine> {
    val context = LocalContext.current
    var medicines by remember { mutableStateOf(SampleData.medicines) }

    DisposableEffect(context) {
        val hasFirebase = runCatching { FirebaseApp.getApps(context).isNotEmpty() }.getOrDefault(false)
        if (!hasFirebase) {
            onDispose { }
        } else {
            val registration = runCatching {
                FirebaseFirestore.getInstance()
                    .collection("medicines")
                    .addSnapshotListener { snapshot, error ->
                        if (error != null) return@addSnapshotListener

                        val firestoreMedicines = snapshot
                            ?.documents
                            ?.mapNotNull { document -> document.toObject(Medicine::class.java) }
                            ?.filter { medicine ->
                                medicine.brandName.isNotBlank() ||
                                    medicine.genericName.isNotBlank() ||
                                    medicine.salt.isNotBlank()
                            }
                            .orEmpty()

                        if (firestoreMedicines.isNotEmpty()) {
                            medicines = firestoreMedicines
                        }
                    }
            }.getOrNull()

            onDispose {
                registration?.remove()
            }
        }
    }

    return medicines
}
