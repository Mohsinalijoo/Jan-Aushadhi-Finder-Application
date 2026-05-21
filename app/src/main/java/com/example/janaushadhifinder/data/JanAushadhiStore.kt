package com.example.janaushadhifinder.data

data class JanAushadhiStore(
    val name: String = "",
    val address: String = "",
    val distanceKm: Double = 0.0,
    val isOpen: Boolean = false,
    val phone: String = "",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0
)
