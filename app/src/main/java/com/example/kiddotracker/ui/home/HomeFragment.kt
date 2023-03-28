package com.example.kiddotracker.ui.home

import ChildAdaptar
import ChildAdapterData
import ChildData
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddotracker.ChildPage
import com.example.kiddotracker.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.google.firebase.storage.FirebaseStorage

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var Childlist:ArrayList<ChildData>
    private lateinit var ChildRecyclerView:RecyclerView
    private lateinit var ChildClickAdpater:ChildAdaptar

    private  lateinit var dataRef:DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        firebaseAuth= FirebaseAuth.getInstance()
        firebaseDatabase=FirebaseDatabase.getInstance()
        firebaseStorage=FirebaseStorage.getInstance()


         ChildRecyclerView =binding.childShowRecycler
        ChildRecyclerView.layoutManager = LinearLayoutManager(view.context)
        ChildRecyclerView.setHasFixedSize(true)

        Childlist= arrayListOf()
        ChildClickAdpater= ChildAdaptar(Childlist)
       dataRef= firebaseDatabase.getReference("Child")


       Log.v("user",firebaseAuth.uid.toString())

        var parent_id=""
        firebaseDatabase.getReference("Child").child(firebaseAuth.uid.toString()).get().addOnSuccessListener {

            parent_id=it.child("parentId").value.toString()
        }

        Log.v("parent id",parent_id)



//        firebaseDatabase.getReference("Parent").child(parent_id).get().addOnSuccessListener { it3->
//
//            parent_email=it3.child("email").value.toString()
//            parent_password=it3.child("password").value.toString()
//        }
//
//        Log.v("parent_id",parent_id)
//        Log.v("parent_email",parent_email)
//

        dataRef.addValueEventListener( object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                if(snapshot.exists())
                {
                    val parent_id=firebaseAuth.uid.toString()
                    for(ChildSnapshot in snapshot.children)
                    {
                        val child=ChildSnapshot.getValue(ChildData::class.java)

                        if (child != null) {
                            if(child.parentId==parent_id)
                            {
                                Childlist.add(child)

                            }

                        }
                    }
                    ChildClickAdpater=ChildAdaptar(Childlist)
                    ChildRecyclerView.adapter = ChildClickAdpater
                    ChildClickAdpater.setOnItemClickListner(object : ChildAdaptar.onItemClickListner{
                        override fun onItemClick(position: Int) {

                            var child_id= Childlist[position].id
                            findNavController().navigate(HomeFragmentDirections.actionNavHomeToChildDetails(child_id!!))

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