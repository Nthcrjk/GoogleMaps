package com.example.googlemaps.ui.mainMap.presenters


import com.example.googlemaps.firebase.model.Date
import com.example.googlemaps.firebase.model.RoadItem
import com.example.googlemaps.firebase.model.Time
import com.example.googlemaps.firebase.model.User
import com.example.googlemaps.ui.mainMap.view.MainMapView
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.google.firebase.database.FirebaseDatabase
import moxy.MvpPresenter
import kotlin.collections.ArrayList

class MainMapPresenter(): MvpPresenter<MainMapView>() {

    var markers: ArrayList<Marker> = ArrayList()

    var line: Polyline? = null

    val ROAD: String = "road"

    fun saveRoad(roadName: String, date: Date, time: Time){
        var modelMarkers: ArrayList<com.example.googlemaps.firebase.model.Marker> = ArrayList(markers.size)
        markers.forEach {
            modelMarkers.add(com.example.googlemaps.firebase.model.Marker(it.position.latitude, it.position.longitude))
        }
        val item: RoadItem = RoadItem(roadName, modelMarkers, date, time)
        FirebaseDatabase.getInstance().getReference(ROAD).push().setValue(item)
    }

    fun hidePoly(){
        viewState.hidePolyLines()
    }

    fun showPoly(){
        viewState.showPolyLines(markers)
    }
}