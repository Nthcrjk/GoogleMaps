package com.example.googlemaps.interfaces

import androidx.fragment.app.Fragment

interface OnNavigationListener {
    fun navigateTo(fragment: Fragment, isAddToBackStack: Boolean)
}