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
// Importăm noile modele API
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

class HartaFragment : Fragment(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheetTitle: TextView
    private lateinit var bottomSheetDescription: TextView
    private lateinit var btnStartCleanup: MaterialButton
    private var selectedPin: MapPin? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    // Launcher-ul de permisiuni (corpul era gol, l-am completat)
    private val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { permissions ->
        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true ||
            permissions[Manifest.permission.ACCESS_COARSE_LOCATION] == true
        ) {
            // Dacă a dat permisiunea, încercăm din nou să luăm locația
            getCurrentLocation()
        } else {
            Toast.makeText(context, "Permisiunea pentru locație a fost refuzată.", Toast.LENGTH_SHORT).show()
        }
    }

    // Piunezele de test pentru Timișoara
    data class MapPin(
        val position: LatLng,
        val title: String,
        val description: String,
        val isClean: Boolean
    )
    private val pins = listOf(
        MapPin(LatLng(45.7575, 21.2288), "Zona Piața Unirii", "Nu a fost curățată niciodată.", false),
        MapPin(LatLng(45.7535, 21.2255), "Piața Victoriei", "Curățat de @teodora acum 1 săptămână", true),
        MapPin(LatLng(45.7472, 21.2262), "Parcul Catedralei", "Murdar (raportat recent)", false)
    )

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

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
        map.setOnMarkerClickListener { marker ->
            val pin = marker.tag as? MapPin
            if (pin != null) {
                selectedPin = pin
                updateBottomSheet(pin)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
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
        pins.forEach { pin ->
            val icon = if (pin.isClean) BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            else BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            googleMap?.addMarker(MarkerOptions().position(pin.position).title(pin.title).icon(icon))?.tag = pin
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
            Toast.makeText(context, "Report Area clicked", Toast.LENGTH_SHORT).show()
        }

        btnStartCleanup.setOnClickListener {
            selectedPin?.let { pin ->
                val requestBody = StartCleanupRequest(
                    latitude = pin.position.latitude,
                    longitude = pin.position.longitude,
                    zoneName = pin.title
                )
                Toast.makeText(context, "Se creează sesiunea...", Toast.LENGTH_SHORT).show()
                ApiClient.apiService.startIndividualCleanup(requestBody).enqueue(object : Callback<StartCleanupResponse> {
                    override fun onResponse(call: Call<StartCleanupResponse>, response: Response<StartCleanupResponse>) {
                        if (response.isSuccessful) {
                            Toast.makeText(context, "Sesiune începută! Mergi la Profil (Sesiuni Active).", Toast.LENGTH_LONG).show()
                            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
                            selectedPin = null
                            // TODO: Schimbă piuneza roșie în galbenă (în progres)
                        } else {
                            Toast.makeText(context, "Eroare server: ${response.message()}", Toast.LENGTH_SHORT).show()
                            Log.e("START_CLEANUP", "Eroare: ${response.code()}")
                        }
                    }
                    override fun onFailure(call: Call<StartCleanupResponse>, t: Throwable) {
                        Toast.makeText(context, "Eroare rețea: ${t.message}", Toast.LENGTH_SHORT).show()
                        Log.e("START_CLEANUP", "Failure: ${t.message}")
                    }
                })
            } ?: run {
                Toast.makeText(context, "Eroare: Zona selectată nu a fost găsită.", Toast.LENGTH_SHORT).show()
            }
        }
    } // <-- AICI SE TERMINA setupButtonListeners

    // =================================================================
    // ▼▼▼ ACESTE FUNCȚII AU FOST DUPLICATE/GOLITE ÎN CODUL TĂU ▼▼▼
    // =================================================================

    private fun checkPermissions(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
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
                    Toast.makeText(context, "Locație găsită!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Nu am putut găsi locația. Verifică dacă GPS-ul e pornit.", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun showAboutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Despre Marcajele Hărții")
            .setMessage(
                "Pe hartă veți găsi două tipuri de marcaje:\n\n" +
                        "🔴 ROȘU: O zonă murdară. Apasă pe ea pentru a începe o sesiune de curățenie.\n\n" +
                        "🟢 VERDE: O zonă curățată recent. Apasă pe ea pentru a vedea cine și când a curățat-o."
            )
            .setPositiveButton("Am înțeles", null)
            .show()
    }

} // <-- AICI SE TERMINĂ CLASA HartaFragment