package com.example.googlemaps.ui.event.view

interface EventAdapterListener {
    fun removeRoadFromDB(roadId: String)
    fun addUserToRoad(roadId: String)
}