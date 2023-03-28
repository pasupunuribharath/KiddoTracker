package com.example.kiddotracker

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddotracker.LocationTrack.LocationService
import com.example.kiddotracker.LocationTrack.MapData
import com.example.kiddotracker.databinding.ActivityChildHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso

@Suppress("DEPRECATION")
class ChildHome : AppCompatActivity() {


    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseStorage: FirebaseStorage
   private lateinit var binding:ActivityChildHomeBinding
    private lateinit var Locationlist:ArrayList<MapData>
    private lateinit var LocationRecyclerView: RecyclerView
    private lateinit var LocationClickAdpater:LocationAdaptar
    private  lateinit var dataRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= DataBindingUtil.setContentView(this,R.layout.activity_child_home)


        firebaseAuth = FirebaseAuth.getInstance()
        firebaseStorage= FirebaseStorage.getInstance()
        firebaseDatabase=FirebaseDatabase.getInstance()



        LocationRecyclerView =binding.locationRecycler
        LocationRecyclerView.layoutManager = LinearLayoutManager(this)
        LocationRecyclerView.setHasFixedSize(true)

        Locationlist= arrayListOf()
        LocationClickAdpater= LocationAdaptar(Locationlist)

        var child_name: String
        var child_email: String

        firebaseDatabase.getReference("Child").child(firebaseAuth.uid.toString()).get().addOnSuccessListener {

            child_name = it.child("name").value.toString()
            child_email=it.child("email").value.toString()

            binding.childEmailHome.text=child_email
            binding.childNameHome.text=child_name

        }



        firebaseStorage.getReference("profile").child(firebaseAuth.uid.toString())
            .child("profile.jpg").downloadUrl.addOnSuccessListener {
                Picasso.get().load(it).into(binding.imageView)
            }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.ACCESS_COARSE_LOCATION,
                android.Manifest.permission.ACCESS_FINE_LOCATION,
                android.Manifest.permission.INTERNET
            ),0)

          Intent(applicationContext,LocationService::class.java).apply {

              action = LocationService.ACTION_START
              startService(this)

          }
        dataRef= firebaseDatabase.getReference("GeoLocation")
        dataRef.addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists())
                {
                    val child_id=firebaseAuth.uid.toString()
                    for(LocationSnapshot in snapshot.children)
                    {
                        val location=LocationSnapshot.getValue(MapData::class.java)

                        if (location != null) {
                            if(location.childId==child_id)
                            {
                                Locationlist.add(location)

                            }

                        }
                    }
                    LocationClickAdpater=LocationAdaptar(Locationlist)
                    LocationRecyclerView.adapter = LocationClickAdpater
                    LocationClickAdpater.setOnItemClickListner(object : LocationAdaptar.onItemClickListner{
                        override fun onItemClick(position: Int) {




                                val fragmentManager: FragmentManager = supportFragmentManager
                                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                                val LocFragment = ChildLoc()

                                val bundle = Bundle()
                                bundle.putString("latitude", Locationlist[position].latitude)
                                bundle.putString("longitude", Locationlist[position].longitude)
                                LocFragment.arguments = bundle
                                fragmentTransaction.add(R.id.frameLayout, LocFragment).addToBackStack(null).commit()



                        }

                    })
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
        
        
          




    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.parent_home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {


        if(item.itemId==R.id.logout)
        {


            val intent = Intent(this, ChildLogin::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)


            Intent(applicationContext,LocationService::class.java).apply {

                action = LocationService.ACTION_STOP
                startService(this)

            }
            firebaseAuth.signOut()
            finish()

        }

        return super.onOptionsItemSelected(item)


    }


    override fun onBackPressed() {
        val fragmentManager = supportFragmentManager
        if (fragmentManager.backStackEntryCount > 0) {
            fragmentManager.popBackStack()
        } else {
            super.onBackPressed()
        }
    }
}