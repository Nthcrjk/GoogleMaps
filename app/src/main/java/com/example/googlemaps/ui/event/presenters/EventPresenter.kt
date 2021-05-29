package com.example.googlemaps.ui.event.presenters

import android.util.Log
import com.example.googlemaps.common.BasePresenter
import com.example.googlemaps.firebase.model.RoadItem
import com.example.googlemaps.firebase.model.User
import com.example.googlemaps.ui.event.view.EventView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import moxy.MvpPresenter

class EventPresenter: BasePresenter<EventView>() {

    val mDataBase = FirebaseDatabase.getInstance().getReference("road")
    val roadList: ArrayList<RoadItem> = ArrayList()

    var isOrg: Boolean = false


    override fun attachView(view: EventView?) {
        super.attachView(view)
    }

    fun removeRoadFromDB(){
        val vListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                //snapshot.
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
        TODO("Not yet implemented")
    }

}