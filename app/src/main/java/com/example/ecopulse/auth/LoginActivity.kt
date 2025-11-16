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
import com.example.ecopulse.network.*
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

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
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

            val request = LoginRequest(email = email, password = password)

            ApiClient.apiService.loginUser(request)
                .enqueue(object : Callback<LoginResponse> {

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                        if (!response.isSuccessful) {
                            Toast.makeText(this@LoginActivity, "Email sau parolă incorectă", Toast.LENGTH_LONG).show()
                            return
                        }

                        val body = response.body() ?: return

                        // 🟢 Salvăm token + userId
                        saveLoginData(body.accessToken, body.userId)

                        Toast.makeText(this@LoginActivity, body.message, Toast.LENGTH_SHORT).show()

                        startActivity(Intent(this@LoginActivity, MainActivity::class.java))
                        finish()
                    }

                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(this@LoginActivity, "Eroare: ${t.message}", Toast.LENGTH_LONG).show()
                    }
                })
        }


        // ============= REGISTER =============
        btnRegister.setOnClickListener {
            val selectedType = rgUserType.checkedRadioButtonId

            if (selectedType == R.id.rb_normal) {

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
                        override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                            if (!response.isSuccessful) return

                            val body = response.body() ?: return

                            // 🟢 Salvăm doar userId (nu avem token)
                            saveLoginData(null, body.userId)

                            Toast.makeText(this@LoginActivity, body.message, Toast.LENGTH_LONG).show()
                            tabLayout.getTabAt(0)?.select()
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, "Eroare: ${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })

            } else {

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
                        override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                            if (!response.isSuccessful) return

                            val body = response.body() ?: return

                            // 🟢 Salvăm doar userId
                            saveLoginData(null, body.userId)

                            Toast.makeText(this@LoginActivity, body.message, Toast.LENGTH_LONG).show()
                            tabLayout.getTabAt(0)?.select()
                        }

                        override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                            Toast.makeText(this@LoginActivity, "Eroare: ${t.message}", Toast.LENGTH_LONG).show()
                        }
                    })
            }
        }
    }


    // SAVE TOKEN + USER ID
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
}
