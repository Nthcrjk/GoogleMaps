package com.example.googlemaps.ui.mainMap.presenters


import android.util.Log
import com.example.googlemaps.common.BasePresenter
import com.example.googlemaps.firebase.model.Date
import com.example.googlemaps.firebase.model.RoadItem
import com.example.googlemaps.firebase.model.Time
import com.example.googlemaps.firebase.model.User
import com.example.googlemaps.ui.mainMap.view.MainMapView
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlin.collections.ArrayList

class MainMapPresenter(): BasePresenter<MainMapView>() {

    var markers: ArrayList<Marker> = ArrayList()
    var line: Polyline? = null
    val ROAD: String = "road"
    var isOrg: String? = null

    override fun getUserStatus(){
        Log.e("gaf", "2")
        val vListener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                isOrg = snapshot.children.first().getValue(User::class.java)?.org
                if (isOrg == "true"){
                    viewState.showOrgButtons()
                } else {
                    viewState.showNotOrgButtons()
                }
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        }
        mAuthBase.addValueEventListener(vListener)
    }

    override fun attachView(view: MainMapView?) {
        super.attachView(view)
    }

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