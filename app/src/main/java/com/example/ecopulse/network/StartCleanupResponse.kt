package com.example.ecopulse.network

import com.google.gson.annotations.SerializedName

data class StartCleanupResponse(
    val userId: Long,
    val sessionId: Long
)
