package com.example.kiddotracker

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddotracker.LocationTrack.MapData
import com.example.kiddotracker.databinding.FragmentChildDetailsBinding
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


class ChildDetails : Fragment() {


    private var _binding: FragmentChildDetailsBinding? = null

    private lateinit var ChildId:String

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!


    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var Locationlist:ArrayList<MapData>
    private lateinit var LocationRecyclerView: RecyclerView
    private lateinit var LocationClickAdpater:LocationAdaptar
    private  lateinit var dataRef: DatabaseReference


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val args= arguments?.let { ChildDetailsArgs.fromBundle(it) }

        if (args != null) {
            ChildId=args.childId
        }
        _binding= FragmentChildDetailsBinding.inflate(inflater,container,false)




        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        LocationRecyclerView =binding.locationRecycler
        LocationRecyclerView.layoutManager = LinearLayoutManager(view.context)
        LocationRecyclerView.setHasFixedSize(true)

        Locationlist= arrayListOf()
        LocationClickAdpater= LocationAdaptar(Locationlist)

        var child_name: String
        var child_email: String

        firebaseDatabase= FirebaseDatabase.getInstance()
        firebaseStorage= FirebaseStorage.getInstance()

        firebaseDatabase.getReference("Child").child(ChildId).get().addOnSuccessListener {

            child_name = it.child("name").value.toString()
            child_email=it.child("email").value.toString()

            binding.childEmailHome.text=child_email
            binding.childNameHome.text=child_name

        }



        firebaseStorage.getReference("profile").child(ChildId)
            .child("profile.jpg").downloadUrl.addOnSuccessListener {
                Picasso.get().load(it).into(binding.imageView)
            }


        dataRef= firebaseDatabase.getReference("GeoLocation")
        dataRef.addValueEventListener( object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists())
                {
                    for(LocationSnapshot in snapshot.children)
                    {
                        val location=LocationSnapshot.getValue(MapData::class.java)

                        if (location != null) {
                            if(location.childId==ChildId)
                            {
                                Locationlist.add(location)

                            }

                        }
                    }
                    LocationClickAdpater=LocationAdaptar(Locationlist)
                    LocationRecyclerView.adapter = LocationClickAdpater
                    LocationClickAdpater.setOnItemClickListner(object : LocationAdaptar.onItemClickListner{
                        override fun onItemClick(position: Int) {



                          findNavController().navigate(ChildDetailsDirections.
                            actionChildDetailsToParentChildLoc(Locationlist[position].latitude,Locationlist[position].longitude))






                        }

                    })
                }

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })








    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
