package com.example.ecopulse.network

// JSON-ul pe care îl TRIMIȚI pentru Organizații
data class RegisterOrgRequest(
    val email: String,
    val username: String,
    val passHash: String,
    val organizationName: String,
    val phone: String,
    val contactEmail: String,
    val description: String?, // Opțional
    val website: String?,     // Opțional
    val adress: String        // Folosim "adress" ca să se potrivească cu backend-ul
)