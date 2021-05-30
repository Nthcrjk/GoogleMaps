package com.example.googlemaps.ui.mainMap.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.RecyclerView
import com.example.googlemaps.R
import com.example.googlemaps.firebase.model.User
import com.example.googlemaps.ui.event.adapters.EventAdapter
import com.example.googlemaps.ui.mainMap.view.UsersOnWayAdapterListener
import kotlinx.android.synthetic.main.road_item.view.*
import kotlinx.android.synthetic.main.user_item.view.*

class UsersOnWayAdapter(var users: ArrayList<User>, var listener: UsersOnWayAdapterListener, var dialog: DialogFragment): RecyclerView.Adapter<UsersOnWayAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.user_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var state = users[position]
        Log.e("sobjaka", "dsd" + state.name)
        holder.nameTextView.text = state.name
        holder.secondNameTextView.text = state.secondName

        holder.itemView.user_view.setOnClickListener {
            listener.showUsersLocation(state.currentLocation)
            dialog.dismiss()
        }

    }

    override fun getItemCount(): Int {
        return users.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        var nameTextView = itemView.textView2
        var secondNameTextView = itemView.textView3
    }
}