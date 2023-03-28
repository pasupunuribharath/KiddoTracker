package com.example.kiddotracker.ui

import ParentData
import android.app.Activity.RESULT_OK
import android.content.Intent

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.kiddotracker.ParentHome
import com.example.kiddotracker.databinding.FragmentParentEditDetailsBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.squareup.picasso.Picasso


@Suppress("DEPRECATION")
class Parent_edit_details : Fragment() {


    private var _binding: FragmentParentEditDetailsBinding? = null
    private val binding get() = _binding!!
    private var Pdata =ParentData()

     private lateinit var firebaseAuth: FirebaseAuth
     private lateinit var firebaseStorage: FirebaseStorage
     private lateinit var firebaseDatabase: FirebaseDatabase

    private lateinit var img:ImageView
    private var imageUri: Uri? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        _binding = FragmentParentEditDetailsBinding.inflate(inflater, container, false)

        img= binding.imageView4


        return binding.root


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @RequiresApi(Build.VERSION_CODES.P)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val editPhotoButton = binding.button




        editPhotoButton.setOnClickListener {

            val intent = Intent(Intent.ACTION_PICK)
            intent.setType("image/*")

            startActivityForResult(Intent.createChooser(intent,"Select image"),200)



        }

        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase = FirebaseDatabase.getInstance()
        firebaseStorage = FirebaseStorage.getInstance()


        val parentRef =  firebaseDatabase.getReference("Parent")
        parentRef.child(firebaseAuth.uid.toString()).get().addOnSuccessListener { it ->

            Pdata.email=it.child("email").value.toString()
            Pdata.id=it.child("id").value.toString()
            Pdata.image=it.child("image").value.toString()
            Pdata.mobileNum=it.child("mobileNum").value.toString()
            Pdata.fullName=it.child("fullName").value.toString()
           firebaseStorage.getReference("profile"!!).child(firebaseAuth.uid.toString()!!).child("profile.jpg"!!)
               .downloadUrl.addOnSuccessListener {

                   Picasso.get().load(it).into(binding.imageView4)

               }



            binding.parentName.setText(it.child("fullName").value.toString())
            binding.parentMobile.setText(it.child("mobileNum").value.toString())
        }



         binding.editButton.setOnClickListener {

             Pdata.fullName=binding.parentName.text.toString()
             Pdata.mobileNum=binding.parentMobile.text.toString()

             ParentHome.header_name.text = binding.parentName.text.toString()

             parentRef.child(firebaseAuth.uid.toString()).setValue(Pdata)
         }








    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(requestCode==200 && resultCode==RESULT_OK)
        {
           imageUri= data?.data


            binding.imageView4.setImageURI(imageUri)
            ParentHome.header_image.setImageURI(imageUri)

            val ProfileImgRef=firebaseStorage.getReference("profile")
                .child(firebaseAuth.uid.toString()).child("profile.jpg")

            ProfileImgRef.putFile(imageUri!!)

        }



        super.onActivityResult(requestCode, resultCode, data)
    }


}