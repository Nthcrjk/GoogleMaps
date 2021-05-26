package com.example.googlemaps.ui.mainMap.view

import com.example.googlemaps.firebase.model.Date
import com.example.googlemaps.firebase.model.Time

interface BeautyTimePickerDialogListener {
    fun saveRoad(name: String, date: Date, time: Time)
}