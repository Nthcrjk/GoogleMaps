package com.example.googlemaps.dataBaseApi.model

import com.google.android.gms.maps.model.Marker
import java.util.*

data class RoadItem(
    var name: String,
    var markerList: LinkedList<Marker>
)
