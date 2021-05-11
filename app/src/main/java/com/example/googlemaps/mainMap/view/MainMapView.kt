package com.example.googlemaps.mainMap.view

import com.google.android.gms.maps.model.Marker
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import java.util.*

@AddToEndSingle
interface MainMapView: MvpView {
    fun showPolyLines(list: LinkedList<Marker>)
}