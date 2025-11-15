package com.example.ecopulse.network

data class RegisterResponse(
    val message: String,
    val userId: Int? // Sau Long?, depinde ce tip de ID aveți
)