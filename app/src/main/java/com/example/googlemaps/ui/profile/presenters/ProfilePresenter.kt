package com.example.googlemaps.ui.profile.presenters

import android.util.Log
import com.example.googlemaps.firebase.model.User
import com.example.googlemaps.ui.profile.view.ProfileView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import moxy.MvpPresenter

class ProfilePresenter: MvpPresenter<ProfileView>() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val mDataBase = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.uid.toString())

    private lateinit var mainUser: User

    override fun attachView(view: ProfileView?) {
        super.attachView(view)
    }

    fun getDataFromDB(){
        Log.e("gaf", "sdsdsd")
        val vListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mainUser = snapshot.children.first().getValue(User::class.java)!!
                viewState.setUsersFields(mainUser)
            }
            override fun onCancelled(error: DatabaseError) {

            }

        }
        mDataBase.addValueEventListener(vListener)
    }
}
