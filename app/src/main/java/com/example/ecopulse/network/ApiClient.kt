package com.example.ecopulse.network

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object ApiClient {

    // IP-ul tău, unde rulează Spring Boot
    private const val BASE_URL = "http://10.0.10.180:8080/"

    // Client custom cu timeout mai mare
    private val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    // Instanța Retrofit
    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Serviciul API pe care îl vom folosi
    val apiService: ApiService = retrofit.create(ApiService::class.java)
}