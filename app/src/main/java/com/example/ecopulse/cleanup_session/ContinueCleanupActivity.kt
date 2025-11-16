package com.example.ecopulse.cleanup_session

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import com.example.ecopulse.R
import com.example.ecopulse.network.ApiClient
import com.example.ecopulse.network.EndCleanupResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
// ▼▼▼ LINIA ASTA LIPSEA SAU NU ERA CITITĂ CORECT ▼▼▼
import android.widget.Toast
// ▲▲▲ SFÂRȘIT ▲▲▲

class ContinueCleanupActivity : AppCompatActivity() {

    private var bagsPhotoUri: Uri? = null
    private var afterPhotoUri: Uri? = null
    private var photoTarget: String? = null
    private var latestTmpUri: Uri? = null

    private var sessionId: Long = -1

    private lateinit var imgBags: ImageView
    private lateinit var imgAfter: ImageView
    private lateinit var btnValidate: Button

    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_continue_cleanup)

        sharedPreferences = getSharedPreferences("EcoPulsePrefs", Context.MODE_PRIVATE)

        sessionId = intent.getLongExtra("SESSION_ID", -1)
        if (sessionId == -1L) {
            Toast.makeText(this, "Eroare: Sesiune invalidă.", Toast.LENGTH_LONG).show()
            finish()
            return
        }

        imgBags = findViewById(R.id.img_bags_photo)
        imgAfter = findViewById(R.id.img_after_photo)
        btnValidate = findViewById(R.id.btn_validate)

        imgBags.setOnClickListener { selectImageFor("bags") }
        imgAfter.setOnClickListener { selectImageFor("after") }

        btnValidate.setOnClickListener {
            if (bagsPhotoUri == null || afterPhotoUri == null) {
                // Acum 'Toast.LENGTH_SHORT' va fi găsit
                Toast.makeText(this, "Încarcă ambele poze!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            uploadPhotos()
        }
    }

    private fun selectImageFor(type: String) {
        photoTarget = type
        AlertDialog.Builder(this)
            .setTitle("Alege imaginea")
            .setItems(arrayOf("Fă poză", "Din galerie")) { _, choice ->
                when (choice) {
                    0 -> openCamera()
                    1 -> openGallery()
                }
            }
            .show()
    }

    // --- GALLERY ---
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) handleSelectedPhoto(uri)
        }

    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }

    // --- CAMERA ---
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) {
                handleSelectedPhoto(latestTmpUri)
            } else {
                Toast.makeText(this, "Fotografia a eșuat.", Toast.LENGTH_SHORT).show()
            }
        }

    private fun openCamera() {
        latestTmpUri = getTmpFileUri()
        cameraLauncher.launch(latestTmpUri)
    }

    private fun handleSelectedPhoto(uri: Uri?) {
        if (uri == null) return
        when (photoTarget) {
            "bags" -> {
                bagsPhotoUri = uri
                imgBags.setImageURI(uri)
            }
            "after" -> {
                afterPhotoUri = uri
                imgAfter.setImageURI(uri)
            }
        }
    }

    private fun getTmpFileUri(): Uri {
        val tmpFile = File.createTempFile("photo_${System.currentTimeMillis()}", ".png", externalCacheDir).apply {
            createNewFile()
        }
        return FileProvider.getUriForFile(
            applicationContext,
            "${applicationContext.packageName}.provider",
            tmpFile
        )
    }

    // --- Funcția uploadPhotos() ---
    private fun uploadPhotos() {
        val bags = uriToMultipart(bagsPhotoUri!!, "bagsPhoto")
        val after = uriToMultipart(afterPhotoUri!!, "afterPhoto")

        if (bags == null || after == null) {
            Toast.makeText(this, "Eroare la pregătirea imaginilor.", Toast.LENGTH_SHORT).show()
            return
        }

        ApiClient.apiService.endCleaningSession(sessionId, bags, after)
            .enqueue(object : Callback<EndCleanupResponse> {
                override fun onResponse(call: Call<EndCleanupResponse>, response: Response<EndCleanupResponse>) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body != null) {
                            Toast.makeText(
                                this@ContinueCleanupActivity,
                                "${body.message} Ai primit ${body.pointsGained} puncte!",
                                Toast.LENGTH_LONG
                            ).show()
                            saveNewProfileStats(body.newTotalPoints, body.newRank)
                            SessionStorage.clearSession(this@ContinueCleanupActivity)
                            finish()
                        } else {
                            Toast.makeText(this@ContinueCleanupActivity, "Răspuns invalid de la server.", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(
                            this@ContinueCleanupActivity,
                            "Eroare server: ${response.message()}",
                            Toast.LENGTH_SHORT
                        ).show()
                        Log.e("UPLOAD_FAIL", "Cod: ${response.code()}, Mesaj: ${response.errorBody()?.string()}")
                    }
                }

                override fun onFailure(call: Call<EndCleanupResponse>, t: Throwable) {
                    Toast.makeText(
                        this@ContinueCleanupActivity,
                        "Eroare rețea: ${t.message}",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e("UPLOAD_FAIL", "Eroare rețea", t)
                }
            })
    }

    private fun uriToMultipart(uri: Uri, name: String): MultipartBody.Part? {
        return try {
            val input = contentResolver.openInputStream(uri)
            val bytes = input?.readBytes() ?: ByteArray(0)
            input?.close()
            val body = bytes.toRequestBody("image/*".toMediaTypeOrNull())
            MultipartBody.Part.createFormData(name, "$name.jpg", body)
        } catch (e: Exception) {
            Log.e("URI_PARSE_FAIL", "Eroare la citirea fișierului URI: ${e.message}")
            null
        }
    }

    private fun saveNewProfileStats(totalPoints: Int, rank: String) {
        val editor = sharedPreferences.edit()
        editor.putInt("USER_POINTS", totalPoints)
        editor.putString("USER_RANK", rank)
        editor.apply()
        Log.d("PROFILE_UPDATE", "Statistici salvate: Puncte=$totalPoints, Rang=$rank")
    }
}