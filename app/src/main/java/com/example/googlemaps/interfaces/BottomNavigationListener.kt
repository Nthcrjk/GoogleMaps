package com.example.googlemaps.interfaces

import com.example.googlemaps.firebase.model.RoadItem

interface BottomNavigationListener {
    fun selectTab(tabId: Int)
    fun selectTab(tabId: Int, road: RoadItem)
}