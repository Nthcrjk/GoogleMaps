package com.example.googlemaps.ui.main

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.googlemaps.interfaces.BottomNavigationListener
import com.example.googlemaps.interfaces.OnNavigationListener
import com.example.googlemaps.R
import com.example.googlemaps.dataBaseApi.model.RoadItem
import com.example.googlemaps.firebase.model.User
import com.example.googlemaps.ui.event.fragments.EventFragment
import com.example.googlemaps.ui.mainMap.fragments.MainMapFragment
import com.example.googlemaps.ui.profile.fragments.ProfileFragment
import com.example.googlemaps.ui.profile.presenters.ProfilePresenter
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity() : AppCompatActivity(), OnNavigationListener, BottomNavigationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateTo(MainMapFragment(), false)

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {
                    navigateTo(MainMapFragment(), false)
                }
                R.id.navigation_events -> {
                    navigateTo(EventFragment(), false)
                }
                R.id.navigation_profile ->{
                    navigateTo(ProfileFragment(), false)
                }
            }
            true
        }
    }

    override fun navigateTo(fragment: Fragment, isAddToBackStack: Boolean) {
        val fTrance = supportFragmentManager.beginTransaction()
        fTrance.replace(R.id.content, fragment)
        if (isAddToBackStack){
            fTrance.addToBackStack("")
        }
        fTrance.commit()
    }

    override fun selectTab(tabId: Int) {
        bottom_navigation.selectedItemId = tabId
        when(tabId){
            R.id.navigation_home -> {
                navigateTo(MainMapFragment(), false)
            }
            R.id.navigation_events -> {
                navigateTo(EventFragment(), false)
            }
            R.id.navigation_profile ->{

            }
        }
    }

    override fun selectTab(tabId: Int, road: RoadItem) {
        when(tabId){
            R.id.navigation_home -> {
                navigateTo(MainMapFragment(), false)
            }
            R.id.navigation_events -> {
                navigateTo(EventFragment(), false)
            }
            R.id.navigation_profile ->{

            }
        }
    }
}