package com.example.kiddotracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.kiddotracker.databinding.ActivityChildLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class ChildLogin : AppCompatActivity() {


    private lateinit var binding: ActivityChildLoginBinding

    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= DataBindingUtil.setContentView(this,R.layout.activity_child_login)

        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this,R.color.orange)

        firebaseAuth=FirebaseAuth.getInstance()

        binding.login.setOnClickListener {

            val email= binding.childEmailLogin.text.toString()
            val password = binding.editTextTextPassword.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty())
            {
                firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener{

                    if(it.isSuccessful)
                    {
                        val intent = Intent(this,ChildHome::class.java)
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












    }
}