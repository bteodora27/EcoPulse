package com.example.ecopulse.events

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ecopulse.R

class EventAdapter(
    private val events: List<Event>,
    private val onEventClick: (Event) -> Unit,
    private val onJoinClick: (Event) -> Unit
) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

    class EventViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val img: ImageView = view.findViewById(R.id.event_image)
        val title: TextView = view.findViewById(R.id.event_title)
        val participants: TextView = view.findViewById(R.id.event_participants)
        val dateLocation: TextView = view.findViewById(R.id.event_date_location)
        val btnJoin: Button = view.findViewById(R.id.btn_join_event)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }

    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {
        val event = events[position]

        holder.title.text = event.title
        holder.participants.text = event.participants
        holder.dateLocation.text = event.dateLocation

        // 🔥 IMAGINE LOCALĂ SAU URL
        when {
            event.imageRes != null -> {
                holder.img.setImageResource(event.imageRes)
            }
            event.imageUrl != null -> {
                Glide.with(holder.itemView.context)
                    .load(event.imageUrl)
                    .placeholder(R.drawable.placeholder) // optional
                    .into(holder.img)
            }
        }

        // 🔥 DESIGN buton Join
        holder.btnJoin.apply {
            text = if (event.joined) "Joined" else "Join"
            setBackgroundResource(
                if (event.joined) R.drawable.bg_joined_button else R.drawable.bg_join_button
            )
        }

        // Join click
        holder.btnJoin.setOnClickListener {
            onJoinClick(event)
            notifyItemChanged(position)
        }

        // Click pe card
        holder.itemView.setOnClickListener {
            onEventClick(event)
        }
    }

    override fun getItemCount(): Int = events.size
}
