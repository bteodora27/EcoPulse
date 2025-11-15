package com.example.ecopulse.network

data class UserProfile(
    val firstName: String,
    val lastName: String,
    val rank: String,
    val totalEventsJoined: Int,
    val totalPoints: Int
)
