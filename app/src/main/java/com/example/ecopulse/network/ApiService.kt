package com.example.ecopulse.network // Pachetul lor

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    /**
     * Endpoint-ul de citire a istoricului.
     * ATENȚIE: Nu începe cu "/"! Retrofit îl adaugă automat la BASE_URL.
     */
    @GET("api/v1/history")
    fun getHistory(): Call<List<HistoryItem>>

    @POST("api/v1/auth/signup")
    fun registerNormalUser(@Body request: RegisterNormalUserRequest): Call<RegisterResponse>
}