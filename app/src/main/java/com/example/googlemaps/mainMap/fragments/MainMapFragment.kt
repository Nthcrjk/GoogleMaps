package com.example.googlemaps.mainMap.fragments

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.googlemaps.MainActivity
import com.example.googlemaps.R
import com.example.googlemaps.dataBaseApi.model.RoadItem
import com.example.googlemaps.mainMap.dialogs.SaveRoadDialogFragment
import com.example.googlemaps.mainMap.presenters.MainMapPresenter
import com.example.googlemaps.mainMap.view.MainMapView
import com.example.googlemaps.mainMap.view.SaveRoadDialogListener

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import kotlinx.android.synthetic.main.fragment_main_map.*
import kotlinx.android.synthetic.main.fragment_main_map.view.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.util.*
import kotlin.math.log

class MainMapFragment constructor() : MvpAppCompatFragment(), OnMapReadyCallback, SaveRoadDialogListener, MainMapView {

    private val presenter by moxyPresenter { MainMapPresenter() }

    private var saveRoadDialogFragment = SaveRoadDialogFragment.newInstance(this)

    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null

    private val defaultLocation = LatLng(44.41824719212245, 38.207623176276684)

    private var locationPermissionGranted = false

    private var lastKnownLocation: Location? = null

    private var line: Polyline? = null

    constructor(road: RoadItem): this(){
        presenter.markers = road.markerList

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            lastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION)
            cameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view = inflater.inflate(R.layout.fragment_main_map, container, false)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        view.button.setOnClickListener {
            presenter.markers.forEach {
                Log.e("gaf", it.position.toString())
            }
        }

        view.create_transit_button.setOnClickListener {
            presenter.showPoly(presenter.markers)
        }

        view.save_transit_button.setOnClickListener {
            if (presenter.markers.isEmpty()){
                Toast.makeText(context, "Маршрут не построен!", Toast.LENGTH_LONG).show()
            } else{
                saveRoadDialogFragment.show(childFragmentManager, SaveRoadDialogFragment.TAG)
            }
        }
        return view
    }

    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    fun disableAllButtons(){
        view?.save_transit_button?.visibility = View.GONE
        view?.save_transit_button?.isEnabled = false

        view?.create_transit_button?.visibility = View.GONE
        view?.create_transit_button?.isEnabled = false
    }

    override fun onMapReady(p0: GoogleMap) {
        this.map = p0


        if (!presenter.markers.isEmpty()) {
            presenter.markers.forEach {
                map?.addMarker(MarkerOptions().position(it.position))
                disableAllButtons()
            }
            showPolyLines(presenter.markers)

        } else {
            map?.setOnMapClickListener {
                var marker = map?.addMarker(MarkerOptions().position(it))
                presenter.markers.add(marker!!)
                Log.e("gaf", marker.position.toString())
            }

            map?.setOnMarkerClickListener {
                it.remove()
                presenter.markers.remove(it)

                if (line != null) {
                    line?.remove()
                }
                presenter.showPoly(LinkedList())
                true
            }
        }
        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()
    }

    private fun getDeviceLocation() {
        try {
            map?.moveCamera(
                CameraUpdateFactory
                .newLatLngZoom(defaultLocation, DEFAULT_ZOOM.toFloat()))
            map?.uiSettings?.isMyLocationButtonEnabled = false
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission((context as MainActivity).applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(context as MainActivity, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    locationPermissionGranted = true
                }
            }
        }
        updateLocationUI()
    }

    private fun updateLocationUI() {
        if (map == null) {
            return
        }
        try {
            if (locationPermissionGranted) {
                map?.isMyLocationEnabled = true
                map?.uiSettings?.isMyLocationButtonEnabled = true
            } else {
                map?.isMyLocationEnabled = false
                map?.uiSettings?.isMyLocationButtonEnabled = false
                lastKnownLocation = null
                getLocationPermission()
            }
        } catch (e: SecurityException) {
            Log.e("Exception: %s", e.message, e)
        }
    }

    companion object {
        private const val DEFAULT_ZOOM = 15
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val KEY_CAMERA_POSITION = "camera_position"
        private const val KEY_LOCATION = "location"
    }

    override fun showPolyLines(list: LinkedList<Marker>) {
        if (line != null){
            line?.remove()
        }
        val poly = PolylineOptions().width(5f).color(Color.BLUE).geodesic(true)
        list.forEach {
            poly.add(it.position)
        }
        line = map?.addPolyline(poly)
    }

    override fun saveRoad(roadName: String) {
        presenter.save(roadName)
    }
}