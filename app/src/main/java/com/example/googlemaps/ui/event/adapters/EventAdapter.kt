package com.example.googlemaps.ui.event.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.googlemaps.R
import com.example.googlemaps.firebase.model.RoadItem
import com.example.googlemaps.ui.event.view.EventAdapterListener
import com.example.googlemaps.interfaces.BottomNavigationListener
import kotlinx.android.synthetic.main.road_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class EventAdapter(listener: EventAdapterListener, states: ArrayList<RoadItem>): RecyclerView.Adapter<EventAdapter.ViewHolder>() {

    private var states = states
    private val listener: EventAdapterListener = listener
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.road_item, parent, false)
        context = parent.context
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val state = states[position]
        holder.nameOfEvent.text = state.name
        holder.itemView.setOnClickListener {
            (context as BottomNavigationListener).selectTab(R.id.navigation_home, state)
        }
        holder.deleteIcon.setOnClickListener {
            listener.removeRoadFromDB()
        }
    }

    override fun getItemCount(): Int {
        return states.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameOfEvent: TextView = itemView.event_name
        val deleteIcon: ImageView = itemView.delete_icon
    }
}