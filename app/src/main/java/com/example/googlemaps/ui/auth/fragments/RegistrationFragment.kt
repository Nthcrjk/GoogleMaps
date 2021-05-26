package com.example.googlemaps.ui.auth.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.googlemaps.R
import com.example.googlemaps.firebase.model.User
import com.example.googlemaps.interfaces.OnNavigationListener
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_registration.view.*

class RegistrationFragment() : Fragment() {

    private val mAuth = FirebaseAuth.getInstance()
    private val USER: String = "Users"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_registration, container, false)
        view.to_login_in_button.setOnClickListener {
            (context as OnNavigationListener).navigateTo(AuthFragment(), true)
        }
        view.sign_up_button.setOnClickListener {
            if (view.login_edit_text.text.toString().isNotEmpty() && view.password_edit_text.text.toString().isNotEmpty() && view.name_edit_text.text.toString().isNotEmpty()){
                val mail: String = view.login_edit_text.text.toString()
                val password: String = view.password_edit_text.text.toString()
                val name: String = view.name_edit_text.text.toString()
                val secondName: String = view.secondName_edit_text.text.toString()
                val isOrg: Boolean = view.is_org.isChecked
                mAuth.createUserWithEmailAndPassword(mail, password).addOnCompleteListener {
                    if (it.isSuccessful){
                        Toast.makeText(context, "Success!!!", Toast.LENGTH_SHORT).show()
                        FirebaseDatabase.getInstance().getReference(USER).child(mAuth.uid.toString()).push().setValue(User(name, secondName, mail, password, isOrg))
                    }
                }.addOnFailureListener {
                    Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                }
            }
        }
        return view
    }

}