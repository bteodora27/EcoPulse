package com.example.ecopulse.network

// JSON-ul pe care îl TRIMIȚI când începi o curățenie
data class StartCleanupRequest(
    val latitude: Double,
    val longitude: Double,
    val zoneName: String
)