package com.example.kiddotracker

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kiddotracker.LocationTrack.MapData

class LocationAdaptar(private val LocationList:ArrayList<MapData>): RecyclerView.Adapter<LocationAdaptar.LocationViewHolder>() {


    interface onItemClickListner{

        fun onItemClick(position:Int)
    }
    lateinit var mlistener : onItemClickListner

    fun setOnItemClickListner(listner : onItemClickListner)
    {
        mlistener=listner
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {



        val ItemView= LayoutInflater.from(parent.context).inflate(R.layout.geolocitem,parent,false)
        return LocationViewHolder(ItemView ,mlistener)

    }

    override fun getItemCount(): Int {
        return LocationList.size
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {

        val currentItem = LocationList[position]


        holder.date.text=currentItem.DateWithTime
        holder.latitueAndLongitude.text= currentItem.latitude +","+currentItem.longitude


    }



    class LocationViewHolder(ItemView : View, listner: onItemClickListner ): RecyclerView.ViewHolder(ItemView){


        val date : TextView = ItemView.findViewById(R.id.DataWithTime)
        val latitueAndLongitude: TextView = ItemView.findViewById(R.id.latilongi)
        init{

            ItemView.setOnClickListener {

                listner.onItemClick(adapterPosition)
            }


        }


    }

}