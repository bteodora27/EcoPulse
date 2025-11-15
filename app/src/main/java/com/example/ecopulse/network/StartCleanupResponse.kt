package com.example.ecopulse.network

// JSON-ul pe care îl PRIMEȘTI înapoi
data class StartCleanupResponse(
    val message: String,
    val sessionId: Long // ID-ul sesiunii, pe care îl vom folosi mai târziu
)