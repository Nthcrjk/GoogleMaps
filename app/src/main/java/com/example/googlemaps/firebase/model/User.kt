package com.example.googlemaps.firebase.model

data class User(
    val name: String = "",
    val secondName: String = "",
    val email: String = "",
    val password: String = "",
    val org: String = "",
    var currentLocation: Marker = Marker(),
    var userUID: String = "",
    var usersTeam: String = ""
)
