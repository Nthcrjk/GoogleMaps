package com.example.googlemaps.ui.mainMap.dialogs


import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.googlemaps.R
import com.example.googlemaps.ui.mainMap.view.SaveRoadDialogListener

import kotlinx.android.synthetic.main.dialog_road_fragment.view.*

class SaveRoadDialogFragment(listener: SaveRoadDialogListener): DialogFragment() {
    private var listener: SaveRoadDialogListener = listener

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
        view.submit_button.setOnClickListener {
            listener.saveRoad(view.road_name_edit_text.text.toString())
            Toast.makeText(context, "Путь сохранен", Toast.LENGTH_LONG).show()
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
        const val TAG = "SaveRoadDialogFragment"

        fun newInstance(listener: SaveRoadDialogListener): SaveRoadDialogFragment {
            return SaveRoadDialogFragment(listener)
        }
    }
}