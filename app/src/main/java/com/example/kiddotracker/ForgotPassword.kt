package com.example.kiddotracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.google.firebase.auth.FirebaseAuth

class ForgotPassword : AppCompatActivity() {


    private lateinit var firebaseAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)


        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this,R.color.orange)

        firebaseAuth =  FirebaseAuth.getInstance()

        val verify_email= findViewById(R.id.verify_email) as TextView



        val button= findViewById(R.id.reset_button) as Button

        button.setOnClickListener {

            val email=verify_email.text.toString()
            if (email.isNotEmpty()) {
                firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {

                    if (it.isSuccessful) {
                        Toast.makeText(
                            this,
                            "We have sent you instructions to reset your password!",
                            Toast.LENGTH_SHORT
                        ).show()

                    } else {
                        Toast.makeText(this, "Failed to send reset email!", Toast.LENGTH_SHORT)
                            .show()
                    }
                }

            } else {
                Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show()

            }

        }




    }
}