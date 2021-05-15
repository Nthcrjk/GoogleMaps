package com.example.googlemaps.dataBaseApi.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.android.gms.maps.model.Marker
import java.util.*

data class RoadItem(
    var name: String,
    var markerList: LinkedList<Marker>
)
