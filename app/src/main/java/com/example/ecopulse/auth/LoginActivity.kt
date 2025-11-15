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
import com.example.ecopulse.network.* // Importă tot
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        sharedPreferences = getSharedPreferences("EcoPulsePrefs", Context.MODE_PRIVATE)

        // --- Găsește Toate Elementele din XML ---
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val loginSection = findViewById<LinearLayout>(R.id.login_section)
        val registerSection = findViewById<LinearLayout>(R.id.register_section)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_register) // ID-ul din XML

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
                    // Când este selectat LOGIN
                    loginSection.visibility = View.VISIBLE
                    registerSection.visibility = View.GONE

                    // --- MODIFICAREA AICI ---
                    btnLogin.visibility = View.VISIBLE
                    btnRegister.visibility = View.GONE

                } else {
                    // Când este selectat REGISTER
                    loginSection.visibility = View.GONE
                    registerSection.visibility = View.VISIBLE

                    // --- MODIFICAREA AICI ---
                    btnLogin.visibility = View.GONE
                    btnRegister.visibility = View.VISIBLE
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

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Te rugăm completează toate câmpurile", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()
            val requestBody = LoginRequest(email = email, password = password)

            ApiClient.apiService.loginUser(requestBody).enqueue(object : Callback<LoginResponse> {

                override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                    if (response.isSuccessful) {
                        val loginResponse = response.body()
                        if (loginResponse?.accessToken != null) {
                            Toast.makeText(this@LoginActivity, loginResponse.message, Toast.LENGTH_SHORT).show()
                            saveAuthToken(loginResponse.accessToken)
                            val intent = Intent(this@LoginActivity, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            Toast.makeText(this@LoginActivity, "Eroare: Răspuns invalid de la server.", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        Toast.makeText(this@LoginActivity, "Eroare: Email sau parolă incorectă.", Toast.LENGTH_LONG).show()
                        Log.e("LOGIN_FAIL", "Cod: ${response.code()}, Mesaj: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                    Toast.makeText(this@LoginActivity, "Eroare rețea: ${t.message}", Toast.LENGTH_LONG).show()
                    Log.e("LOGIN_FAIL", "Eroare conexiune API", t)
                }
            })
        }

        // --- Logica Buton REGISTER (CU APEL API) ---
        btnRegister.setOnClickListener {
            val selectedTypeId = rgUserType.checkedRadioButtonId

            if (selectedTypeId == R.id.rb_normal) {
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

                val requestBody = RegisterNormalUserRequest(
                    email = email, username = username, passHash = password,
                    firstName = firstName, lastName = lastName, phone = phone, birthDate = birthDate
                )

                Toast.makeText(this, "Se creează contul...", Toast.LENGTH_SHORT).show()

                ApiClient.apiService.registerNormalUser(requestBody).enqueue(object : Callback<RegisterResponse> {

                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {
                            val registerResponse = response.body()
                            val mesaj = registerResponse?.message ?: "Cont creat cu succes"
                            Toast.makeText(this@LoginActivity, mesaj, Toast.LENGTH_LONG).show()
                            tabLayout.getTabAt(0)?.select()
                        } else {
                            val errorMsg = response.message()
                            Toast.makeText(this@LoginActivity, "Eroare server: $errorMsg", Toast.LENGTH_LONG).show()
                            Log.e("REGISTER_FAIL", "Cod: ${response.code()}, Mesaj: ${response.errorBody()?.string()}")
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Eroare rețea: ${t.message}", Toast.LENGTH_LONG).show()
                        Log.e("REGISTER_FAIL", "Eroare conexiune API", t)
                    }
                })

            } else {
                val username = etRegOrgUsername.text.toString()
                val email = etRegOrgEmail.text.toString()
                val password = etRegOrgPassword.text.toString()
                val orgName = etRegOrgName.text.toString()
                val phone = etRegOrgPhone.text.toString()
                val contactEmail = etRegOrgContactEmail.text.toString()
                val address = etRegOrgAddress.text.toString()
                val website = etRegOrgWebsite.text.toString().ifEmpty { null }
                val description = etRegOrgDesc.text.toString().ifEmpty { null }

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() ||
                    orgName.isEmpty() || phone.isEmpty() || contactEmail.isEmpty() || address.isEmpty()) {
                    Toast.makeText(this, "Completează câmpurile obligatorii (Organizație)", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }

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

                ApiClient.apiService.registerOrganization(requestBody).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {
                            val registerResponse = response.body()
                            val mesaj = registerResponse?.message ?: "Cont creat cu succes"
                            Toast.makeText(this@LoginActivity, mesaj, Toast.LENGTH_LONG).show()
                            tabLayout.getTabAt(0)?.select()
                        } else {
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

    private fun saveAuthToken(token: String) {
        val editor = sharedPreferences.edit()
        editor.putString("AUTH_TOKEN", token)
        editor.apply()
    }
}