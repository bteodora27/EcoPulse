package com.example.ecopulse.network // Sau pachetul tău de date

data class RegisterNormalUserRequest(
    val email: String,
    val username: String,
    val passHash: String, // Am folosit numele exact din JSON-ul tău
    val firstName: String,
    val lastName: String,
    val phone: String,
    val birthDate: String? // Îl facem 'nullable' (opțional)
)