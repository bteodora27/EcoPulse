package com.example.ecopulse.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.ecopulse.R
import com.example.ecopulse.auth.LoginActivity
import okhttp3.Request
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient


@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {
    // private val client = OkHttpClient()
    // 2500ms = 2.5 secunde
    private val SPLASH_DELAY: Long = 2500

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Log.d("API " , "Am primit")
        // CoroutineScope(Dispatchers.IO).launch {
           // val response = callEndpoint()
            //Log.d("API", "Response: $response")
       // }//how to map response data to a data class
        //how to populate data from data class to app page
        setContentView(R.layout.activity_splash)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({

            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)

            finish()

        }, SPLASH_DELAY)
    }

//    private fun callEndpoint(): String? {
//        val request = Request.Builder()
//            .url("http://10.0.10.180:8080/api/v1/history")
//            .build()
//
//        client.newCall(request).execute().use { response ->
//            return response.body?.string()
//        }
//    }
}
