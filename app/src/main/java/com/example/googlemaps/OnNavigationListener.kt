package com.example.googlemaps

import androidx.fragment.app.Fragment
import com.example.googlemaps.dataBaseApi.model.RoadItem

interface OnNavigationListener {
    fun navigateTo(fragment: Fragment)
    fun selectTab(tabId: Int)
    fun selectTab(tabId: Int, road: RoadItem)
}