package com.example.ecopulse.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.ecopulse.R
import com.example.ecopulse.auth.LoginActivity

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    // 2500ms = 2.5 secunde
    private val SPLASH_DELAY: Long = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            finish()

        }, SPLASH_DELAY)
    }
}