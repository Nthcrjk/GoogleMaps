package com.example.googlemaps.ui.event.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.googlemaps.R
import com.example.googlemaps.ui.event.adapters.EventAdapter
import com.example.googlemaps.ui.event.presenters.EventPresenter
import com.example.googlemaps.ui.event.view.EventAdapterListener
import com.example.googlemaps.ui.event.view.EventView
import kotlinx.android.synthetic.main.fragment_event.view.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class EventFragment() : MvpAppCompatFragment(), EventView {

    private val presenter: EventPresenter by moxyPresenter { EventPresenter() }
    private lateinit var myContext: Context
    private lateinit var adapter: EventAdapter
    private lateinit var manager: LinearLayoutManager

    private var eventAdapterListener = object : EventAdapterListener {
        override fun removeRoadFromDB(roadId: String) {
            presenter.removeRoadFromDB(roadId)
        }

        override fun addUserToRoad(roadId: String) {
            presenter.addUserToRoad(roadId)
        }
    }
    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view =  inflater.inflate(R.layout.fragment_event, container, false)

        presenter.getUserStatus()
        manager = LinearLayoutManager(myContext)
        adapter = EventAdapter(eventAdapterListener, presenter.roadList)

        return view
    }

    override fun setAdapter() {
        view?.road_list?.layoutManager = manager
        view?.road_list?.adapter = adapter
    }
}