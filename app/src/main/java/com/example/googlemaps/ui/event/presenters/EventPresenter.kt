package com.example.googlemaps.ui.event.presenters

import android.util.Log
import com.example.googlemaps.common.BasePresenter
import com.example.googlemaps.firebase.model.RoadItem
import com.example.googlemaps.firebase.model.User
import com.example.googlemaps.ui.event.view.EventView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import moxy.MvpPresenter

class EventPresenter: BasePresenter<EventView>() {

    val mDataBase = FirebaseDatabase.getInstance().getReference("road")
    val roadList: ArrayList<RoadItem> = ArrayList()
    private lateinit var mainUser: User
    var isOrg: Boolean = false


    override fun attachView(view: EventView?) {
        super.attachView(view)
    }

    fun addUserToRoad(roadsUid: String){
        FirebaseDatabase.getInstance().getReference("road").child(roadsUid)
            .addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
               var road: RoadItem = snapshot.getValue(RoadItem::class.java)!!
                road.usersUid.add(mAuth.uid.toString())
                mDataBase.child(roadsUid).setValue(road)
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    fun removeRoadFromDB(roadId: String){
        val vListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mDataBase.child(roadId).removeValue()
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        mDataBase.addValueEventListener(vListener)
    }

    fun getDataFromDb(){
        val vListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    var roadItem = it.getValue(RoadItem::class.java)
                    roadItem?.roadsUid = it.key!!
                    if (roadItem != null) {
                        roadList.add(roadItem)
                    }
                    viewState.setAdapter()
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        }
        mDataBase.addValueEventListener(vListener)
    }

    override fun getUserStatus() {
        val vListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mainUser = snapshot.children.first().getValue(User::class.java)!!
                getDataFromDb()
            }
            override fun onCancelled(error: DatabaseError) {

            }

        }
        mAuthBase.addValueEventListener(vListener)
    }

}