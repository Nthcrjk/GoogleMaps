package com.example.googlemaps.ui.event.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.googlemaps.R
import com.example.googlemaps.ui.event.presenters.EventPresenter
import com.example.googlemaps.ui.event.view.EventAdapterListener
import com.example.googlemaps.ui.event.view.EventView
import kotlinx.android.synthetic.main.fragment_event.view.*
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter

class EventFragment() : MvpAppCompatFragment(), EventView, EventAdapterListener {

    private val presenter: EventPresenter by moxyPresenter { EventPresenter() }

    private lateinit var myContext: Context

    //private lateinit var adapter: EventAdapter
    private lateinit var manager: LinearLayoutManager

    override fun onAttach(context: Context) {
        super.onAttach(context)
        myContext = context
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        var view =  inflater.inflate(R.layout.fragment_event, container, false)
        manager = LinearLayoutManager(myContext)

        //view.road_list.adapter = adapter
        view.road_list.layoutManager = manager
        return view
    }

}