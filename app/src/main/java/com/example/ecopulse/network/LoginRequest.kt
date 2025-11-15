package com.example.ecopulse.network

// JSON-ul pe care îl TRIMIȚI
data class LoginRequest(
    val email: String,
    val password: String
)