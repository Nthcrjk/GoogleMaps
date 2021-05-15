package com.example.googlemaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.room.Room
import com.example.googlemaps.dataBaseApi.model.RoadItem
import com.example.googlemaps.event.fragments.EventFragment
import com.example.googlemaps.mainMap.fragments.MainMapFragment
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity(), OnNavigationListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateTo(MainMapFragment())

        bottom_navigation.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.navigation_home -> {
                    navigateTo(MainMapFragment())
                }
                R.id.navigation_events -> {
                    navigateTo(EventFragment())
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
                navigateTo(MainMapFragment())
            }
            R.id.navigation_events -> {
                navigateTo(EventFragment())
            }
            R.id.navigation_profile ->{

            }
        }
    }

    override fun selectTab(tabId: Int, road: RoadItem) {
        when(tabId){
            R.id.navigation_home -> {
                navigateTo(MainMapFragment())
            }
            R.id.navigation_events -> {
                navigateTo(EventFragment())
            }
            R.id.navigation_profile ->{

            }
        }
    }

}