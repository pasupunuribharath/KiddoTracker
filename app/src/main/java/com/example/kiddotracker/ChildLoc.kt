package com.example.kiddotracker

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class ChildLoc : Fragment() {

    private lateinit var map: GoogleMap
    private lateinit var latitude:String
    private lateinit var longitude:String

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */

        map=googleMap

        Log.v("latitude",latitude)
        Log.v("longitude",longitude)
        val location_child = LatLng(latitude.toDouble(), longitude.toDouble())
        map.addMarker(MarkerOptions().position(location_child).title("Marker in Sydney"))
        map.moveCamera(CameraUpdateFactory.newLatLng(location_child))
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(location_child,16f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {


        val bundle = arguments
        latitude = bundle?.getString("latitude")!!
        longitude= bundle?.getString("longitude")!!
        return inflater.inflate(R.layout.fragment_child_loc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)


    }






}