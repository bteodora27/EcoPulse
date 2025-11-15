package com.example.ecopulse.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.ecopulse.R
import com.example.ecopulse.network.ApiClient
import com.example.ecopulse.network.StartCleanupRequest
import com.example.ecopulse.network.StartCleanupResponse
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// Importăm MainActivity pentru a accesa funcția publică (asigură-te că importul este corect)
import com.example.ecopulse.main.MainActivity

class HartaFragment : Fragment(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheetTitle: TextView
    private lateinit var bottomSheetDescription: TextView
    private lateinit var btnStartCleanup: MaterialButton
    private var selectedPin: MapPin? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            getCurrentLocation()
        } else {
            Toast.makeText(context, "Location permission denied.", Toast.LENGTH_SHORT).show() // TRADUS
        }
    }

    data class MapPin(
        val position: LatLng,
        val title: String,
        val description: String,
        val isClean: Boolean
    )
    private val pins = listOf(
        MapPin(LatLng(45.7575, 21.2288), "Unirii Square Area", "Has never been cleaned.", false), // TRADUS
        MapPin(LatLng(45.7535, 21.2255), "Victoriei Square", "Cleaned by @teodora 1 week ago", true), // TRADUS
        MapPin(LatLng(45.7472, 21.2262), "Cathedral Park", "Dirty (recently reported)", false) // TRADUS
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    // NOU: Logica pentru ascunderea FAB-ului global când fragmentul Map este vizibil
    override fun onResume() {
        super.onResume()
        // Ascunde FAB-ul global (Create Event)
        (activity as? MainActivity)?.setFabVisibility(false)
    }

    // NOU: Logica pentru re-afișarea FAB-ului global când fragmentul Map nu mai este vizibil
    override fun onPause() {
        super.onPause()
        // Re-afișează FAB-ul global (Create Event)
        (activity as? MainActivity)?.setFabVisibility(true)
    }
    // END NOU

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        val bottomSheetLayout = view.findViewById<LinearLayout>(R.id.bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetTitle = view.findViewById(R.id.bottom_sheet_title)
        bottomSheetDescription = view.findViewById(R.id.bottom_sheet_description)
        btnStartCleanup = view.findViewById(R.id.btn_start_cleanup)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        setupButtonListeners(view)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        map.uiSettings.isZoomControlsEnabled = false
        map.uiSettings.isMyLocationButtonEnabled = false
        addMarkersToMap()
        Log.d("HartaDebug", "onMapReady: Map is ready.") // TRADUS

        map.setOnMarkerClickListener { marker ->
            Log.d("HartaDebug", "!!! MARKER CLICK DETECTED !!! Title: ${marker.title}") // TRADUS
            val pin = marker.tag as? MapPin
            if (pin != null) {
                Log.d("HartaDebug", "Marker has tag. Showing bottom sheet for: ${pin.title}") // TRADUS
                selectedPin = pin
                updateBottomSheet(pin)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
            else {
                Log.e("HartaDebug", "ERROR: marker.tag is NULL. Check addMarkersToMap.") // TRADUS
            }
            true
        }
        map.setOnMapClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
            selectedPin = null
        }
        val timisoara = LatLng(45.7575, 21.2288)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(timisoara, 15f))
    }

    private fun addMarkersToMap() {
        Log.d("HartaDebug", "Adding ${pins.size} markers to the map...") // TRADUS

        pins.forEach { pin ->
            val icon = if (pin.isClean) BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            else BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)

            val marker = googleMap?.addMarker(
                MarkerOptions()
                    .position(pin.position)
                    .title(pin.title)
                    .icon(icon)
            )
            marker?.tag = pin

            if (marker == null) {
                Log.e("HartaDebug", "ERROR: addMarker failed for pin: ${pin.title}") // TRADUS
            }
        }
    }

    private fun updateBottomSheet(pin: MapPin) {
        bottomSheetTitle.text = pin.title
        bottomSheetDescription.text = pin.description
        btnStartCleanup.visibility = if (pin.isClean) View.GONE else View.VISIBLE
    }

    private fun setupButtonListeners(view: View) {

        view.findViewById<ImageButton>(R.id.btn_menu).setOnClickListener { showAboutDialog() }
        view.findViewById<ImageButton>(R.id.btn_zoom_in).setOnClickListener { googleMap?.animateCamera(CameraUpdateFactory.zoomIn()) }
        view.findViewById<ImageButton>(R.id.btn_zoom_out).setOnClickListener { googleMap?.animateCamera(CameraUpdateFactory.zoomOut()) }
        view.findViewById<ImageButton>(R.id.btn_my_location).setOnClickListener {
            if (checkPermissions()) {
                getCurrentLocation()
            } else {
                requestPermissionLauncher.launch(
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
                )
            }
        }
        view.findViewById<ExtendedFloatingActionButton>(R.id.fab_report_area).setOnClickListener {
            Toast.makeText(context, "Report Area clicked", Toast.LENGTH_SHORT).show() // TRADUS
        }

        btnStartCleanup.setOnClickListener {
            selectedPin?.let { pin ->
                val requestBody = StartCleanupRequest(
                    latitude = pin.position.latitude,
                    longitude = pin.position.longitude,
                    zoneName = pin.title
                )
                Toast.makeText(context, "Creating session...", Toast.LENGTH_SHORT).show() // TRADUS
                ApiClient.apiService.startIndividualCleanup(requestBody).enqueue(object : Callback<StartCleanupResponse> {
                    override fun onResponse(call: Call<StartCleanupResponse>, response: Response<StartCleanupResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Session started! Go to Profile (Active Sessions).", Toast.LENGTH_LONG).show() // TRADUS
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                            selectedPin = null
                            // TODO: Change red pin to yellow (in progress)
                        } else {
                            Toast.makeText(context, "Server error: ${response.message()}", Toast.LENGTH_SHORT).show() // TRADUS
                            Log.e("START_CLEANUP", "Error: ${response.code()}") // TRADUS
                        }
                    }
                    override fun onFailure(call: Call<StartCleanupResponse>, t: Throwable) {
                        Toast.makeText(context, "Network error: ${t.message}", Toast.LENGTH_SHORT).show() // TRADUS
                        Log.e("START_CLEANUP", "Failure: ${t.message}") // TRADUS
                    }
                })
            } ?: run {
                Toast.makeText(context, "Error: Selected area not found.", Toast.LENGTH_SHORT).show() // TRADUS
            }
        }
    }

    @SuppressLint("MissingPermission")
    private fun getCurrentLocation() {
        if (!checkPermissions()) return
        googleMap?.isMyLocationEnabled = true
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    val currentLatLng = LatLng(location.latitude, location.longitude)
                    googleMap?.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 15f))
                    Toast.makeText(context, "Location found!", Toast.LENGTH_SHORT).show() // TRADUS
                } else {
                    Toast.makeText(context, "Could not find location. Check if GPS is enabled.", Toast.LENGTH_LONG).show() // TRADUS
                }
            }
    }

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun showAboutDialog() {
        // TRADUS întregul dialog
        AlertDialog.Builder(requireContext())
            .setTitle("About Map Markers")
            .setMessage(
                "On the map you will find two types of markers:\n\n" +
                        "🔴 RED: A dirty area. Press it to start a cleanup session.\n\n" +
                        "🟢 GREEN: A recently cleaned area. Press it to see who cleaned it and when."
            )
            .setPositiveButton("I understand", null)
            .show()
    }

}