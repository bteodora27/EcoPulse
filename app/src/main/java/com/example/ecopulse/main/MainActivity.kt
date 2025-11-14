package com.example.ecopulse.main

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ecopulse.R
import com.example.ecopulse.events.EventsFragment
import com.example.ecopulse.map.HartaFragment


import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    // simulare
    private val currentUserRank = 1
    private val requiredRank = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        val fabCreateEvent = findViewById<FloatingActionButton>(R.id.fab_create_event)

        if (savedInstanceState == null) {
            loadFragment(EventsFragment())
        }

        bottomNavView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.nav_events -> {
                    selectedFragment = EventsFragment()
                }
                R.id.nav_map -> {
                    selectedFragment = HartaFragment()
                }
                R.id.nav_profile -> {
                    // TODO: Creează și încarcă ProfileFragment
                    // selectedFragment = ProfileFragment()
                }
                R.id.nav_rewards -> {
                    // TODO: Creează și încarcă RewardsFragment
                    // selectedFragment = RewardsFragment()
                }
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment)
            }

            true
        }

        fabCreateEvent.setOnClickListener { view ->

            if (currentUserRank >= requiredRank) {
                // DACĂ ARE RANGUL: Pornește activitatea de creare a evenimentului
                // TODO: Va trebui să creăm 'CreateEventActivity'
                Toast.makeText(this, "Ai rangul necesar! Pornim editorul...", Toast.LENGTH_SHORT).show()

            } else {
                // DACĂ NU ARE RANGUL: Afișează un mesaj sugestiv
                Snackbar.make(view, "Trebuie să atingi rangul 'Garda Eco' pentru a crea evenimente!", Snackbar.LENGTH_LONG)
                    .setAction("Info", null)
                    .show()
            }
        }
    }

    // Funcție ajutătoare pentru a încărca un fragment
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}