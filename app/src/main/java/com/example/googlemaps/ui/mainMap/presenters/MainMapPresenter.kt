package com.example.googlemaps.ui.mainMap.presenters


import com.example.googlemaps.ui.mainMap.view.MainMapView
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import moxy.MvpPresenter
import java.util.*

class MainMapPresenter(): MvpPresenter<MainMapView>() {

    var markers: LinkedList<Marker> = LinkedList()

    var line: Polyline? = null


    override fun onFirstViewAttach() {

    }

    fun save(roadName: String){

    }

    fun hidePoly(){
        viewState.hidePolyLines()
    }

    fun showPoly(){
        viewState.showPolyLines(markers)
    }
}