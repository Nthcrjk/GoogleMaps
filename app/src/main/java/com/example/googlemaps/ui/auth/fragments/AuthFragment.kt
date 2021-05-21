package com.example.googlemaps.ui.auth.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.googlemaps.R
import com.example.googlemaps.interfaces.OnNavigationListener
import com.example.googlemaps.ui.auth.activity.AuthActivity
import com.example.googlemaps.ui.main.MainActivity
import com.google.android.gms.tasks.RuntimeExecutionException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.fragment_auth.view.*
import kotlinx.android.synthetic.main.fragment_auth.view.login_edit_text
import kotlinx.android.synthetic.main.fragment_auth.view.password_edit_text
import kotlinx.android.synthetic.main.fragment_registration.view.*

class AuthFragment() : Fragment() {

    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var view = inflater.inflate(R.layout.fragment_auth, container, false)
        view.to_registration_button.setOnClickListener {
            (context as OnNavigationListener).navigateTo(RegistrationFragment(), true)
        }
        view.auth_button.setOnClickListener {
            if (view.login_edit_text.text.toString().isNotEmpty() && view.password_edit_text.text.toString().isNotEmpty()){
                    mAuth.signInWithEmailAndPassword(
                        view.login_edit_text.text.toString(),
                        view.password_edit_text.text.toString()
                    ).addOnFailureListener {
                        Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                    }.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(context, "Success!!!", Toast.LENGTH_LONG).show()
                        val intent: Intent = Intent((context as AuthActivity), MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            } else {
                Toast.makeText(context, "Email or password is empty", Toast.LENGTH_LONG).show()
            }
        }
        view.sign_in_like_guest.setOnClickListener {
            val intent: Intent = Intent((context as AuthActivity), MainActivity::class.java)
            startActivity(intent)
        }
        return view
    }


}

