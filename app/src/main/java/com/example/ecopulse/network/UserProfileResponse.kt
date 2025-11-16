package com.example.ecopulse.network

// Bazat pe UserProfileDTO
data class UserProfileResponse(
    val userId: Long,
    val email: String,
    val username: String,
    val firstName: String,
    val lastName: String,
    val phone: String?, // Poate fi null
    val standardUserRank: String,
    val totalPoints: Int
)