package com.example.ecopulse.profile // Asigură-te că pachetul e corect

import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.ecopulse.R // Asigură-te că importul e corect
import java.io.File

class UploadActivity : AppCompatActivity() {

    private val SLOT_BEFORE = 1
    private val SLOT_AFTER = 2
    private val SLOT_BAGS = 3

    private var currentSlot: Int = 0
    private var uriBefore: Uri? = null
    private var uriAfter: Uri? = null
    private var uriBags: Uri? = null
    private var latestTmpUri: Uri? = null // Variabilă temporară pentru camera

    private lateinit var imgBefore: ImageView
    private lateinit var imgAfter: ImageView
    private lateinit var imgBags: ImageView

    // Launcher pentru a LUA O POZĂ (Camera)
    private val takePictureLauncher = registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
        if (success) {
            // Poza a fost făcută cu succes, 'latestTmpUri' conține poza
            setImageInSlot(latestTmpUri)
        } else {
            Toast.makeText(this, "Fotografia a eșuat sau a fost anulată.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        supportActionBar?.title = "Finalizează Curățenia"

        // Găsește elementele din UI
        imgBefore = findViewById(R.id.img_before)
        imgAfter = findViewById(R.id.img_after)
        imgBags = findViewById(R.id.img_bags)

        val btnBefore = findViewById<Button>(R.id.btn_add_before)
        val btnAfter = findViewById<Button>(R.id.btn_add_after)
        val btnBags = findViewById<Button>(R.id.btn_add_bags)
        val btnSubmit = findViewById<Button>(R.id.btn_submit_cleanup)

        // Setează click listeners (ACUM DESCHID DIRECT CAMERA)
        btnBefore.setOnClickListener {
            currentSlot = SLOT_BEFORE
            launchCamera()
        }
        btnAfter.setOnClickListener {
            currentSlot = SLOT_AFTER
            launchCamera()
        }
        btnBags.setOnClickListener {
            currentSlot = SLOT_BAGS
            launchCamera()
        }

        btnSubmit.setOnClickListener {
            // TODO: Logica de validare și trimitere la API
            Toast.makeText(this, "Se trimite spre validare...", Toast.LENGTH_SHORT).show()
        }
    }

    // Pornește Camera Intent
    private fun launchCamera() {
        // Creăm un URI nou unde camera să salveze poza
        latestTmpUri = getTmpFileUri()
        takePictureLauncher.launch(latestTmpUri)
    }

    // Setează imaginea în slotul (ImageView) corect
    private fun setImageInSlot(uri: Uri?) {
        if (uri == null) return

        when (currentSlot) {
            SLOT_BEFORE -> {
                imgBefore.setImageURI(uri)
                uriBefore = uri
            }
            SLOT_AFTER -> {
                imgAfter.setImageURI(uri)
                uriAfter = uri
            }
            SLOT_BAGS -> {
                imgBags.setImageURI(uri)
                uriBags = uri
            }
        }
    }

    // Funcție ajutătoare pentru a crea un fișier temporar
    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("ecopulse_img_", ".png", cacheDir).apply {
            createNewFile()
        }

        return FileProvider.getUriForFile(
            applicationContext,
            "${applicationContext.packageName}.provider", // Trebuie să fie identic cu 'authorities' din Manifest
            tmpFile
        )
    }
}