package com.example.googlemaps.ui.mainMap.fragments

import android.Manifest
import android.content.Context
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
import com.example.googlemaps.ui.main.MainActivity
import com.example.googlemaps.R
import com.example.googlemaps.firebase.model.Date
import com.example.googlemaps.firebase.model.RoadItem
import com.example.googlemaps.firebase.model.Time
import com.example.googlemaps.ui.mainMap.dialogs.SaveRoadDialogFragment
import com.example.googlemaps.ui.mainMap.presenters.MainMapPresenter
import com.example.googlemaps.ui.mainMap.view.BeautyTimePickerDialogListener
import com.example.googlemaps.ui.mainMap.view.MainMapView
import com.example.googlemaps.ui.mainMap.view.SaveRoadDialogListener
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import kotlinx.android.synthetic.main.fragment_main_map.view.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import java.util.*
import kotlin.collections.ArrayList

class MainMapFragment constructor() : MvpAppCompatFragment(), OnMapReadyCallback, MainMapView {

    private val presenter by moxyPresenter { MainMapPresenter() }
    private var map: GoogleMap? = null
    private var cameraPosition: CameraPosition? = null
    private val defaultLocation = LatLng(44.41824719212245, 38.207623176276684)
    private var locationPermissionGranted = false
    private var lastKnownLocation: Location? = null
    private var roadItem: RoadItem? = null
    private lateinit var activityContext: Context

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private var beautyListener = object : BeautyTimePickerDialogListener {
        override fun saveRoad(name: String, date: Date, time: Time) {
            presenter.saveRoad(name, date, time)
        }
    }

    private var saveRoadDialogFragment = SaveRoadDialogFragment.newInstance(beautyListener)

    constructor(roadItem: RoadItem) : this() {
        this.roadItem = roadItem
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

        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity)
        activityContext = context!!

        presenter.getUserStatus()
        view.create_transit_button.setOnClickListener {
            presenter.showPoly()
        }

        view.gps.setOnClickListener {
            Log.e("gaf", "kowka")
            gaf()
        }

        view.save_transit_button.setOnClickListener {
            if (presenter.line == null){
                Toast.makeText(context, "Маршрут не построен", Toast.LENGTH_LONG).show()
            } else {
                saveRoadDialogFragment.show(childFragmentManager, SaveRoadDialogFragment.TAG)
            }
        }

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        return view
    }

    fun gaf(){
        if (ActivityCompat.checkSelfPermission(
                        activityContext, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(activityContext, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity!!, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 101)
            return
        }
        var task: Task<Location> = fusedLocationProviderClient.lastLocation
        task.addOnSuccessListener {
            Log.e("gaf", "meowmoew")
            if (it != null){
                map?.isMyLocationEnabled = true
                presenter.currentLocation = it
                Log.e("gaf", presenter.currentLocation?.latitude.toString() + presenter.currentLocation?.longitude.toString())
                presenter.currentLocation?.let { getDeviceLocation(it) }
                presenter.addCurrentLocation()
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        map?.let { map ->
            outState.putParcelable(KEY_CAMERA_POSITION, map.cameraPosition)
            outState.putParcelable(KEY_LOCATION, lastKnownLocation)
        }
        super.onSaveInstanceState(outState)
    }

    override fun disableAllButtons(){
        view?.save_transit_button?.visibility = View.GONE
        view?.save_transit_button?.isEnabled = false

        view?.create_transit_button?.visibility = View.GONE
        view?.create_transit_button?.isEnabled = false
    }

    override fun showOrgButtons() {
        view!!.gps.visibility = View.VISIBLE
        view!!.users.visibility = View.VISIBLE
        view!!.create_transit_button.visibility = View.VISIBLE
        view!!.save_transit_button.visibility = View.VISIBLE
    }

    override fun showNotOrgButtons() {
        view!!.gps.visibility = View.VISIBLE
    }

    override fun onMapReady(p0: GoogleMap) {
        this.map = p0



        map?.setOnMapClickListener {
            var marker = map?.addMarker(MarkerOptions().position(it))
            presenter.markers.add(marker!!)
            presenter.hidePoly()
        }

        map?.setOnMarkerClickListener {
            presenter.markers.remove(it)
            it.remove()
            presenter.hidePoly()
            true
        }


        loadRoad()

        getLocationPermission()
        updateLocationUI()
        getDeviceLocation()
    }

    private fun loadRoad(){
        if (roadItem != null) {
            roadItem?.markerList?.forEach {
                var item = map?.addMarker(MarkerOptions().position(LatLng(it.point1, it.point2)))
                presenter.markers.add(item!!)
            }
        }
        presenter.showPoly()
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

    private fun getDeviceLocation(place: Location) {
        try {
            map?.moveCamera(
                    CameraUpdateFactory
                            .newLatLngZoom(LatLng(place.latitude, place.longitude), DEFAULT_ZOOM.toFloat()))
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

    override fun showPolyLines(list: ArrayList<Marker>) {
        presenter.hidePoly()
        val poly = PolylineOptions().width(5f).color(Color.BLUE).geodesic(true)
        list.forEach {
            poly.add(it.position)
        }
        presenter.line = map?.addPolyline(poly)
    }

    override fun hidePolyLines() {
        presenter.line?.remove()
        presenter.line = null
    }


}