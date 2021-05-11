package com.example.googlemaps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        //mapFragment?.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {

    }
}