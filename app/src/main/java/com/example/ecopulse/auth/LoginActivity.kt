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
import com.google.android.material.tabs.TabLayout
import com.google.android.material.textfield.TextInputEditText

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()

        // --- Referințe UI Generale ---
        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val loginSection = findViewById<LinearLayout>(R.id.login_section)
        val registerSection = findViewById<LinearLayout>(R.id.register_section)
        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        // --- Referințe UI pentru Register ---
        val rgUserType = findViewById<RadioGroup>(R.id.rg_user_type)
        val containerNormal = findViewById<LinearLayout>(R.id.container_normal_user)
        val containerOrg = findViewById<LinearLayout>(R.id.container_org_user)

        // --- Referințe Câmpuri Normal User ---
        val etRegUsername = findViewById<TextInputEditText>(R.id.et_reg_username)
        val etRegEmail = findViewById<TextInputEditText>(R.id.et_reg_email)
        val etRegPassword = findViewById<TextInputEditText>(R.id.et_reg_password)
        val etRegPhone = findViewById<TextInputEditText>(R.id.et_reg_phone)
        val etRegFirstName = findViewById<TextInputEditText>(R.id.et_reg_firstname)
        val etRegLastName = findViewById<TextInputEditText>(R.id.et_reg_lastname)
        val etRegBirthDate = findViewById<TextInputEditText>(R.id.et_reg_birthdate)

        // --- Referințe Câmpuri Organization ---
        val etRegOrgUsername = findViewById<TextInputEditText>(R.id.et_reg_org_username)
        val etRegOrgEmail = findViewById<TextInputEditText>(R.id.et_reg_org_email)
        val etRegOrgPassword = findViewById<TextInputEditText>(R.id.et_reg_org_password)
        val etRegOrgName = findViewById<TextInputEditText>(R.id.et_reg_org_name)
        val etRegOrgPhone = findViewById<TextInputEditText>(R.id.et_reg_org_phone)
        val etRegOrgWebsite = findViewById<TextInputEditText>(R.id.et_reg_org_website)
        val etRegOrgDesc = findViewById<TextInputEditText>(R.id.et_reg_org_desc)
        val etRegOrgAddress = findViewById<TextInputEditText>(R.id.et_reg_org_address)
        val etRegOrgContactEmail = findViewById<TextInputEditText>(R.id.et_reg_org_contact_email)


        // 1. Logica pentru Tab-uri (Comutare Login / Register)
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

        // 2. Logica pentru Comutare Tip Utilizator (Normal vs Org)
        rgUserType.setOnCheckedChangeListener { _, checkedId ->
            if (checkedId == R.id.rb_normal) {
                containerNormal.visibility = View.VISIBLE
                containerOrg.visibility = View.GONE
            } else {
                containerNormal.visibility = View.GONE
                containerOrg.visibility = View.VISIBLE
            }
        }

        // 3. Logica Buton LOGIN
        btnLogin.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.et_login_email).text.toString()
            val password = findViewById<TextInputEditText>(R.id.et_login_password).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Te rugăm completează toate câmpurile", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Call API Login
                Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // 4. Logica Buton REGISTER
        btnRegister.setOnClickListener {
            val selectedTypeId = rgUserType.checkedRadioButtonId

            if (selectedTypeId == R.id.rb_normal) {
                // === Validare User Normal ===
                val username = etRegUsername.text.toString()
                val email = etRegEmail.text.toString()
                val password = etRegPassword.text.toString()
                val phone = etRegPhone.text.toString()
                val firstName = etRegFirstName.text.toString()
                val lastName = etRegLastName.text.toString()
                val birthDate = etRegBirthDate.text.toString() // Opțional

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() ||
                    phone.isEmpty() || firstName.isEmpty() || lastName.isEmpty()) {
                    Toast.makeText(this, "Completează câmpurile obligatorii (Normal)", Toast.LENGTH_SHORT).show()
                } else {
                    // TODO: Trimite datele USER NORMAL la Backend/Firebase
                    Toast.makeText(this, "Creare cont Normal: $username", Toast.LENGTH_SHORT).show()
                    Log.d("REGISTER", "Normal: $username, $email, $firstName $lastName")
                }

            } else {
                // === Validare Organizație ===
                val username = etRegOrgUsername.text.toString()
                val email = etRegOrgEmail.text.toString()
                val password = etRegOrgPassword.text.toString()
                val orgName = etRegOrgName.text.toString()
                val phone = etRegOrgPhone.text.toString()
                val address = etRegOrgAddress.text.toString()
                val contactEmail = etRegOrgContactEmail.text.toString()
                val website = etRegOrgWebsite.text.toString() // Opțional
                val desc = etRegOrgDesc.text.toString() // Opțional

                if (username.isEmpty() || email.isEmpty() || password.isEmpty() ||
                    orgName.isEmpty() || phone.isEmpty() || address.isEmpty() || contactEmail.isEmpty()) {
                    Toast.makeText(this, "Completează câmpurile obligatorii (Org)", Toast.LENGTH_SHORT).show()
                } else {
                    // TODO: Trimite datele ORGANIZAȚIE la Backend/Firebase
                    Toast.makeText(this, "Creare cont Organizație: $orgName", Toast.LENGTH_SHORT).show()
                    Log.d("REGISTER", "Org: $username, $orgName, $address")
                }
            }
        }
    }
}