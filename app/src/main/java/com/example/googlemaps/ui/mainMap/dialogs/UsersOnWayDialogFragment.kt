package com.example.googlemaps.ui.mainMap.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.googlemaps.R
import com.example.googlemaps.firebase.model.User
import com.example.googlemaps.ui.mainMap.adapter.UsersOnWayAdapter
import com.example.googlemaps.ui.mainMap.view.BeautyTimePickerDialogListener
import com.example.googlemaps.ui.mainMap.view.UsersOnWayAdapterListener
import kotlinx.android.synthetic.main.dialog_road_fragment.view.*
import kotlinx.android.synthetic.main.dialog_road_fragment.view.submit_button
import kotlinx.android.synthetic.main.dialog_users_on_way.view.*
import kotlinx.android.synthetic.main.user_item.view.*

class UsersOnWayDialogFragment(var users: ArrayList<User>, var listener: UsersOnWayAdapterListener): DialogFragment() {

    var usersOnWayAdapter: UsersOnWayAdapter = UsersOnWayAdapter(users, listener, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val view = inflater.inflate(R.layout.dialog_users_on_way, container, false)

        view.list_items.adapter = usersOnWayAdapter
        view.list_items.layoutManager = LinearLayoutManager(context)

        view.submit_button.setOnClickListener {
            dialog?.dismiss()
        }
        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    companion object {
        const val TAG = "UsersOnWayDialogFragment"


    }
}