package com.example.kiddotracker

import androidx.fragment.app.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class parent_child_loc : Fragment() {


    private  lateinit var latitude:String
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
        val location_child= LatLng(latitude.toDouble(),longitude.toDouble())
        googleMap.addMarker(MarkerOptions().position(location_child).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(location_child))
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(location_child,10f))
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        val args= arguments?.let { parent_child_locArgs.fromBundle(it) }

        if (args != null) {
            latitude=args.latitude
            longitude=args.longitude
        }
        return inflater.inflate(R.layout.fragment_parent_child_loc, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}