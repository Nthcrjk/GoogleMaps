package com.example.googlemaps.ui.mainMap.view

import com.example.googlemaps.firebase.model.Marker


interface UsersOnWayAdapterListener {
    fun showUsersLocation(position: Marker)
}