package com.example.googlemaps.ui.profile.view

import moxy.MvpView
import moxy.viewstate.strategy.alias.AddToEndSingle

@AddToEndSingle
interface ProfileView: MvpView{
    fun showOrHideViews(hide: Boolean)
}