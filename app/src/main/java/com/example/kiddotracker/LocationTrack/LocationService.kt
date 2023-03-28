package com.example.kiddotracker.LocationTrack

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.google.android.gms.location.LocationServices
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.util.Date
import kotlin.time.Duration.Companion.seconds

class LocationService : Service() {

    private val servieScope = CoroutineScope(
        SupervisorJob() + Dispatchers.IO
    )
    private lateinit var locationClient: LocationClient
    private lateinit var firebaseDatabase: FirebaseDatabase
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        super.onCreate()


        firebaseAuth = FirebaseAuth.getInstance()
        firebaseDatabase= FirebaseDatabase.getInstance()
        locationClient = DefaultLocationClient(
            applicationContext,
            LocationServices.getFusedLocationProviderClient(applicationContext)
        )

    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        when(intent?.action){

            ACTION_START -> start()
            ACTION_STOP -> stop()
        }
        return super.onStartCommand(intent, flags, startId)
    }

  private fun start()
  {
         locationClient
             .getLocationUpdates(300000L)
             .catch { e -> e.printStackTrace()}
             .onEach {
                 location ->

                 val latitude= location.latitude.toString()
                 val longitude=location.longitude.toString()
                 val mapData= MapData()
                 mapData.latitude=latitude
                 mapData.longitude=longitude
                 mapData.childId=firebaseAuth.uid.toString()
                 mapData.DateWithTime= Date().time.seconds.toString()

                 firebaseDatabase.getReference("GeoLocation").child(firebaseAuth.uid.toString()+Date().time.seconds).setValue(mapData)
                 firebaseDatabase.getReference("Child").child(firebaseAuth.uid.toString()).child("active").setValue("true")

             }.launchIn(servieScope)
  }

   private fun stop()
   {
       locationClient
           .getLocationUpdates(1000L)
           .catch { e -> e.printStackTrace()}
           .onEach {
                   location ->

               val latitude= location.latitude.toString()
               val longitude=location.longitude.toString()
               val mapData= MapData()
               mapData.latitude=latitude
               mapData.longitude=longitude
               mapData.childId=firebaseAuth.uid.toString()
               mapData.DateWithTime= Date().time.seconds.toString()

               firebaseDatabase.getReference("GeoLocation").child(firebaseAuth.uid.toString()+Date().time.seconds).setValue(mapData)

           }.launchIn(servieScope)
        stopSelf()
   }


    override fun onDestroy() {
        super.onDestroy()
        servieScope.cancel()
    }

   companion object{
       const val ACTION_START ="ACTION_START"
       const val ACTION_STOP= "ACTION_STOP"

   }


}