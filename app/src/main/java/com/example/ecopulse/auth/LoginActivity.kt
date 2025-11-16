package com.example.ecopulse.auth

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ecopulse.main.MainActivity
import com.example.ecopulse.R
import com.example.ecopulse.network.* // Importă tot (inclusiv UserProfileResponse)
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var tabLayout: TabLayout // Am mutat-o aici ca să o accesăm din Register

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences("EcoPulsePrefs", Context.MODE_PRIVATE)

        // --- Găsește Toate Elementele din XML ---
        tabLayout = findViewById(R.id.tab_layout) // Inițializare
        val loginSection = findViewById<LinearLayout>(R.id.login_section)
        val registerSection = findViewById<LinearLayout>(R.id.register_section)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        val etLoginEmail = findViewById<TextInputEditText>(R.id.et_login_email)
        val etLoginPassword = findViewById<TextInputEditText>(R.id.et_login_password)

        val rgUserType = findViewById<RadioGroup>(R.id.rg_user_type)
        val containerNormal = findViewById<LinearLayout>(R.id.container_normal_user)
        val containerOrg = findViewById<LinearLayout>(R.id.container_org_user)

        val etRegUsername = findViewById<TextInputEditText>(R.id.et_reg_username)
        val etRegEmail = findViewById<TextInputEditText>(R.id.et_reg_email)
        val etRegPassword = findViewById<TextInputEditText>(R.id.et_reg_password)
        val etRegPhone = findViewById<TextInputEditText>(R.id.et_reg_phone)
        val etRegFirstName = findViewById<TextInputEditText>(R.id.et_reg_firstname)
        val etRegLastName = findViewById<TextInputEditText>(R.id.et_reg_lastname)
        val etRegBirthDate = findViewById<TextInputEditText>(R.id.et_reg_birthdate)

        val etRegOrgUsername = findViewById<TextInputEditText>(R.id.et_reg_org_username)
        val etRegOrgEmail = findViewById<TextInputEditText>(R.id.et_reg_org_email)
        val etRegOrgPassword = findViewById<TextInputEditText>(R.id.et_reg_org_password)
        val etRegOrgName = findViewById<TextInputEditText>(R.id.et_reg_org_name)
        val etRegOrgPhone = findViewById<TextInputEditText>(R.id.et_reg_org_phone)
        val etRegOrgWebsite = findViewById<TextInputEditText>(R.id.et_reg_org_website)
        val etRegOrgDesc = findViewById<TextInputEditText>(R.id.et_reg_org_desc)
        val etRegOrgAddress = findViewById<TextInputEditText>(R.id.et_reg_org_address)
        val etRegOrgContactEmail = findViewById<TextInputEditText>(R.id.et_reg_org_contact_email)


        // TAB CONTROL
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    loginSection.visibility = View.VISIBLE
                    registerSection.visibility = View.GONE
                    btnLogin.visibility = View.VISIBLE
                    btnRegister.visibility = View.GONE
                } else {
                    loginSection.visibility = View.GONE
                    registerSection.visibility = View.VISIBLE
                    btnLogin.visibility = View.GONE
                    btnRegister.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })


        // SWITCH NORMAL / ORG REGISTER
        rgUserType.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_normal) {
                containerNormal.visibility = View.VISIBLE
                containerOrg.visibility = View.GONE
            } else {
                containerNormal.visibility = View.GONE
                containerOrg.visibility = View.VISIBLE
            }
        }


        // ============= LOGIN =============
        btnLogin.setOnClickListener {
            val email = etLoginEmail.text.toString()
            val password = etLoginPassword.text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Completează toate câmpurile", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()

            val request = LoginRequest(email = email, password = password)

            ApiClient.apiService.loginUser(request)
                .enqueue(object : Callback<LoginResponse> {

                    override fun onResponse(
                        call: Call<LoginResponse>,
                        response: Response<LoginResponse>
                    ) {
                        if (!response.isSuccessful) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Email sau parolă incorectă",
                                Toast.LENGTH_LONG
                            ).show()
                            return
                        }

                        val body = response.body()
                        if (body?.accessToken == null || body.userId == null) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Eroare: Răspuns invalid de la server",
                                Toast.LENGTH_LONG
                            ).show()
                            return
                        }

                        // ▼▼▼ MODIFICARE ▼▼▼
                        // Am primit token și ID. NU pornim încă MainActivity.
                        // Mai întâi, facem al doilea apel pentru a lua profilul.

                        // 1. Salvăm datele primare
                        saveLoginData(body.accessToken, body.userId)
                        Toast.makeText(this@LoginActivity, body.message, Toast.LENGTH_SHORT).show()

                        // 2. Facem al doilea apel
                        fetchUserProfile(body.accessToken, body.userId)
                        // ▲▲▲ SFÂRȘIT MODIFICARE ▲▲▲
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(
                            this@LoginActivity,
                            "Eroare rețea: ${t.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                })
        }


        // ============= REGISTER =============
        btnRegister.setOnClickListener {
            val selectedType = rgUserType.checkedRadioButtonId

            if (selectedType == R.id.rb_normal) {
                // ... (Logica ta de register normal rămâne neschimbată)
                val request = RegisterNormalUserRequest(
                    email = etRegEmail.text.toString(),
                    username = etRegUsername.text.toString(),
                    passHash = etRegPassword.text.toString(),
                    firstName = etRegFirstName.text.toString(),
                    lastName = etRegLastName.text.toString(),
                    phone = etRegPhone.text.toString(),
                    birthDate = etRegBirthDate.text.toString().ifEmpty { null }
                )
                ApiClient.apiService.registerNormalUser(request)
                    .enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            if (!response.isSuccessful) return
                            val body = response.body() ?: return
                            saveLoginData(null, body.userId)
                            Toast.makeText(this@LoginActivity, body.message, Toast.LENGTH_LONG)
                                .show()
                            tabLayout.getTabAt(0)?.select()
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Eroare: ${t.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })

            } else {
                // ... (Logica ta de register organizație rămâne neschimbată)
                val request = RegisterOrgRequest(
                    email = etRegOrgEmail.text.toString(),
                    username = etRegOrgUsername.text.toString(),
                    passHash = etRegOrgPassword.text.toString(),
                    organizationName = etRegOrgName.text.toString(),
                    phone = etRegOrgPhone.text.toString(),
                    contactEmail = etRegOrgContactEmail.text.toString(),
                    description = etRegOrgDesc.text.toString().ifEmpty { null },
                    website = etRegOrgWebsite.text.toString().ifEmpty { null },
                    adress = etRegOrgAddress.text.toString()
                )
                ApiClient.apiService.registerOrganization(request)
                    .enqueue(object : Callback<RegisterResponse> {
                        override fun onResponse(
                            call: Call<RegisterResponse>,
                            response: Response<RegisterResponse>
                        ) {
                            if (!response.isSuccessful) return
                            val body = response.body() ?: return
                            saveLoginData(null, body.userId)
                            Toast.makeText(this@LoginActivity, body.message, Toast.LENGTH_LONG)
                                .show()
                            tabLayout.getTabAt(0)?.select()
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(
                                this@LoginActivity,
                                "Eroare: ${t.message}",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    })
            }
        }
    }

    // --- FUNCȚIA TA DE SALVARE (Am păstrat-o) ---
    private fun saveLoginData(token: String?, userId: Long?) {
        val editor = sharedPreferences.edit()

        token?.let {
            editor.putString("AUTH_TOKEN", it)
        }

        userId?.let {
            editor.putLong("USER_ID", it)
        }

        editor.apply()
    }

    // --- ▼▼▼ FUNCȚIE MODIFICATĂ PENTRU APELUL 2 ▼▼▼ ---
    // --- FUNCȚIE NOUĂ PENTru APELUL 2 (CU NUMELE CORECT) ---
    private fun fetchUserProfile(token: String, userId: Long) {
        // Am șters 'authToken = "Bearer $token"' (pentru că am scos @Header)

        // ▼▼▼ MODIFICARE AICI: Apeși funcția CORECTĂ ▼▼▼
        ApiClient.apiService.getAuthProfile(userId).enqueue(object : Callback<UserProfileResponse> {

            override fun onResponse(
                call: Call<UserProfileResponse>,
                response: Response<UserProfileResponse>
            ) {
                if (response.isSuccessful) {
                    val profile = response.body()
                    if (profile != null) {
                        // AM PRIMIT PROFILUL!
                        // 2. Salvăm datele de profil
                        saveFullProfileData(profile)

                        // 3. ABIA ACUM Îl băgăm în aplicație
                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    } else {
                        Toast.makeText(
                            this@LoginActivity,
                            "Eroare: Nu s-a putut citi profilul.",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        this@LoginActivity,
                        "Eroare la încărcarea profilului.",
                        Toast.LENGTH_LONG
                    ).show()
                    Log.e(
                        "GET_PROFILE_FAIL",
                        "Cod: ${response.code()}, Mesaj: ${response.errorBody()?.string()}"
                    )
                }
            }

            override fun onFailure(call: Call<UserProfileResponse>, t: Throwable) {
                Toast.makeText(
                    this@LoginActivity,
                    "Eroare rețea (Profil): ${t.message}",
                    Toast.LENGTH_LONG
                ).show()
                Log.e("GET_PROFILE_FAIL", "Eroare conexiune API", t)
            }
        })
    }

    // --- FUNCȚIE NOUĂ PENTRU SALVAREA PROFILULUI COMPLET ---
    private fun saveFullProfileData(profile: UserProfileResponse) {
        val editor = sharedPreferences.edit()

        // Salvăm tot ce primim de la al doilea apel
        // (ID-ul și token-ul sunt deja salvate de 'saveLoginData')
        editor.putString("USER_EMAIL", profile.email)
        editor.putString("USER_USERNAME", profile.username)
        editor.putString("USER_FIRST_NAME", profile.firstName)
        editor.putString("USER_LAST_NAME", profile.lastName)
        editor.putString("USER_RANK", profile.standardUserRank)
        editor.putInt("USER_POINTS", profile.totalPoints)
        editor.putString("USER_PHONE", profile.phone)

        editor.apply()
    }
}