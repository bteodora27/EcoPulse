package com.example.ecopulse.network
data class LoginResponse(
    val message: String,
    val accessToken: String,
    val userId: Long
)
