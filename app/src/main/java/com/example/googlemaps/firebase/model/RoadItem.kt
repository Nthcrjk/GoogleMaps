package com.example.googlemaps.firebase.model

import kotlin.collections.ArrayList

data class RoadItem(
    var name: String = "",
    var markerList: ArrayList<Marker> = ArrayList(),
    var date: Date = Date(),
    var time: Time = Time()
)

data class Marker(
    val point1: Double = 0.0,
    val point2: Double = 0.0
)
