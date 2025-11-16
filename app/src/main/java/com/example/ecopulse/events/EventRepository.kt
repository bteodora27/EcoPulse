package com.example.ecopulse.events
import com.example.ecopulse.R

object EventRepository {

    val events = listOf(
        Event(
            id = 1,
            title = "Curățenie în Pădurea Verde",
            participants = "12/40 participanți",
            dateLocation = "Sâmbătă, 20 Feb • Timișoara",
            imageRes = R.drawable.padure,
            latitude = 45.7666,
            longitude = 21.2550
        ),

        Event(
            id = 2,
            title = "Ecologizare Parcul Rozelor",
            participants = "30/60 participanți",
            dateLocation = "Duminică, 22 Feb • Timișoara",
            imageRes = R.drawable.parc,    // <- imagine locală
            latitude = 45.7505,
            longitude = 21.2303
        ),

        Event(
            id = 3,
            title = "Acțiune la Lacul Surduc",
            participants = "18/40 participanți",
            dateLocation = "Sâmbătă, 27 Feb • Timiș",
            imageRes = R.drawable.lac,    // <- imagine locală
            latitude = 45.7430,
            longitude = 22.1022
        )
    )
}
