package com.example.ecopulse.auth

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
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
        Log.d("API " , "Am primit")

        val tabLayout = findViewById<TabLayout>(R.id.tab_layout)
        val loginSection = findViewById<LinearLayout>(R.id.login_section)
        val registerSection = findViewById<LinearLayout>(R.id.register_section)

        val btnLogin = findViewById<Button>(R.id.btn_login)
        val btnRegister = findViewById<Button>(R.id.btn_register)

        // Logica pentru Tab-uri (Login/Register)
        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab?.position == 0) {
                    // S-a selectat "Login"
                    loginSection.visibility = View.VISIBLE
                    registerSection.visibility = View.GONE
                } else {
                    // S-a selectat "Register"
                    loginSection.visibility = View.GONE
                    registerSection.visibility = View.VISIBLE
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {}
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })

        // Logica pentru Butonul de Login
        btnLogin.setOnClickListener {
            val email = findViewById<TextInputEditText>(R.id.et_login_email).text.toString()
            val password = findViewById<TextInputEditText>(R.id.et_login_password).text.toString()

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Adaugă aici logica de autentificare (ex. Firebase)
                Toast.makeText(this, "Logging in...", Toast.LENGTH_SHORT).show()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Logica pentru Butonul de Register
        btnRegister.setOnClickListener {
            val name = findViewById<TextInputEditText>(R.id.et_register_name).text.toString()
            val email = findViewById<TextInputEditText>(R.id.et_register_email).text.toString()
            val password = findViewById<TextInputEditText>(R.id.et_register_password).text.toString()

            if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Adaugă aici logica de înregistrare (ex. Firebase)
                Toast.makeText(this, "Creating account...", Toast.LENGTH_SHORT).show()

            }
        }
    }
}