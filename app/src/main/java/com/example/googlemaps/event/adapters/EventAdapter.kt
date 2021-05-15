package com.example.googlemaps.event.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.googlemaps.OnNavigationListener
import com.example.googlemaps.R
import com.example.googlemaps.dataBaseApi.model.RoadItem
import com.example.googlemaps.event.view.EventAdapterListener
import kotlinx.android.synthetic.main.road_item.view.*
import java.util.*

class EventAdapter(listener: EventAdapterListener, states: LinkedList<RoadItem>): RecyclerView.Adapter<EventAdapter.ViewHolder>() {

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
            (context as OnNavigationListener).selectTab(R.id.navigation_home, state)
        }
        holder.deleteIcon.setOnClickListener {
            states.remove(state)
            notifyDataSetChanged()
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