package com.example.googlemaps.ui.auth.activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.googlemaps.R
import com.example.googlemaps.interfaces.OnNavigationListener
import com.example.googlemaps.ui.auth.fragments.AuthFragment
import com.example.googlemaps.ui.main.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.activity_auth.*

class AuthActivity : AppCompatActivity(), OnNavigationListener {

    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        mAuth.signOut()
    }

    override fun onStart() {
        super.onStart()
        val currentUser: FirebaseUser? = mAuth.currentUser
        if (currentUser == null){
            Toast.makeText(this, "user null", Toast.LENGTH_SHORT).show()
            navigateTo(AuthFragment(mAuth), false)
        } else {
            val intent: Intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    override fun navigateTo(fragment: Fragment, isAddToBackStack: Boolean) {
        val fTrans: FragmentTransaction = supportFragmentManager.beginTransaction()
        fTrans.replace(R.id.content, fragment)
        if (isAddToBackStack){
            fTrans.addToBackStack(null)
        }
        fTrans.commit()

    }
}