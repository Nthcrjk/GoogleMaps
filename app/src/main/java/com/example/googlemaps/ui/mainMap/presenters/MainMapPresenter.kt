package com.example.googlemaps.ui.mainMap.presenters


import com.example.googlemaps.ui.mainMap.view.MainMapView
import com.google.android.gms.maps.model.Marker
import moxy.MvpPresenter
import java.util.*

class MainMapPresenter(): MvpPresenter<MainMapView>() {

    var markers: LinkedList<Marker> = LinkedList()


    override fun onFirstViewAttach() {

    }

    fun save(roadName: String){

    }


    fun showPoly(list: LinkedList<Marker>){
        markers = list
        viewState.showPolyLines(markers)
    }
}