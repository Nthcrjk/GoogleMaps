package com.example.googlemaps.ui.profile.presenters

import android.util.Log
import com.example.googlemaps.common.BasePresenter
import com.example.googlemaps.firebase.model.User
import com.example.googlemaps.ui.profile.view.ProfileView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import moxy.MvpPresenter

class ProfilePresenter: BasePresenter<ProfileView>() {

    private lateinit var mainUser: User

    override fun attachView(view: ProfileView?) {
        super.attachView(view)
    }



    fun saveTeams(team: String){
        mAuthBase.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                var user = snapshot.children.first().getValue(User::class.java)!!
                user.usersTeam = team
                mAuthBase.child(snapshot.children.first().key!!).setValue(user)

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }

    override fun getUserStatus() {
        val vListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mainUser = snapshot.children.first().getValue(User::class.java)!!
                viewState.setUsersFields(mainUser)
            }
            override fun onCancelled(error: DatabaseError) {

            }

        }
        mAuthBase.addValueEventListener(vListener)
    }
}
