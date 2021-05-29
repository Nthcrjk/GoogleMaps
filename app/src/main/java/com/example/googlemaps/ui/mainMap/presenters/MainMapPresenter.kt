package com.example.googlemaps.ui.mainMap.presenters


import android.util.Log
import com.example.googlemaps.firebase.model.Date
import com.example.googlemaps.firebase.model.RoadItem
import com.example.googlemaps.firebase.model.Time
import com.example.googlemaps.firebase.model.User
import com.example.googlemaps.ui.mainMap.view.MainMapView
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.Polyline
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import moxy.MvpPresenter
import kotlin.collections.ArrayList

class MainMapPresenter(): MvpPresenter<MainMapView>() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val mAuthBase = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.uid.toString())

    var markers: ArrayList<Marker> = ArrayList()
    var line: Polyline? = null
    val ROAD: String = "road"

    fun getUserStatus(){
        val vListener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

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