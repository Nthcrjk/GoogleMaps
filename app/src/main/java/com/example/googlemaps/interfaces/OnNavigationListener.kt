package com.example.googlemaps.interfaces

import android.app.Activity
import androidx.fragment.app.Fragment
import com.example.googlemaps.dataBaseApi.model.RoadItem
import com.example.googlemaps.ui.main.MainActivity

interface OnNavigationListener {
    fun navigateTo(fragment: Fragment, isAddToBackStack: Boolean)
}