package com.example.ecopulse.network

// JSON-ul pe care îl PRIMEȘTI
data class LoginResponse(
    val message: String,
    val accessToken: String? // Îl facem 'nullable' (opțional) pentru siguranță
)