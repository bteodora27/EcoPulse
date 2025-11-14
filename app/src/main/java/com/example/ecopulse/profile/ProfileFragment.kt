package com.example.ecopulse.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.ecopulse.R

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_profile, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val btnSettings = view.findViewById<ImageView>(R.id.btn_settings)
        val tvUsername = view.findViewById<TextView>(R.id.tv_username)
        val tvRank = view.findViewById<TextView>(R.id.tv_rank)

        val statActivities = view.findViewById<TextView>(R.id.tv_stat_activities)
        val statPoints = view.findViewById<TextView>(R.id.tv_stat_points)

        val btnEcoCard = view.findViewById<LinearLayout>(R.id.btn_ecocard)
        val btnContinueActivity = view.findViewById<Button>(R.id.btn_continue_activity)
        val historyContainer = view.findViewById<LinearLayout>(R.id.history_container)

        // Exemplu
        tvUsername.text = "Andreea Popescu"
        tvRank.text = "Agentul Verde"
        statActivities.text = "12"
        statPoints.text = "1580"

        addHistoryItem(historyContainer, "Parcul Cișmigiu - Zona Nord", "18 Iunie 2024", "+50 Puncte")
        addHistoryItem(historyContainer, "Pădurea Băneasa", "05 Iunie 2024", "+120 Puncte")
        addHistoryItem(historyContainer, "Parcul Tineretului", "22 Mai 2024", "+75 Puncte")
    }

    private fun addHistoryItem(container: LinearLayout, title: String, date: String, points: String) {
        val item = LayoutInflater.from(requireContext())
            .inflate(R.layout.item_history, container, false)

        item.findViewById<TextView>(R.id.tv_history_title).text = title
        item.findViewById<TextView>(R.id.tv_history_date).text = date
        item.findViewById<TextView>(R.id.tv_history_points).text = points

        container.addView(item)
    }
}
