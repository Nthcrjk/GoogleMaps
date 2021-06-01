package com.example.googlemaps.ui.mainMap.view

import com.google.android.gms.maps.model.Marker
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle
import java.util.*
import kotlin.collections.ArrayList

@AddToEndSingle
interface MainMapView: MvpView {
    fun showPolyLines(list: ArrayList<Marker>)
    fun hidePolyLines()
    fun disableAllButtons()
    fun showOrgButtons()
    fun showNotOrgButtons()
    fun setUsersAdapter()
    fun showGroupsBtn()
    fun hideGroupsBtn()
    fun disableMarkers()
}