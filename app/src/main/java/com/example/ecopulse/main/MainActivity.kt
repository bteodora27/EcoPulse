package com.example.ecopulse.main

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ecopulse.R
import com.example.ecopulse.events.EventsFragment
import com.example.ecopulse.map.HartaFragment
import com.example.ecopulse.profile.ProfileFragment
import com.example.ecopulse.rewards.RewardsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import android.view.View // Adăugat pentru a folosi View

class MainActivity : AppCompatActivity() {

    private val currentUserRank = 1
    private val requiredRank = 3

    // Mutat referința la FAB în clasa, pentru acces public
    private lateinit var fabCreateEvent: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val bottomNavView = findViewById<BottomNavigationView>(R.id.bottom_navigation_view)
        fabCreateEvent = findViewById<FloatingActionButton>(R.id.fab_create_event) // Inițializat

        if (savedInstanceState == null) {
            loadFragment(EventsFragment())
        }

        bottomNavView.setOnItemSelectedListener { item ->
            var selectedFragment: Fragment? = null

            when (item.itemId) {
                R.id.nav_events -> {
                    // Când mergi pe Events, asigură-te că FAB-ul e vizibil
                    setFabVisibility(true)
                    selectedFragment = EventsFragment()
                }
                R.id.nav_map -> {
                    // Când mergi pe Map, ascunde FAB-ul Add Event
                    setFabVisibility(false)
                    selectedFragment = HartaFragment()
                }
                R.id.nav_profile -> {
                    setFabVisibility(false)
                    selectedFragment = ProfileFragment()
                }
                R.id.nav_rewards -> {
                    setFabVisibility(false)
                    selectedFragment = RewardsFragment()
                }
            }

            if (selectedFragment != null) {
                loadFragment(selectedFragment)
            }

            true
        }

        fabCreateEvent.setOnClickListener { view ->
            if (currentUserRank >= requiredRank) {
                Toast.makeText(this, "You have the required rank! Starting event editor...", Toast.LENGTH_SHORT).show()
            } else {
                Snackbar.make(view, "You must reach the 'Eco Guard' rank to create events!", Snackbar.LENGTH_LONG)
                    .setAction("Info", null)
                    .show()
            }
        }
    }

    // NOU: Funcție publică pentru a controla vizibilitatea FAB-ului
    fun setFabVisibility(isVisible: Boolean) {
        if (isVisible) {
            fabCreateEvent.show()
        } else {
            fabCreateEvent.hide()
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, fragment)
            .commit()
    }
}