package com.example.ecopulse.network // Pachetul lor

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {

    /**
     * Endpoint-ul de citire a istoricului.
     * ATENȚIE: Nu începe cu "/"! Retrofit îl adaugă automat la BASE_URL.
     */
    @GET("api/v1/test-db-save")
    fun getHistory(): Call<List<HistoryItem>>
}