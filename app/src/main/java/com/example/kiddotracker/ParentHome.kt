package com.example.kiddotracker

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.*
import com.example.kiddotracker.databinding.ActivityParentHomeBinding
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class ParentHome : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityParentHomeBinding
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var firebaseAuth: FirebaseAuth


    @SuppressLint("SuspiciousIndentation")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        pass= intent.getStringExtra("password").toString()

        binding = ActivityParentHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarParentHome.toolbar)

        val navController = findNavController(R.id.nav_host_fragment_content_parent_home)

        Navigation.setViewNavController(binding.appBarParentHome.fab, navController)

        binding.appBarParentHome.fab.setOnClickListener { view ->
            view.findNavController().navigate(R.id.addChild2)
        }
        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView

        firebaseAuth= FirebaseAuth.getInstance()
        firebaseDatabase= FirebaseDatabase.getInstance()
        firebaseStorage= FirebaseStorage.getInstance()

        val header= navView.getHeaderView(0)
         header_name = header.findViewById(R.id.header_name) as TextView
         val header_email= header.findViewById(R.id.header_email) as TextView
        header_image =header.findViewById(R.id.header_image) as ImageView

        val parentRef =  firebaseDatabase.getReference("Parent")
        parentRef.child(firebaseAuth.uid.toString()).get().addOnSuccessListener {

            header_name.text = it.child("fullName").value.toString()
            header_email.text = it.child("email").value.toString()

//                firebaseStorage.getReference("profile").child(firebaseAuth.uid.toString())
//                    .child("profile.jpg").downloadUrl.addOnSuccessListener {
//
//                        Picasso.get().load(it).into(header_image)
//
//                    }



        }



        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home,R.id.nav_parent_edit_details,R.id.addChild2,R.id.childDetails,R.id.map
            ), drawerLayout
        )
        NavigationUI.setupWithNavController(binding.navView, navController)
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.parent_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        if(item.itemId==R.id.logout)
        {
            firebaseAuth.signOut()
            val intent = Intent(this, Parentlogin::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
            finish()

        }

        return super.onOptionsItemSelected(item)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_parent_home)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var header_name: TextView
        @SuppressLint("StaticFieldLeak")
        lateinit var header_image:ImageView

        lateinit var pass:String


    }
}

