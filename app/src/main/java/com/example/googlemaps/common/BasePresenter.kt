package com.example.googlemaps.common

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import moxy.MvpPresenter
import moxy.MvpView

abstract class BasePresenter<View: MvpView>: MvpPresenter<View>() {

    val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    val mAuthBase = FirebaseDatabase.getInstance().getReference("Users").child(mAuth.uid.toString())

    abstract fun getUserStatus()
}