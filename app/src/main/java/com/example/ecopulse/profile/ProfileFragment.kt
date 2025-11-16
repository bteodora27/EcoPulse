package com.example.ecopulse.profile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
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

    private lateinit var historyContainer: LinearLayout
    private lateinit var profileImage: ImageView
    private lateinit var activeSessionCard: View
    private lateinit var btnContinue: View
    private lateinit var tvActiveSessionTitle: TextView

    private var userId: Long = -1L

    // ---------- PERMISSIONS ----------
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            // no UI needed
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

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES
            )
        )

        userId = requireContext()
            .getSharedPreferences("EcoPulsePrefs", Context.MODE_PRIVATE)
            .getLong("USER_ID", -1L)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_profile, container, false)
    }

    override fun onResume() {
        super.onResume()
        checkActiveSession()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        historyContainer = view.findViewById(R.id.history_container)
        profileImage = view.findViewById(R.id.profile_image)
        activeSessionCard = view.findViewById(R.id.active_session_card)
        btnContinue = view.findViewById(R.id.btn_continue_activity)
        tvActiveSessionTitle = view.findViewById(R.id.tv_active_session_title)

        loadUserProfile()
        loadUserActivities()

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

            if (sessionId == -1L) {
                return@setOnClickListener
            }

            val intent = Intent(requireContext(), ContinueCleanupActivity::class.java)
            intent.putExtra("SESSION_ID", sessionId)
            startActivity(intent)
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
            tvActiveSessionTitle.text = "Curățenie în desfășurare"
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

        ApiClient.apiService.uploadProfilePicture(userId, part)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    Log.d("UPLOAD", "SUCCESS: ${response.body()?.message}")
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
        if (userId == -1L) return

        ApiClient.apiService.getUserProfile(userId).enqueue(
            object : Callback<UserProfile> {
                override fun onResponse(call: Call<UserProfile>, res: Response<UserProfile>) {
                    val p = res.body() ?: return

                    view?.findViewById<TextView>(R.id.tv_username)?.text =
                        "${p.firstName} ${p.lastName}"

                    view?.findViewById<TextView>(R.id.tv_rank)?.text = mapRank(p.rank)

                    view?.findViewById<TextView>(R.id.tv_stat_activities)?.text =
                        p.totalEventsJoined.toString()

                    view?.findViewById<TextView>(R.id.tv_stat_points)?.text =
                        p.totalPoints.toString()
                }

                override fun onFailure(call: Call<UserProfile>, t: Throwable) {}
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

                override fun onFailure(call: Call<List<UserActivityItem>>, t: Throwable) {}
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
        "seed" -> "Sămânță"
        "sprout" -> "Vlăstar"
        "sapling" -> "Mugure"
        "green_agent" -> "Agent Verde"
        else -> "Necunoscut"
    }

    private fun showImagePickerDialog(onGallery: () -> Unit, onCamera: () -> Unit) {
        AlertDialog.Builder(requireContext())
            .setTitle("Schimbă poza de profil")
            .setItems(arrayOf("Alege din galerie", "Fă poză", "Anulează")) { d, i ->
                when (i) {
                    0 -> onGallery()
                    1 -> onCamera()
                    else -> d.dismiss()
                }
            }
            .show()
    }
}
