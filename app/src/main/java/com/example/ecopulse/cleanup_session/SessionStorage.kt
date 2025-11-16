package com.example.ecopulse.cleanup_session

import android.content.Context

object SessionStorage {

    fun saveSession(context: Context, sessionId: Long, userId: Long) {
        context.getSharedPreferences("EcoPulsePrefs", Context.MODE_PRIVATE)
            .edit()
            .putLong("SESSION_ID", sessionId)
            .putLong("USER_ID", userId) // Asta e OK, deși e redundant cu LoginActivity
            .apply()
    }

    fun getSession(context: Context): Pair<Long, Long> {
        val prefs = context.getSharedPreferences("EcoPulsePrefs", Context.MODE_PRIVATE)
        val sessionId = prefs.getLong("SESSION_ID", -1L)
        val userId = prefs.getLong("USER_ID", -1L)
        return Pair(sessionId, userId)
    }

    // ▼▼▼ AICI ESTE CORECTURA ▼▼▼
    fun clearSession(context: Context) {
        context.getSharedPreferences("EcoPulsePrefs", Context.MODE_PRIVATE)
            .edit()
            .remove("SESSION_ID") // Șterge DOAR sesiunea de curățenie
            // Am șters: .remove("USER_ID")
            .apply()
    }
    // ▲▲▲ SFÂRȘIT CORECTURĂ ▲▲▲
}