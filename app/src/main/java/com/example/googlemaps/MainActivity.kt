package com.example.googlemaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.googlemaps.dataBaseApi.model.RoadItem
import com.example.googlemaps.mainMap.fragments.MainMapFragment
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity(), OnNavigationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        //mapFragment?.getMapAsync(this)

        navigateTo(MainMapFragment())

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {
                }
                R.id.navigation_events -> {
                }
                R.id.navigation_profile ->{

                }
            }
            true
        }
    }

    override fun navigateTo(fragment: Fragment) {
        val ftrance = supportFragmentManager.beginTransaction()
        ftrance.replace(R.id.content, fragment)
        ftrance.commitNow()
    }

    override fun selectTab(tabId: Int) {
        bottom_navigation.selectedItemId = tabId
        when(tabId){
            R.id.navigation_home -> {
            }
            R.id.navigation_events -> {
            }
            R.id.navigation_profile ->{

            }
        }
    }

    override fun selectTab(tabId: Int, road: RoadItem) {
        when(tabId){
            R.id.navigation_home -> {
            }
            R.id.navigation_events -> {
            }
            R.id.navigation_profile ->{

            }
        }
    }

}