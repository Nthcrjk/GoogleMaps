package com.example.googlemaps.ui.mainMap.dialogs


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.googlemaps.R
import com.example.googlemaps.firebase.model.Date
import com.example.googlemaps.ui.mainMap.view.BeautyTimePickerDialogListener
import com.example.googlemaps.ui.mainMap.view.SaveRoadDialogListener

import kotlinx.android.synthetic.main.dialog_road_fragment.view.*
import java.text.DateFormat
import java.util.*

class SaveRoadDialogFragment(beautyListener: BeautyTimePickerDialogListener): DialogFragment() {
    private var beautyListener: BeautyTimePickerDialogListener = beautyListener

    private lateinit var beautyTimePickerDialog: BeautyTimePickerDialog
    private var date: Date? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        val view = inflater.inflate(R.layout.dialog_road_fragment, container, false)
        view.cancel_button.setOnClickListener {
            dialog?.dismiss()
        }

        view.calendar_view.setOnDateChangeListener { view, year, month, dayOfMonth ->
            date = Date(year, month, dayOfMonth)
        }


        view.submit_button.setOnClickListener {
            if (date != null && !view.road_name_edit_text.text.isNullOrEmpty()) {
                beautyTimePickerDialog = BeautyTimePickerDialog.newInstance(view.road_name_edit_text.text.toString(), date!!, beautyListener, this)
                beautyTimePickerDialog.show(childFragmentManager, BeautyTimePickerDialog.TAG)
            } else {
                Toast.makeText(context, "date or name is null or empty", Toast.LENGTH_LONG).show()
            }

            /*
            listener.saveRoad(view.road_name_edit_text.text.toString())
            Toast.makeText(context, "Путь сохранен", Toast.LENGTH_LONG).show()
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = view.calendar_view.date
            val dateFromatter = DateFormat.getDateInstance(DateFormat.MEDIUM)

            Log.e("gaf", dateFromatter.format(calendar.time))
            dialog?.dismiss()
             */
        }

        return view
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val dialog = super.onCreateDialog(savedInstanceState)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        return dialog
    }

    override fun onStop() {
        super.onStop()
    }

    companion object {
        const val TAG = "SaveRoadDialogFragment"

        fun newInstance(beautyListener: BeautyTimePickerDialogListener): SaveRoadDialogFragment {
            return SaveRoadDialogFragment(beautyListener)
        }
    }
}