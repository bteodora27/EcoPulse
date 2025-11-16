package com.example.ecopulse.events

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import com.example.ecopulse.R


class AutumnPromoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autumn_promo)

        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_autumn)
        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish()
        }

        findViewById<Button>(R.id.btn_join_autumn).setOnClickListener {
            Toast.makeText(this, "Te-ai înscris! 🗑️🌍", Toast.LENGTH_SHORT).show()
        }
    }
}
