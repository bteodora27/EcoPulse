package com.example.ecopulse.profile

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
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
    private lateinit var tempPhotoFile: File

    private val userId = 1L // TODO: Ia acest ID din SharedPreferences după Login

    // ---------- PERMISSION LAUNCHER ----------
    private val permissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { perms ->
            val granted = perms[Manifest.permission.CAMERA] == true &&
                    perms[Manifest.permission.READ_MEDIA_IMAGES] == true

            if (!granted)
                Log.e("PERMISSION", "Permissions denied.")
        }

    // ---------- GALLERY PICKER ----------
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val uri = result.data?.data ?: return@registerForActivityResult
                profileImage.setImageURI(uri)
                uploadProfilePicture(uri)
            }
        }

    // ---------- CAMERA LAUNCHER ----------
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {

                val bitmap = result.data?.extras?.get("data") as? Bitmap
                if (bitmap != null) {
                    profileImage.setImageBitmap(bitmap)

                    val uri = saveTempBitmap(bitmap)
                    uploadProfilePicture(uri)
                }
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.CAMERA,
                Manifest.permission.READ_MEDIA_IMAGES // Consideră permisiunile noi pt Android 13+
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.activity_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        historyContainer = view.findViewById(R.id.history_container)
        profileImage = view.findViewById(R.id.profile_image)

        loadUserProfile()
        loadUserActivities()

        profileImage.setOnClickListener {

            val anim = AnimationUtils.loadAnimation(requireContext(), R.anim.profile_pulse)
            profileImage.startAnimation(anim)

            profileImage.postDelayed({
                showImagePickerDialog(
                    onGallery = { pickImageFromGallery() },
                    onCamera = { openCamera() }
                )
            }, 180)
        }
    }

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
        val out = FileOutputStream(file)
        bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
        out.flush()
        out.close()

        return FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )
    }

    private fun uploadProfilePicture(uri: Uri) {
        // ▼▼▼ CORECTAT AICI ▼▼▼
        val api = ApiClient.apiService
        val part = uriToMultipart(uri)

        api.uploadProfilePicture(userId, part)
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
        val input = requireContext().contentResolver.openInputStream(uri)
        val bytes = input?.readBytes() ?: ByteArray(0)

        val body = bytes.toRequestBody("image/*".toMediaTypeOrNull())
        return MultipartBody.Part.createFormData("file", "profile.jpg", body)
    }


    private fun loadUserProfile() {
        // ▼▼▼ CORECTAT AICI ▼▼▼
        val api = ApiClient.apiService
        api.getUserProfile(userId).enqueue(
            object : Callback<UserProfile> {
                override fun onResponse(call: Call<UserProfile>, res: Response<UserProfile>) {
                    if (!res.isSuccessful) return
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
        // ▼▼▼ CORECTAT AICI ▼▼▼
        val api = ApiClient.apiService
        api.getUserActivities(userId).enqueue(
            object : Callback<List<UserActivityItem>> {
                override fun onResponse(
                    call: Call<List<UserActivityItem>>,
                    res: Response<List<UserActivityItem>>
                ) {
                    if (!res.isSuccessful) return
                    val list = res.body() ?: return
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
        "green_agent" -> "Agentul Verde"
        else -> "Necunoscut"
    }

    private fun showImagePickerDialog(onGallery: () -> Unit, onCamera: () -> Unit) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle("Schimbă poza de profil")
            .setItems(arrayOf("Alege din galerie", "Fă poză", "Anulează")) { d, i ->
                when (i) {
                    0 -> onGallery()
                    1 -> onCamera()
                    2 -> d.dismiss()
                }
            }
            .create()

        dialog.show()
    }
}