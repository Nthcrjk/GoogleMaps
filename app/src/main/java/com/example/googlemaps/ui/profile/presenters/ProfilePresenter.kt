package com.example.googlemaps.ui.profile.presenters

import com.example.googlemaps.ui.profile.view.ProfileView
import com.google.firebase.auth.FirebaseAuth
import moxy.MvpPresenter

class ProfilePresenter: MvpPresenter<ProfileView>() {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    override fun attachView(view: ProfileView?) {
        super.attachView(view)

        if (mAuth.currentUser != null) {
            viewState.showOrHideViews(false)
        } else {
            viewState.showOrHideViews(true)
        }
    }
}