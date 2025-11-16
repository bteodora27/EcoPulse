package com.example.ecopulse.events

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.ecopulse.R
import com.google.android.material.card.MaterialCardView

class EventsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_events, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        // --- LISTA DE EVENIMENTE ---
        val recycler = view.findViewById<RecyclerView>(R.id.recycler_events)
        recycler.layoutManager = LinearLayoutManager(requireContext())

        recycler.adapter = EventAdapter(
            EventRepository.events,
            onEventClick = { event ->
                val intent = Intent(requireContext(), EventDetailsActivity::class.java)
                intent.putExtra("event_id", event.id)
                startActivity(intent)
            },
            onJoinClick = { event ->
                event.joined = true
            }
        )

        // --- CARD PROMO AUTUMN ---
        val cardAutumn = view.findViewById<MaterialCardView>(R.id.card_autumn)
        cardAutumn?.setOnClickListener {
            val intent = Intent(requireContext(), AutumnPromoActivity::class.java)
            startActivity(intent)
        }
    }
}
