package com.example.ecopulse.cleanup_session

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.ecopulse.R

class CleanupSessionActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cleanup_session)

        val sessionId = intent.getLongExtra("SESSION_ID", -1)
        findViewById<TextView>(R.id.tv_session_id).text =
            if (sessionId == -1L) "Session ID: Unknown" else "Session ID: $sessionId"

        findViewById<TextView>(R.id.tv_session_id).text = "Session ID: $sessionId"

        findViewById<Button>(R.id.btn_end_session).setOnClickListener {
            finish() // TODO: implement AFTER-END workflow
        }
    }
}
