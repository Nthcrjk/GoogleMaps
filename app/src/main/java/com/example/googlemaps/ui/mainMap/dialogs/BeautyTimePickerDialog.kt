package com.example.googlemaps.ui.mainMap.dialogs

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.googlemaps.R
import com.example.googlemaps.firebase.model.Date
import com.example.googlemaps.firebase.model.Time
import com.example.googlemaps.ui.mainMap.view.BeautyTimePickerDialogListener
import com.example.googlemaps.ui.mainMap.view.SaveRoadDialogListener
import kotlinx.android.synthetic.main.dialog_beauty_time_picker.view.*

class BeautyTimePickerDialog(var name: String, var date: Date, listener: BeautyTimePickerDialogListener, val lastDialog: DialogFragment): DialogFragment() {

    private var listener = listener
    private var time: Time? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val view = inflater.inflate(R.layout.dialog_beauty_time_picker, container, false)
        view.time_picker.setOnTimeChangedListener { view, hourOfDay, minute ->
            time = Time(hourOfDay, minute)
        }
        view.submit_button.setOnClickListener {
            if (time != null) {
                listener.saveRoad(name, date, time!!)
                lastDialog.dismiss()
            } else {
                Toast.makeText(context, "Error", Toast.LENGTH_LONG).show()
            }
        }
        view.cancel_button.setOnClickListener {
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
        const val TAG = "BeautyTimePickerDialog"

        fun newInstance(name: String, date: Date, listener: BeautyTimePickerDialogListener, lastDialog: DialogFragment): BeautyTimePickerDialog {
            return BeautyTimePickerDialog(name, date ,listener, lastDialog)
        }
    }
}