package com.example.kiddotracker

import ParentData
import android.content.Intent
import android.os.Bundle
import android.widget.Toast

import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.kiddotracker.databinding.ActivityParentSignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class ParentSignUp : AppCompatActivity() {

    private lateinit var binding : ActivityParentSignUpBinding


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_parent_sign_up)
        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this, R.color.orange)


        firebaseAuth = FirebaseAuth.getInstance()

        firebaseDatabase = FirebaseDatabase.getInstance()

        binding.signupButton.setOnClickListener {

              val email = binding.parentEmail.text.toString()
              val password= binding.signupPassword.text.toString()
              val fullName= binding.parentFullname.text.toString()

            if(email.isNotEmpty() && password.isNotEmpty() && fullName.isNotEmpty())
            {
                if(password.length<6)
                {
                    Toast.makeText(this,"Password cannot have less than 6 characters",Toast.LENGTH_SHORT).show()

                }
                else
                {
                    firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener{
                        if(it.isSuccessful)
                        {

                            val data = ParentData(
                                fullName = fullName,
                                image ="",
                                email = email,
                                id = firebaseAuth.uid.toString(),
                                mobileNum = "Your Mobile Number",
                                password = password

                            )

                            firebaseDatabase.getReference("Parent").child(firebaseAuth.uid.toString())
                                .setValue(data)

                             val intent = Intent(this,Parentlogin::class.java)
                            startActivity(intent)
                        }
                        else
                        {
                            Toast.makeText(this,it.exception.toString(),Toast.LENGTH_SHORT).show()
                        }
                    }
                }


            }
            else
            {
                Toast.makeText(this, "Fieils cannot be empty",Toast.LENGTH_SHORT).show()
            }








        }


    }



}