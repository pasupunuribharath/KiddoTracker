package com.example.kiddotracker.ui

import ChildData
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.example.kiddotracker.R
import com.example.kiddotracker.databinding.FragmentAddChildBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


@Suppress("DEPRECATION")
class AddChild : Fragment() {


    private var _binding : FragmentAddChildBinding ?=null
    private val binding get() = _binding!!
    private var imageUri: Uri? = null
    private lateinit var img: ImageView
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseReference: DatabaseReference



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentAddChildBinding.inflate(inflater, container, false)

        img=binding.imageView5


        return binding.root
    }

    @SuppressLint("SuspiciousIndentation")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addChildPhoto.setOnClickListener {

            val intent= Intent(Intent.ACTION_PICK)
            intent.type = "image/*"

            startActivityForResult(Intent.createChooser(intent,"Select image"),200)

        }

        firebaseStorage = FirebaseStorage.getInstance()
        firebaseDatabase= FirebaseDatabase.getInstance()
        firebaseAuth= FirebaseAuth.getInstance()

        firebaseReference= firebaseDatabase.getReference("Parent")



        binding.addChildButton.setOnClickListener {





            val data= ChildData()

            val parentId=data.parentId

            data.name=binding.addChildName.text.toString()
            data.email=binding.childEmail.text.toString()
            data.password=binding.addChildPassword.text.toString()
            data.parentId=firebaseAuth.uid.toString()
            data.image=binding.imageView5.toString()

            data.parent_email = firebaseAuth.currentUser?.email.toString()

            if(data.email.isNotEmpty() && data.password.isNotEmpty() && data.name.isNotEmpty()) {



                 firebaseAuth.createUserWithEmailAndPassword(data.email, data.password).addOnCompleteListener{

                     if(it.isSuccessful)
                     {
                         val uid= it.result.user?.uid.toString()

                         data.id=uid
                         firebaseDatabase.getReference("Child").child(uid).setValue(data)
                         firebaseStorage.getReference("profile").child(uid).child("profile.jpg")
                          .putFile(imageUri!!)

                     }
                     else
                     {
                         Toast.makeText(view.context,"Something went wrong",Toast.LENGTH_SHORT).show()
                     }


                 }

                var parent_email=""
                var parent_password=""

                firebaseDatabase.getReference("Parent").child(parentId).get().addOnSuccessListener {

                    parent_email=it.child("email").value.toString()

                    parent_password=it.child("passowrd").value.toString()
                }

                if(parent_email.isNotEmpty() && parent_password.isNotEmpty())
                firebaseAuth.signInWithEmailAndPassword(parent_email,parent_password).addOnCompleteListener {

                }


                findNavController().navigate(R.id.action_addChild2_to_nav_home)

            }
            else
            {
                Toast.makeText(view.context,"Fields cannot be empty",Toast.LENGTH_SHORT).show()
            }






        }






    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode==200 && resultCode==RESULT_OK)
        {
              imageUri= data?.data

              img.setImageURI(imageUri)
        }



        super.onActivityResult(requestCode, resultCode, data)
    }

}