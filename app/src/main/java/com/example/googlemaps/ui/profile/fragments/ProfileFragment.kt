package com.example.googlemaps.ui.profile.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.googlemaps.R
import com.example.googlemaps.ui.auth.activity.AuthActivity
import com.example.googlemaps.ui.main.MainActivity
import com.example.googlemaps.ui.profile.presenters.ProfilePresenter
import com.example.googlemaps.ui.profile.view.ProfileView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_profile.view.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class ProfileFragment : MvpAppCompatFragment(), ProfileView {

    private val presenter by moxyPresenter { ProfilePresenter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        view.button2.setOnClickListener {
            FirebaseAuth.getInstance().signOut()
            val intent: Intent = Intent((context as MainActivity), AuthActivity::class.java)
            startActivity(intent)
        }
        return view
    }

    override fun showOrHideViews(hide: Boolean) {
        if (hide){
            view?.textView2?.visibility = View.GONE
            view?.textView3?.visibility = View.GONE
            view?.button2?.visibility = View.GONE
            view?.textView4?.visibility = View.VISIBLE
        } else {
            view?.textView2?.visibility = View.VISIBLE
            view?.textView3?.visibility = View.VISIBLE
            view?.button2?.visibility = View.VISIBLE
            view?.textView4?.visibility = View.GONE
        }
    }
}