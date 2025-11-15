package com.example.ecopulse.auth

import android.content.Intent
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
import com.example.ecopulse.network.ApiClient
import com.example.ecopulse.network.RegisterNormalUserRequest
import com.example.ecopulse.network.RegisterResponse // Folosim acest model
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        // --- Găsește Toate Elementele din XML ---
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val loginSection = findViewById<LinearLayout>(R.id.login_section)
        val registerSection = findViewById<LinearLayout>(R.id.register_section)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_register)
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

        // --- Referințe Câmpuri Organization (nefolosite încă) ---
        // val etRegOrgUsername = findViewById<TextInputEditText>(R.id.et_reg_org_username)
        // ... etc.


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

        // --- Logica Buton LOGIN ---
        btnLogin.setOnClickListener {
            // TODO: Implementează apelul API pentru Login
            Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
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
                // TODO: Implementează logica de register pentru Organizație
                Toast.makeText(this, "Logica pentru organizații nu e implementată încă.", Toast.LENGTH_SHORT).show()
            }
        }
    }
}