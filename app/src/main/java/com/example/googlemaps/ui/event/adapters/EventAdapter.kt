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
import com.example.googlemaps.firebase.model.User
import com.example.googlemaps.ui.event.view.EventAdapterListener
import com.example.googlemaps.interfaces.BottomNavigationListener
import kotlinx.android.synthetic.main.road_item.view.*
import java.util.*
import kotlin.collections.ArrayList

class EventAdapter(listener: EventAdapterListener, states: ArrayList<RoadItem>, var mainUser: User): RecyclerView.Adapter<EventAdapter.ViewHolder>() {

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

        var minutes: String = ""

        if (state.time.minute > 9){
            minutes = state.time.minute.toString()
        } else {
            minutes = "0" + state.time.minute.toString()
        }

        holder.itemView.textView.text = state.time.hourOfDay.toString() + ":" + minutes
        holder.itemView.date.text = state.date.dayOfMonth.toString() + "." + state.date.month.toString() + "." + state.date.year.toString()

        holder.itemView.setOnClickListener {
            (context as BottomNavigationListener).selectTab(R.id.navigation_home, state)
        }

        if (state.usersUid.contains(mainUser.userUID)){
            holder.itemView.button2.visibility = View.GONE
            holder.itemView.button2.isEnabled = false
            holder.itemView.button3.visibility = View.VISIBLE
            holder.itemView.button3.isEnabled = true
        } else {
            holder.itemView.button2.visibility = View.VISIBLE
            holder.itemView.button2.isEnabled = true
            holder.itemView.button3.visibility = View.GONE
            holder.itemView.button3.isEnabled = false
        }

        holder.itemView.button3.setOnClickListener {
            listener.unsubscribeUserFromRoad(mainUser.userUID, state.roadsUid)
            states.clear()
            notifyDataSetChanged()
            holder.itemView.button2.visibility = View.VISIBLE
            holder.itemView.button2.isEnabled = true
            holder.itemView.button3.visibility = View.GONE
            holder.itemView.button3.isEnabled = false
        }

        holder.itemView.button2.setOnClickListener {
            listener.addUserToRoad(state.roadsUid)
            states.clear()
            notifyDataSetChanged()
            holder.itemView.button2.visibility = View.GONE
            holder.itemView.button2.isEnabled = false
            holder.itemView.button3.visibility = View.VISIBLE
            holder.itemView.button3.isEnabled = true
        }

        holder.itemView.delete_icon.setOnClickListener {
            listener.removeRoadFromDB(state.roadsUid)
            states.clear()
            notifyDataSetChanged()
        }

        if (mainUser.org.contains("false")) {
            holder.itemView.delete_icon.visibility = View.GONE
        }
    }

    override fun getItemCount(): Int {
        return states.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val nameOfEvent: TextView = itemView.event_name

    }
}