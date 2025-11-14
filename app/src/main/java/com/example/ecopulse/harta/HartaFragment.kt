package com.example.ecopulse.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.ecopulse.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.button.MaterialButton
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton

class HartaFragment : Fragment(), OnMapReadyCallback {

    private var googleMap: GoogleMap? = null
    private lateinit var bottomSheetBehavior: BottomSheetBehavior<LinearLayout>
    private lateinit var bottomSheetTitle: TextView
    private lateinit var bottomSheetDescription: TextView
    private lateinit var btnStartCleanup: MaterialButton

    // Date model simple (le vei lua din baza de date)
    data class MapPin(
        val position: LatLng,
        val title: String,
        val description: String,
        val isClean: Boolean // true = verde, false = roșu
    )

    // Date de test
    private val pins = listOf(
        MapPin(LatLng(46.770439, 23.591423), "Zona Piata Unirii", "Nu a fost curățată niciodată.", false),
        MapPin(LatLng(46.768875, 23.584424), "Parcul Central", "Curățat de @teodora acum 2 săptămâni", true),
        MapPin(LatLng(46.773370, 23.619472), "Zona Piata Marasti", "Curățat acum 3 luni", false)
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inițializează Harta
        val mapFragment = childFragmentManager.findFragmentById(R.id.map_container) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        // Inițializează Panoul de Jos (Bottom Sheet)
        val bottomSheetLayout = view.findViewById<LinearLayout>(R.id.bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheetLayout)
        bottomSheetTitle = view.findViewById(R.id.bottom_sheet_title)
        bottomSheetDescription = view.findViewById(R.id.bottom_sheet_description)
        btnStartCleanup = view.findViewById(R.id.btn_start_cleanup)

        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        // Setează Listeners pentru butoane
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
                updateBottomSheet(pin)
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            true
        }

        map.setOnMapClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }

        val clujNapoca = LatLng(46.7712, 23.6236)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(clujNapoca, 13f))
    }

    private fun addMarkersToMap() {
        pins.forEach { pin ->
            val icon = if (pin.isClean) {
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)
            } else {
                BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
            }

            val marker = googleMap?.addMarker(
                MarkerOptions().position(pin.position).title(pin.title).icon(icon)
            )
            marker?.tag = pin
        }
    }

    private fun updateBottomSheet(pin: MapPin) {
        bottomSheetTitle.text = pin.title
        bottomSheetDescription.text = pin.description

        if (pin.isClean) {
            btnStartCleanup.visibility = View.GONE
        } else {
            btnStartCleanup.visibility = View.VISIBLE
        }
    }

    private fun setupButtonListeners(view: View) {

        // === MODIFICARE AICI: Butonul Meniu (About) ===
        view.findViewById<ImageButton>(R.id.btn_menu).setOnClickListener {
            showAboutDialog()
        }

        // Controalele hărții
        view.findViewById<ImageButton>(R.id.btn_zoom_in).setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomIn())
        }
        view.findViewById<ImageButton>(R.id.btn_zoom_out).setOnClickListener {
            googleMap?.animateCamera(CameraUpdateFactory.zoomOut())
        }
        view.findViewById<ImageButton>(R.id.btn_my_location).setOnClickListener {
            // TODO: Adaugă logica de permisiuni și Găsire Locație Curentă
            Toast.makeText(context, "Finding your location...", Toast.LENGTH_SHORT).show()
        }

        // Butoanele de acțiune
        view.findViewById<ExtendedFloatingActionButton>(R.id.fab_report_area).setOnClickListener {
            // TODO: Pornește fluxul de raportare zonă
            Toast.makeText(context, "Report Area clicked", Toast.LENGTH_SHORT).show()
        }

        // Logica pentru butonul de Start Cleanup (din panou)
        btnStartCleanup.setOnClickListener {
            // TODO: Pornește fluxul "Before/After"
            Toast.makeText(context, "Start Cleanup clicked!", Toast.LENGTH_SHORT).show()
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
    }

    private fun showAboutDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Despre Marcajele Hărții")
            .setMessage(
                "Pe hartă veți găsi două tipuri de marcaje:\n\n" +
                        "🔴 ROȘU: O zonă murdară care nu a fost curățată recent. Apasă pe ea pentru a începe o sesiune de curățenie.\n\n" +
                        "🟢 VERDE: O zonă curățată recent. Apasă pe ea pentru a vedea cine și când a curățat-o."
            )
            .setPositiveButton("Am înțeles", null)
            .show()
    }
}