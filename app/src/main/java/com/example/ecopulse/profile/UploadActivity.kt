package com.example.ecopulse.profile

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import com.example.ecopulse.R
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import com.google.android.material.textfield.TextInputEditText
import java.io.File

class UploadActivity : AppCompatActivity() {

    // Constante
    private val SLOT_BEFORE = 1
    private val SLOT_AFTER = 2
    private val SLOT_BAGS = 3
    private val MAX_DISTANCE_METERS = 100 // Distanța maximă (în metri) permisă

    // Variabile de stare
    private var currentSlot: Int = 0
    private var uriBefore: Uri? = null
    private var uriAfter: Uri? = null
    private var uriBags: Uri? = null
    private var latestTmpUri: Uri? = null

    // Variabile UI
    private lateinit var imgBefore: ImageView
    private lateinit var imgAfter: ImageView
    private lateinit var imgBags: ImageView
    private lateinit var etBagCount: TextInputEditText

    // Variabile pentru Localizare și Reguli
    private var targetLatLng: LatLng? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var eventType: String = "INDIVIDUAL" // Valoare implicită
    private var eventStartTime: Long = 0L
    private var eventEndTime: Long = 0L

    // Launcher pentru Permisiuni (Locație)
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
            if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
                permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
            ) {
                checkAllRulesAndLaunchCamera()
            } else {
                Toast.makeText(this, "Permisiunea pentru locație este necesară pentru a valida poza.", Toast.LENGTH_LONG).show()
            }
        }

    // Launcher pentru Cameră
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            setImageInSlot(latestTmpUri)
        } else {
            Toast.makeText(this, "Fotografia a eșuat sau a fost anulată.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        supportActionBar?.title = "Finalizează Curățenia"

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // Primim datele despre eveniment
        targetLatLng = intent.getParcelableExtra("TARGET_LATLNG")
        eventType = intent.getStringExtra("EVENT_TYPE") ?: "INDIVIDUAL"
        eventStartTime = intent.getLongExtra("EVENT_START_TIME", 0L)
        eventEndTime = intent.getLongExtra("EVENT_END_TIME", 0L)

        if (targetLatLng == null) {
            Toast.makeText(this, "Eroare: Nu s-au putut încărca datele zonei.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        // Găsește elementele din UI
        imgBefore = findViewById(R.id.img_before)
        imgAfter = findViewById(R.id.img_after)
        imgBags = findViewById(R.id.img_bags)
        etBagCount = findViewById(R.id.et_bag_count)

        val btnBefore = findViewById<Button>(R.id.btn_add_before)
        val btnAfter = findViewById<Button>(R.id.btn_add_after)
        val btnBags = findViewById<Button>(R.id.btn_add_bags)
        val btnSubmit = findViewById<Button>(R.id.btn_submit_cleanup)

        // Setează click listeners
        btnBefore.setOnClickListener {
            currentSlot = SLOT_BEFORE
            checkAllRulesAndLaunchCamera()
        }
        btnAfter.setOnClickListener {
            currentSlot = SLOT_AFTER
            checkAllRulesAndLaunchCamera()
        }
        btnBags.setOnClickListener {
            currentSlot = SLOT_BAGS
            checkAllRulesAndLaunchCamera()
        }

        btnSubmit.setOnClickListener {
            // TODO: Aici va veni logica de trimitere (Upload)
            // pe care am discutat-o (cu multipart/form-data)
            Toast.makeText(this, "Se trimite spre validare...", Toast.LENGTH_SHORT).show()
        }
    }

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    // Funcția "Gardian"
    private fun checkAllRulesAndLaunchCamera() {
        // --- REGULA 1: Verificarea Timpului (doar pentru evenimente COLECTIVE) ---
        if (eventType == "COLLECTIVE") {
            val currentTime = System.currentTimeMillis()

            if (currentTime < eventStartTime) {
                Toast.makeText(this, "Evenimentul nu a început încă!", Toast.LENGTH_LONG).show()
                return // OPREȘTE FLUXUL
            }
            if (eventEndTime > 0 && currentTime > eventEndTime) {
                Toast.makeText(this, "Evenimentul s-a încheiat.", Toast.LENGTH_LONG).show()
                return // OPREȘTE FLUXUL
            }
        }

        // --- REGULA 2: Verificarea Permisiunii de Locație ---
        if (checkPermissions()) {
            getDeviceLocationAndVerify()
        } else {
            requestPermissionLauncher.launch(
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
            )
        }
    }

    // REGULA 3: Verificarea Distanței GPS
    @SuppressLint("MissingPermission")
    private fun getDeviceLocationAndVerify() {
        Toast.makeText(this, "Se verifică locația...", Toast.LENGTH_SHORT).show()

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location == null) {
                    Toast.makeText(this, "Nu am putut găsi locația. Verifică dacă GPS-ul e pornit.", Toast.LENGTH_LONG).show()
                    return@addOnSuccessListener
                }

                val target = targetLatLng ?: return@addOnSuccessListener

                val results = FloatArray(1)
                Location.distanceBetween(
                    target.latitude, target.longitude, // Ținta
                    location.latitude, location.longitude, // Poziția curentă
                    results
                )

                val distanceInMeters = results[0]

                // Decizia finală
                if (distanceInMeters <= MAX_DISTANCE_METERS) {
                    // SUCCES! Deschidem camera.
                    latestTmpUri = getTmpFileUri()
                    takePictureLauncher.launch(latestTmpUri)
                } else {
                    // EȘEC! Ești prea departe.
                    Toast.makeText(this, "Ești prea departe de zona de curățenie! (Aprox. ${distanceInMeters.toInt()} metri)", Toast.LENGTH_LONG).show()
                }
            }
            .addOnFailureListener {
                Toast.makeText(this, "Eroare la obținerea locației: ${it.message}", Toast.LENGTH_LONG).show()
            }
    }

    // Setează imaginea în slotul (ImageView) corect
    private fun setImageInSlot(uri: Uri?) {
        if (uri == null) return
        when (currentSlot) {
            SLOT_BEFORE -> { imgBefore.setImageURI(uri); uriBefore = uri }
            SLOT_AFTER -> { imgAfter.setImageURI(uri); uriAfter = uri }
            SLOT_BAGS -> { imgBags.setImageURI(uri); uriBags = uri }
        }
    }

    // Funcție ajutătoare pentru a crea un fișier temporar
    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("ecopulse_img_", ".png", cacheDir).apply { createNewFile() }
        return FileProvider.getUriForFile(
            applicationContext,
            "${applicationContext.packageName}.provider",
            tmpFile
        )
    }
}