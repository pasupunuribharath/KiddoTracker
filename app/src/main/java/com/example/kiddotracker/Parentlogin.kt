package com.example.kiddotracker


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil

import com.example.kiddotracker.databinding.ActivityParentLoginBinding
import com.google.firebase.auth.FirebaseAuth


class Parentlogin : AppCompatActivity() {

    private lateinit var binding: ActivityParentLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_parent_login)

        firebaseAuth= FirebaseAuth.getInstance()

        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this,R.color.orange)

        binding.login.setOnClickListener {

            val email= binding.parentEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty())
            {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{

                    if(it.isSuccessful)
                    {
                        val intent = Intent(this,ParentHome::class.java)
                        intent.putExtra("password",password)
                        startActivity(intent)

                    }else
                    {
                        Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else
            {
                Toast.makeText(this,"Fields cannot be empty",Toast.LENGTH_SHORT).show()
            }


        }


        binding.textView2.setOnClickListener {

            val intent=Intent(this,ForgotPassword::class.java)
            startActivity(intent)


        }


    }
}