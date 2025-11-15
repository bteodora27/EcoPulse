package com.example.ecopulse.network

data class UserActivityItem(
    val eventName: String,
    val date: String,
    val rating: Int?,
    val feedback: String?,
    val role: String
)
