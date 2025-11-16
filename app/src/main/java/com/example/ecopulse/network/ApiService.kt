package com.example.ecopulse.network // Pachetul lor

import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import okhttp3.RequestBody
import retrofit2.http.Header


interface ApiService {

    /**
     * Endpoint-ul de citire a istoricului.
     * ATENȚIE: Nu începe cu "/"! Retrofit îl adaugă automat la BASE_URL.
     */
    @GET("api/v1/history")
    fun getHistory(): Call<List<HistoryItem>>

    @POST("api/v1/auth/signup")
    fun registerNormalUser(@Body request: RegisterNormalUserRequest): Call<RegisterResponse>

    @POST("api/v1/auth/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("api/v1/auth/signup-organization")
    fun registerOrganization(@Body request: RegisterOrgRequest): Call<RegisterResponse>

    // --- ACEASTA ESTE PENTRU PROFILE FRAGMENT ---
    @GET("api/v1/users/{userId}/profile")
    fun getUserProfile(
        @Path("userId") userId: Long
    ): Call<UserProfile> // Folosește modelul UserProfile

    @GET("api/v1/users/{userId}/activities")
    fun getUserActivities(
        @Path("userId") userId: Long
    ): Call<List<UserActivityItem>>

    @Multipart
    @POST("api/v1/users/{userId}/profile-picture")
    fun uploadProfilePicture(
        @Path("userId") userId: Long,
        @Part file: MultipartBody.Part
    ): Call<ApiResponse>

    @Multipart
    @POST("api/v1/cleaning/start")
    fun startIndividualCleanup(
        @Part("user_id") userId: RequestBody,
        @Part beforePhoto: MultipartBody.Part,
        @Part("latitude") latitude: RequestBody,
        @Part("longitude") longitude: RequestBody
    ): Call<StartCleanupResponse>

    @Multipart
    @POST("api/v1/cleaning/{sessionId}/end")
    fun endCleaningSession(
        @Path("sessionId") sessionId: Long,
        @Part bagsPhoto: MultipartBody.Part,
        @Part afterPhoto: MultipartBody.Part
    ): Call<EndCleanupResponse>

    // --- ▼▼▼ MODIFICARE AICI ▼▼▼ ---
    // AM REDENUMIT FUNCȚIA PENTRU LOGIN
    // (și am șters duplicatul)
    @GET("api/v1/auth/{userId}/profile")
    fun getAuthProfile( // <-- NUME NOU
        @Path("userId") userId: Long
        // Am șters parametrul @Header("Authorization")
    ): Call<UserProfileResponse> // Folosește UserProfileResponse
    // --- ▲▲▲ SFÂRȘIT MODIFICARE ▲▲▲ ---
}