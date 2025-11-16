package com.example.ecopulse.network

// JSON-ul pe care îl PRIMEȘTI la finalizarea curățeniei
data class EndCleanupResponse(
    val message: String,
    val pointsGained: Int,
    val bagCount: Int,
    val newTotalPoints: Int,
    val newRank: String
)