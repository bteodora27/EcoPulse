package com.example.ecopulse.profile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.ecopulse.R
import com.example.ecopulse.cleanup_session.ContinueCleanupActivity
import com.example.ecopulse.cleanup_session.SessionStorage
import com.example.ecopulse.network.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileOutputStream

class ProfileFragment : Fragment() {

    // --- Elemente UI ---
    private lateinit var historyContainer: LinearLayout
    private lateinit var profileImage: ImageView
    private lateinit var activeSessionCard: View
    private lateinit var btnContinue: View
    private lateinit var tvActiveSessionTitle: TextView

    // --- Date Utilizator ---
    private lateinit var sharedPreferences: SharedPreferences
    private var userId: Long = -1L
    private var authToken: String? = null

    // ---------- PERMISSIONS ----------
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perms ->
            val granted = perms[Manifest.permission.CAMERA] == true || perms[Manifest.permission.READ_MEDIA_IMAGES] == true
            if (!granted) {
                Log.w("ProfileFragment", "Permisiunile pentru cameră sau galerie nu au fost acordate.")
            }
        }

    // ---------- GALLERY ----------
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data ?: return@registerForActivityResult
                profileImage.setImageURI(uri)
                uploadProfilePicture(uri)
            }
        }

    // ---------- CAMERA ----------
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val bmp = result.data?.extras?.get("data") as? Bitmap ?: return@registerForActivityResult
                profileImage.setImageBitmap(bmp)
                uploadProfilePicture(saveTempBitmap(bmp))
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cere permisiunile o singură dată
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES // Pentru Android 13+
                // Consideră și READ_EXTERNAL_STORAGE pt API < 33 dacă e nevoie
            )
        )

        // Citește datele salvate la Login
        sharedPreferences = requireContext().getSharedPreferences("EcoPulsePrefs", Context.MODE_PRIVATE)
        userId = sharedPreferences.getLong("USER_ID", -1L)
        authToken = sharedPreferences.getString("AUTH_TOKEN", null)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate layout-ul
        return inflater.inflate(R.layout.activity_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Găsește elementele UI o singură dată
        historyContainer = view.findViewById(R.id.history_container)
        profileImage = view.findViewById(R.id.profile_image)
        activeSessionCard = view.findViewById(R.id.active_session_card)
        btnContinue = view.findViewById(R.id.btn_continue_activity)
        tvActiveSessionTitle = view.findViewById(R.id.tv_active_session_title)

        // Setează listener-ii
        profileImage.setOnClickListener {
            profileImage.startAnimation(
                AnimationUtils.loadAnimation(requireContext(), R.anim.profile_pulse)
            )
            profileImage.postDelayed({
                showImagePickerDialog(
                    onGallery = { pickImageFromGallery() },
                    onCamera = { openCamera() }
                )
            }, 180)
        }

        btnContinue.setOnClickListener {
            val (sessionId, _) = SessionStorage.getSession(requireContext())
            if (sessionId == -1L) return@setOnClickListener

            val intent = Intent(requireContext(), ContinueCleanupActivity::class.java)
            intent.putExtra("SESSION_ID", sessionId)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        // === MODIFICAREA CHEIE ===
        // De fiecare dată când fragmentul redevine vizibil (ex. după ce închizi ContinueCleanupActivity):

        // 1. Actualizează UI-ul IMEDIAT cu datele salvate local (inclusiv noul rang/puncte)
        updateProfileUIFromPrefs()

        // 2. Verifică dacă există o sesiune activă
        checkActiveSession()

        // 3. Cere date proaspete de la server (care vor suprascrie datele locale)
        loadUserProfile()
        loadUserActivities()
    }

    // --- FUNCȚIE NOUĂ: Actualizează UI-ul din ce e salvat local ---
    private fun updateProfileUIFromPrefs() {
        view?.let {
            it.findViewById<TextView>(R.id.tv_username)?.text =
                "${sharedPreferences.getString("USER_FIRST_NAME", "...")} ${sharedPreferences.getString("USER_LAST_NAME", "...")}"

            val currentRank = sharedPreferences.getString("USER_RANK", "seed") ?: "seed"
            it.findViewById<TextView>(R.id.tv_rank)?.text = mapRank(currentRank)

            it.findViewById<TextView>(R.id.tv_stat_activities)?.text = "..." // Acesta vine doar de la API

            it.findViewById<TextView>(R.id.tv_stat_points)?.text =
                sharedPreferences.getInt("USER_POINTS", 0).toString()
        }
    }

    // -------------------
    // ACTIVE SESSION UI
    // -------------------
    private fun checkActiveSession() {
        val (sessionId, _) = SessionStorage.getSession(requireContext())
        if (sessionId == -1L) {
            activeSessionCard.visibility = View.GONE
        } else {
            activeSessionCard.visibility = View.VISIBLE
            tvActiveSessionTitle.text = "Cleaning in progress"
        }
    }

    // -------------------
    // PROFILE IMAGE UPLOAD
    // -------------------
    private fun pickImageFromGallery() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        galleryLauncher.launch(intent)
    }

    private fun openCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraLauncher.launch(intent)
    }

    private fun saveTempBitmap(bitmap: Bitmap): Uri {
        val file = File(requireContext().cacheDir, "profile_temp.jpg")
        FileOutputStream(file).use {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, it)
        }
        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )
    }

    private fun uploadProfilePicture(uri: Uri) {
        val part = uriToMultipart(uri)

        if (userId == -1L || authToken == null) {
            Toast.makeText(requireContext(), "Eroare: Nu sunteți logat.", Toast.LENGTH_SHORT).show()
            return
        }

        // TODO: Adaugă @Header("Authorization") la 'uploadProfilePicture' în ApiService
        ApiClient.apiService.uploadProfilePicture(userId, part)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    if (response.isSuccessful) {
                        Log.d("UPLOAD", "SUCCESS: ${response.body()?.message}")
                    } else {
                        Log.e("UPLOAD", "FAIL: ${response.code()}")
                    }
                }
                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    Log.e("UPLOAD", "FAIL: ${t.message}")
                }
            })
    }

    private fun uriToMultipart(uri: Uri): MultipartBody.Part {
        val bytes = requireContext().contentResolver.openInputStream(uri)?.readBytes()
            ?: ByteArray(0)
        val body = bytes.toRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", "profile.jpg", body)
    }

    // -------------------
    // PROFILE API
    // -------------------
    private fun loadUserProfile() {
        if (userId == -1L) {
            Log.e("ProfileFragment", "Nu pot încărca profilul, userId e -1")
            return // Nu suntem logați, nu facem apel
        }

        ApiClient.apiService.getUserProfile(userId).enqueue(
            object : Callback<UserProfile> {
                override fun onResponse(call: Call<UserProfile>, res: Response<UserProfile>) {
                    if (!res.isSuccessful) {
                        Log.e("ProfileFragment", "Eroare la încărcare profil: ${res.code()}")
                        return
                    }
                    val p = res.body() ?: return

                    // Actualizăm UI-ul cu datele proaspete de la server
                    view?.findViewById<TextView>(R.id.tv_username)?.text =
                        "${p.firstName} ${p.lastName}"
                    view?.findViewById<TextView>(R.id.tv_rank)?.text = mapRank(p.rank)
                    view?.findViewById<TextView>(R.id.tv_stat_activities)?.text =
                        p.totalEventsJoined.toString()
                    view?.findViewById<TextView>(R.id.tv_stat_points)?.text =
                        p.totalPoints.toString()

                    // Salvăm datele noi în SharedPreferences
                    val editor = sharedPreferences.edit()
                    editor.putString("USER_FIRST_NAME", p.firstName)
                    editor.putString("USER_LAST_NAME", p.lastName)
                    editor.putString("USER_RANK", p.rank)
                    editor.putInt("USER_POINTS", p.totalPoints)
                    editor.apply()
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {
                    Log.e("ProfileFragment", "Fail at loading the profile: ${t.message}")
                }
            }
        )
    }

    private fun loadUserActivities() {
        if (userId == -1L) return

        ApiClient.apiService.getUserActivities(userId).enqueue(
            object : Callback<List<UserActivityItem>> {
                override fun onResponse(
                    call: Call<List<UserActivityItem>>,
                    res: Response<List<UserActivityItem>>
                ) {
                    val list = res.body().orEmpty()
                    historyContainer.removeAllViews()
                    list.forEach { addHistoryItem(it) }
                }
                override fun onFailure(call: Call<List<UserActivityItem>>, t: Throwable) {
                    Log.w("ProfileFragment", "Fail at loading the activities: ${t.message}")
                }
            }
        )
    }

    private fun addHistoryItem(item: UserActivityItem) {
        val v = LayoutInflater.from(requireContext())
            .inflate(R.layout.item_history, historyContainer, false)

        v.findViewById<TextView>(R.id.tv_history_title).text = item.eventName
        v.findViewById<TextView>(R.id.tv_history_date).text = item.date
        v.findViewById<TextView>(R.id.tv_history_points).text =
            "+${item.rating ?: 0} puncte"

        historyContainer.addView(v)
    }

    private fun mapRank(rank: String) = when (rank) {
        "Seed" -> "Seed"
        "Sprout" -> "Sprout"
        "Sapling" -> "Sapling"
        "Tree" -> "Tree"
        else -> "Unknown"
    }

    private fun showImagePickerDialog(onGallery: () -> Unit, onCamera: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("Change your profile picture")
            .setItems(arrayOf("Pick from gallery", "Take a picture", "Cancel")) { d, i ->
                when (i) {
                    0 -> onGallery()
                    1 -> onCamera()
                    else -> d.dismiss()
                }
            }
            .show()
    }
}