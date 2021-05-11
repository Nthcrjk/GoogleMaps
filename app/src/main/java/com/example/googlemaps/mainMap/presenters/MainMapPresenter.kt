package com.example.googlemaps.mainMap.presenters


import com.example.googlemaps.dataBaseApi.model.RoadItem
import com.example.googlemaps.mainMap.view.MainMapView
import com.google.android.gms.maps.model.Marker
import moxy.MvpPresenter
import java.util.*

class MainMapPresenter(): MvpPresenter<MainMapView>() {

    var markers: LinkedList<Marker> = LinkedList()


    override fun onFirstViewAttach() {

    }

    fun save(roadName: String){
        var roadItem: RoadItem = RoadItem(roadName, markers)
    }


    fun showPoly(list: LinkedList<Marker>){
        markers = list
        viewState.showPolyLines(markers)
    }
}