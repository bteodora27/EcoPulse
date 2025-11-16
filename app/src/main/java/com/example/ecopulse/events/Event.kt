package com.example.ecopulse.events

data class Event(
    val id: Int,
    val title: String,
    val participants: String,
    val dateLocation: String,

    // Pentru imagini remote (URL)
    val imageUrl: String? = null,

    // Pentru imagini locale din drawable
    val imageRes: Int? = null,

    val latitude: Double,
    val longitude: Double,
    var joined: Boolean = false
)
