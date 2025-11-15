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
import com.example.ecopulse.network.* // Importă tot (ApiClient, LoginRequest, LoginResponse, etc.)
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    // Referință la SharedPreferences pentru a salva token-ul
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        // Inițializează SharedPreferences
        sharedPreferences = getSharedPreferences("EcoPulsePrefs", Context.MODE_PRIVATE)

        // --- Găsește Toate Elementele din XML ---
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val loginSection = findViewById<LinearLayout>(R.id.login_section)
        val registerSection = findViewById<LinearLayout>(R.id.register_section)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        // Câmpurile de Login
        val etLoginEmail = findViewById<TextInputEditText>(R.id.et_login_email)
        val etLoginPassword = findViewById<TextInputEditText>(R.id.et_login_password)

        // Câmpurile de Register
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

        // --- Referințe Câmpuri Organization (LIPSEAU) ---
        val etRegOrgUsername = findViewById<TextInputEditText>(R.id.et_reg_org_username)
        val etRegOrgEmail = findViewById<TextInputEditText>(R.id.et_reg_org_email)
        val etRegOrgPassword = findViewById<TextInputEditText>(R.id.et_reg_org_password)
        val etRegOrgName = findViewById<TextInputEditText>(R.id.et_reg_org_name)
        val etRegOrgPhone = findViewById<TextInputEditText>(R.id.et_reg_org_phone)
        val etRegOrgWebsite = findViewById<TextInputEditText>(R.id.et_reg_org_website)
        val etRegOrgDesc = findViewById<TextInputEditText>(R.id.et_reg_org_desc)
        val etRegOrgAddress = findViewById<TextInputEditText>(R.id.et_reg_org_address)
        val etRegOrgContactEmail = findViewById<TextInputEditText>(R.id.et_reg_org_contact_email)

        // --- Logica Tab-uri (Login/Register) ---
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    loginSection.visibility = View.VISIBLE
                    registerSection.visibility = View.GONE
                } else {
                    loginSection.visibility = View.GONE
                    registerSection.visibility = View.VISIBLE
                }
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // --- Logica Tip Cont (Normal/Org) ---
        rgUserType.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_normal) {
                containerNormal.visibility = View.VISIBLE
                containerOrg.visibility = View.GONE
            } else {
                containerNormal.visibility = View.GONE
                containerOrg.visibility = View.VISIBLE
            }
        }

        // --- Logica Buton LOGIN (CU APEL API) ---
        btnLogin.setOnClickListener {
            val email = etLoginEmail.text.toString()
            val password = etLoginPassword.text.toString()

            // 1. Validare locală
            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Te rugăm completează toate câmpurile", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()

            // 2. Creează obiectul cererii
            // Schimbă 'passHash = password' în 'password = password'
            val requestBody = LoginRequest(email = email, password = password)

            // 3. Trimite cererea la API
            ApiClient.apiService.loginUser(requestBody).enqueue(object : Callback<LoginResponse> {

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        // SUCCES! (Cod 2xx)
                        val loginResponse = response.body()

                        if (loginResponse?.accessToken != null) {
                            // AM PRIMIT TOKEN!
                            Toast.makeText(this@LoginActivity, loginResponse.message, Toast.LENGTH_SHORT).show()

                            // 1. Salvăm token-ul!
                            saveAuthToken(loginResponse.accessToken)

                            // 2. Îl băgăm în aplicație
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Eroare de logică backend (ex: 200 OK dar fără token)
                            Toast.makeText(this@LoginActivity, "Eroare: Răspuns invalid de la server.", Toast.LENGTH_LONG).show()
                        }

                    } else {
                        // EROARE DE LA SERVER (ex: 401 Unauthorized - parolă greșită)
                        Toast.makeText(this@LoginActivity, "Eroare: Email sau parolă incorectă.", Toast.LENGTH_LONG).show()
                        Log.e("LOGIN_FAIL", "Cod: ${response.code()}, Mesaj: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    // Eroare de rețea (fără net, IP greșit, server oprit)
                    Toast.makeText(this@LoginActivity, "Eroare rețea: ${t.message}", Toast.LENGTH_LONG).show()
                    Log.e("LOGIN_FAIL", "Eroare conexiune API", t)
                }
            })
        }

        // --- Logica Buton REGISTER (CU APEL API) ---
        btnRegister.setOnClickListener {
            val selectedTypeId = rgUserType.checkedRadioButtonId

            if (selectedTypeId == R.id.rb_normal) {
                // 1. Citește datele și validează
                val username = etRegUsername.text.toString()
                val email = etRegEmail.text.toString()
                val password = etRegPassword.text.toString()
                val phone = etRegPhone.text.toString()
                val firstName = etRegFirstName.text.toString()
                val lastName = etRegLastName.text.toString()
                val birthDate = etRegBirthDate.text.toString().ifEmpty { null }

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() ||
                    phone.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(this, "Completează câmpurile obligatorii", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // 2. Creează obiectul
                val requestBody = RegisterNormalUserRequest(
                    email = email, username = username, passHash = password,
                    firstName = firstName, lastName = lastName, phone = phone, birthDate = birthDate
                )

                Toast.makeText(this, "Se creează contul...", Toast.LENGTH_SHORT).show()

                // 3. Trimite cererea la API
                ApiClient.apiService.registerNormalUser(requestBody).enqueue(object : Callback<RegisterResponse> {

                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {
                            // SUCCES! (Cod 2xx)
                            val registerResponse = response.body()
                            val mesaj = registerResponse?.message ?: "Cont creat cu succes"

                            Toast.makeText(this@LoginActivity, mesaj, Toast.LENGTH_LONG).show()

                            // === FLUXUL TĂU: ÎL ÎNTOARCEM LA LOGIN ===
                            tabLayout.getTabAt(0)?.select() // Comută tab-ul înapoi la Login

                        } else {
                            // EROARE DE LA SERVER (ex: 409 Conflict)
                            val errorMsg = response.message()
                            Toast.makeText(this@LoginActivity, "Eroare server: $errorMsg", Toast.LENGTH_LONG).show()
                            Log.e("REGISTER_FAIL", "Cod: ${response.code()}, Mesaj: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        // Aici va crăpa DOAR dacă JSON-ul nu se potrivește
                        Toast.makeText(this@LoginActivity, "Eroare rețea: ${t.message}", Toast.LENGTH_LONG).show()
                        Log.e("REGISTER_FAIL", "Eroare conexiune API", t)
                    }
                })

            } else {
                // =============================================
                // ▼▼▼ LOGICA NOUĂ PENTRU ORGANIZAȚIE ▼▼▼
                // =============================================

                // 1. Citește datele din formularul de Organizație
                val username = etRegOrgUsername.text.toString()
                val email = etRegOrgEmail.text.toString()
                val password = etRegOrgPassword.text.toString()
                val orgName = etRegOrgName.text.toString()
                val phone = etRegOrgPhone.text.toString()
                val contactEmail = etRegOrgContactEmail.text.toString()
                val address = etRegOrgAddress.text.toString()
                val website = etRegOrgWebsite.text.toString().ifEmpty { null } // Opțional
                val description = etRegOrgDesc.text.toString().ifEmpty { null } // Opțional

                // 2. Validează câmpurile obligatorii
                if (username.isEmpty() || email.isEmpty() || password.isEmpty() ||
                    orgName.isEmpty() || phone.isEmpty() || contactEmail.isEmpty() || address.isEmpty()) {
                    Toast.makeText(this, "Completează câmpurile obligatorii (Organizație)", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

                // 3. Creează obiectul de trimis
                val requestBody = RegisterOrgRequest(
                    email = email,
                    username = username,
                    passHash = password,
                    organizationName = orgName,
                    phone = phone,
                    contactEmail = contactEmail,
                    description = description,
                    website = website,
                    adress = address
                )

                Toast.makeText(this, "Se creează contul de organizație...", Toast.LENGTH_SHORT).show()

                // 4. Trimite cererea la API
                ApiClient.apiService.registerOrganization(requestBody).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {
                            // SUCCES! (Cod 2xx)
                            val registerResponse = response.body()
                            val mesaj = registerResponse?.message ?: "Cont creat cu succes"
                            Toast.makeText(this@LoginActivity, mesaj, Toast.LENGTH_LONG).show()

                            // Întoarce la Login
                            tabLayout.getTabAt(0)?.select()
                        } else {
                            // EROARE DE LA SERVER (ex: 409 Conflict)
                            val errorMsg = response.message()
                            Toast.makeText(this@LoginActivity, "Eroare server: $errorMsg", Toast.LENGTH_LONG).show()
                            Log.e("REGISTER_ORG_FAIL", "Cod: ${response.code()}, Mesaj: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Eroare rețea: ${t.message}", Toast.LENGTH_LONG).show()
                        Log.e("REGISTER_ORG_FAIL", "Eroare conexiune API", t)
                    }
                })
            }
        }
    }

    // Funcție ajutătoare pentru a SALVA token-ul
    private fun saveAuthToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("AUTH_TOKEN", token)
        editor.apply()
    }
}