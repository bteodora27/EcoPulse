package com.example.ecopulse.network // Pachetul lor

// Mapează câmpurile din ChatHistory.java (id, question, answer, timestamp)
data class HistoryItem(
    val id: Long,
    val question: String,
    val answer: String,
    val timestamp: String // Retrofit/GSON se ocupă de parsarea string-ului de dată
)