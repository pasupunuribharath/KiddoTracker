package com.example.kiddotracker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.example.kiddotracker.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        supportActionBar?.hide()
        window.statusBarColor = ContextCompat.getColor(this,R.color.orange)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_main)


        binding.parentLogin.setOnClickListener {

            val intent:Intent = Intent(this,Parentlogin::class.java)

            startActivity(intent)

        }

        binding.childLogin.setOnClickListener {

           val intent:Intent= Intent(this,ChildLogin::class.java)

            startActivity(intent)
        }

        binding.signup.setOnClickListener {
            val it:Intent = Intent(this, ParentSignUp::class.java)

            startActivity(it)
        }


    }
}