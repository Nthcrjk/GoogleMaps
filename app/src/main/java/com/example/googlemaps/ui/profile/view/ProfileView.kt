package com.example.googlemaps.ui.profile.view

import com.example.googlemaps.firebase.model.User
import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ProfileView: MvpView{
    fun setUsersFields(user: User)

}