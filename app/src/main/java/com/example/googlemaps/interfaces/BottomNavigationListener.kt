package com.example.googlemaps.interfaces

import com.example.googlemaps.dataBaseApi.model.RoadItem

interface BottomNavigationListener {
    fun selectTab(tabId: Int)
    fun selectTab(tabId: Int, road: RoadItem)
}