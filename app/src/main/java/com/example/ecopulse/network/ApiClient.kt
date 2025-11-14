package com.example.ecopulse.network // Pachetul lor

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {

    // IP-UL TĂU, STABILIT PRIN ipconfig
    private const val BASE_URL = "http://10.0.10.180:8080/"

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}