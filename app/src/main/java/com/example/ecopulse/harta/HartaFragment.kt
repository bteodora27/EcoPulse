package com.example.ecopulse.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.fragment.app.Fragment
import com.example.ecopulse.R
import com.example.ecopulse.cleanup_session.CleanupSessionActivity
import com.example.ecopulse.cleanup_session.SessionStorage
import com.example.ecopulse.main.MainActivity
import com.example.ecopulse.network.ApiClient
import com.example.ecopulse.network.StartCleanupResponse
import com.google.android.gms.location.*
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.*
import java.io.File

class HartaFragment : Fragment(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheetTitle: TextView
    private lateinit var bottomSheetDescription: TextView
    private lateinit var btnStartCleanup: MaterialButton

    private var selectedPin: MapPin? = null
    private lateinit var currentPhotoUri: Uri


    // PERMISIUNI
    private val requestPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                getCurrentLocation()
            }
        }

    // CAMERA
    private val cameraLauncher =
        registerForActivityResult(ActivityResultContracts.TakePicture()) { success ->
            if (success) showPhotoReviewDialog(currentPhotoUri)
            else Toast.makeText(context, "Camera canceled.", Toast.LENGTH_SHORT).show()
        }

    // GALERIE
    private val galleryLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
            if (uri != null) {
                currentPhotoUri = uri
                showPhotoReviewDialog(uri)
            }
        }


    data class MapPin(
        val position: LatLng,
        val title: String,
        val description: String,
        val isClean: Boolean
    )

    private val pins = listOf(
        MapPin(LatLng(45.7575, 21.2288), "Unirii Square", "Dirty area. Needs cleaning!", false),
        MapPin(LatLng(45.7541, 21.2257), "Opera House", "Cleaned 2 days ago.", true),
        MapPin(LatLng(45.7513, 21.2291), "Cathedral Park", "Reported dirty recently.", false)
    )


    override fun onCreateView(
        inflater: android.view.LayoutInflater,
        container: android.view.ViewGroup?,
        savedInstanceState: Bundle?
    ): android.view.View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setFabVisibility(false)
    }

    override fun onPause() {
        super.onPause()
        (activity as? MainActivity)?.setFabVisibility(true)
    }


    override fun onViewCreated(view: android.view.View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment = childFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val bottomSheet = view.findViewById<LinearLayout>(R.id.bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)

        bottomSheetTitle = view.findViewById(R.id.bottom_sheet_title)
        bottomSheetDescription = view.findViewById(R.id.bottom_sheet_description)
        btnStartCleanup = view.findViewById(R.id.btn_start_cleanup)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        btnStartCleanup.setOnClickListener { showPhotoChoiceDialog() }

        setupMapControls(view)
    }


    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false

        addPinsToMap()

        map.setOnMarkerClickListener { marker ->
            selectedPin = marker.tag as MapPin
            showPinDetails(selectedPin!!)
            true
        }

        map.setOnMapClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        map.moveCamera(CameraUpdateFactory.newLatLngZoom(pins.first().position, 15f))
    }

    private fun addPinsToMap() {
        pins.forEach { pin ->
            val marker = googleMap?.addMarker(
                MarkerOptions()
                    .position(pin.position)
                    .title(pin.title)
                    .icon(
                        BitmapDescriptorFactory.defaultMarker(
                            if (pin.isClean) BitmapDescriptorFactory.HUE_GREEN
                            else BitmapDescriptorFactory.HUE_RED
                        )
                    )
            )
            marker?.tag = pin
        }
    }


    private fun showPinDetails(pin: MapPin) {
        bottomSheetTitle.text = pin.title
        bottomSheetDescription.text = pin.description
        btnStartCleanup.visibility = if (pin.isClean) View.GONE else View.VISIBLE
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
    }


    private fun showPhotoChoiceDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Choose photo")
            .setItems(arrayOf("Take a photo", "Choose from gallery")) { _, choice ->
                when (choice) {
                    0 -> takePhoto()
                    1 -> chooseFromGallery()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }


    private fun takePhoto() {
        val file = File(requireContext().cacheDir, "before_${System.currentTimeMillis()}.jpg")
        currentPhotoUri = FileProvider.getUriForFile(
            requireContext(),
            "${requireContext().packageName}.provider",
            file
        )
        cameraLauncher.launch(currentPhotoUri)
    }


    private fun chooseFromGallery() {
        galleryLauncher.launch("image/*")
    }


    private fun showPhotoReviewDialog(uri: Uri) {
        val img = ImageView(requireContext())
        img.setImageURI(uri)

        AlertDialog.Builder(requireContext())
            .setTitle("Confirm photo")
            .setView(img)
            .setPositiveButton("Confirm") { _, _ -> uploadPhoto(uri) }
            .setNegativeButton("Retake") { _, _ -> showPhotoChoiceDialog() }
            .setNeutralButton("Cancel", null)
            .show()
    }


    private fun uploadPhoto(uri: Uri) {
        val pin = selectedPin ?: return

        // 🔥 User ID din login
        val prefs = requireContext().getSharedPreferences("EcoPulsePrefs", Context.MODE_PRIVATE)
        val userId = prefs.getLong("USER_ID", -1)

        if (userId == -1L) {
            Toast.makeText(context, "Eroare: nu ești logat!", Toast.LENGTH_LONG).show()
            return
        }

        // 🔥 COPY SAFE pentru toate tipurile de URI
        val file = File(requireContext().cacheDir, "upload_${System.currentTimeMillis()}.jpg")

        requireContext().contentResolver.openInputStream(uri)?.use { input ->
            file.outputStream().use { output ->
                input.copyTo(output)
            }
        }

        val reqPhoto = file.asRequestBody("image/jpeg".toMediaType())
        val multipartPhoto = MultipartBody.Part.createFormData("beforePhoto", file.name, reqPhoto)

        val reqUser = userId.toString().toRequestBody("text/plain".toMediaType())
        val reqLat = pin.position.latitude.toString().toRequestBody("text/plain".toMediaType())
        val reqLon = pin.position.longitude.toString().toRequestBody("text/plain".toMediaType())

        ApiClient.apiService.startIndividualCleanup(
            reqUser,
            multipartPhoto,
            reqLat,
            reqLon
        ).enqueue(object : Callback<StartCleanupResponse> {
            override fun onResponse(call: Call<StartCleanupResponse>, response: Response<StartCleanupResponse>) {
                if (!response.isSuccessful) {
                    Toast.makeText(context, "Server error!", Toast.LENGTH_SHORT).show()
                    return
                }

                val body = response.body()
                if (body == null || body.sessionId == null) {
                    Toast.makeText(context, "Eroare: sesiune invalidă!", Toast.LENGTH_SHORT).show()
                    return
                }

                val sessionId = body.sessionId
                val newUserId = body.userId

                SessionStorage.saveSession(requireContext(), sessionId, newUserId)

                requireActivity().runOnUiThread {
                    Toast.makeText(requireContext(), "Curățenie începută!", Toast.LENGTH_SHORT).show()

                    SessionStorage.saveSession(requireContext(), sessionId, userId)

                    (activity as? MainActivity)?.goToProfile()
                }
            }

            override fun onFailure(call: Call<StartCleanupResponse>, t: Throwable) {
                Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    private fun goToProfile() {
        val main = requireActivity() as MainActivity
        main.goToProfile()
    }

//    private fun openCleanupSession(sessionId: Long) {
//        val intent = Intent(requireContext(), CleanupSessionActivity::class.java)
//        intent.putExtra("SESSION_ID", sessionId)
//        startActivity(intent)
//
//        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
//        selectedPin = null
//    }


    @SuppressLint("MissingPermission")
    private fun setupMapControls(view: View) {
        view.findViewById<ImageButton>(R.id.btn_my_location).setOnClickListener {

            if (!checkLocationPermission()) {
                requestPermissionLauncher.launch(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION)
                )
                return@setOnClickListener
            }

            fusedLocationClient.lastLocation
                .addOnSuccessListener { loc: Location? ->
                    loc?.let {
                        googleMap?.animateCamera(
                            CameraUpdateFactory.newLatLngZoom(
                                LatLng(it.latitude, it.longitude), 16f
                            )
                        )
                    }
                }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (!checkLocationPermission()) return

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    googleMap?.animateCamera(
                        CameraUpdateFactory.newLatLngZoom(
                            LatLng(location.latitude, location.longitude), 16f
                        )
                    )

                } else {
                    Toast.makeText(
                        context,
                        "GPS unavailable. Turn on location",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
    }

    private fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }
}
