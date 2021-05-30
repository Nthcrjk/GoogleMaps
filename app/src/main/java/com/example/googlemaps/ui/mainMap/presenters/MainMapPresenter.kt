package com.example.googlemaps.ui.mainMap.presenters


import android.location.Location
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
    var currentLocation: Location? = null

    var roadItem: RoadItem? = null
    var usersList: ArrayList<User> = ArrayList()

    lateinit var mainUser: User

    fun loadUseresFromRoad(roadItem: RoadItem){
        FirebaseDatabase.getInstance().getReference("Users")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    snapshot.children.forEach {
                        var user: User = User()
                        it.children.forEach {
                            user = it.getValue(User::class.java)!!
                        }


                        if (roadItem.usersUid.contains(it.key)) {
                            usersList.add(user)
                            viewState.setUsersAdapter()
                            Log.e("sobjaka", "sdsd" + usersList.last().name)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }

            })
    }

    override fun getUserStatus(){
        val vListener = object: ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                mainUser = snapshot.children.first().getValue(User::class.java)!!
                isOrg = mainUser.org
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

    fun addCurrentLocation(){
        FirebaseDatabase.getInstance().getReference("Users").child(mAuth.uid!!)
            .addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    var user: User = snapshot.children.first().getValue(User::class.java)!!
                    user.currentLocation = com.example.googlemaps.firebase.model.Marker(currentLocation!!.latitude, currentLocation!!.longitude)
                    mAuthBase.child(snapshot.children.first().key!!).setValue(user)
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
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