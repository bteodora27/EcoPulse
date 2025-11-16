package com.example.ecopulse.events

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.ecopulse.R
import com.google.android.material.appbar.MaterialToolbar

class EventDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_event_details)

        // 🔙 TOOLBAR cu back button
        val toolbar = findViewById<MaterialToolbar>(R.id.toolbar_event)


        setSupportActionBar(toolbar)

        toolbar.setNavigationOnClickListener {
            finish() // închide activity și revine la lista de evenimente
        }

        val eventId = intent.getIntExtra("event_id", -1)
        val event = EventRepository.events.find { it.id == eventId }

        if (event == null) {
            finish()
            return
        }

        val img = findViewById<ImageView>(R.id.img_event)
        val tvTitle = findViewById<TextView>(R.id.tv_title)
        val tvDetails = findViewById<TextView>(R.id.tv_details)
        val btnMaps = findViewById<Button>(R.id.btn_open_maps)

        // SETARE DATE
        tvTitle.text = event.title
        tvDetails.text = "${event.participants}\n${event.dateLocation}"

        // IMAGINE
        if (event.imageRes != null) {
            img.setImageResource(event.imageRes!!)
        } else if (event.imageUrl != null) {
            Glide.with(this).load(event.imageUrl).into(img)
        }

        // GOOGLE MAPS
        btnMaps.setOnClickListener {
            val gmmIntentUri = Uri.parse(
                "geo:${event.latitude},${event.longitude}?q=${event.latitude},${event.longitude}(${event.title})"
            )
            val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
            mapIntent.setPackage("com.google.android.apps.maps")
            startActivity(mapIntent)
        }
    }
}
