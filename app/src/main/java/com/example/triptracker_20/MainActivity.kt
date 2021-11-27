package com.example.triptracker_20

import android.Manifest.*
import android.content.pm.PackageManager
import android.location.Location
import android.os.Build
import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.triptracker_20.screens.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.OnSuccessListener
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.triptracker_20.screens.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.jar.Manifest

class MainActivity : AppCompatActivity() {

    private val DEFAULT_UPDATE_INTERVAL = 30L
    private val FAST_UPDATE_INTERVAL = 5L
    private val PERMISSIONS_FINE_LOCATION = 99

    private val mapsFragment = MapsFragment()
    private val filtersFragment = FiltersFragment()
    private  val tripsFragment = TripsFragment()

    private lateinit var fusedLocationProviderClient : FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    private lateinit var coorTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        replaceFragment(mapsFragment)

        val bottomNavigation: BottomNavigationView = findViewById<com.google.android.material.bottomnavigation.BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigation.setOnNavigationItemSelectedListener {

            when(it.itemId){

                R.id.ic_filters -> replaceFragment(filtersFragment)
                R.id.ic_map -> replaceFragment(mapsFragment)
                R.id.ic_trips -> replaceFragment(tripsFragment)
            }

            true

        }

        locationRequest = LocationRequest()

        locationRequest.setInterval(DEFAULT_UPDATE_INTERVAL * 100)

        locationRequest.setFastestInterval(FAST_UPDATE_INTERVAL * 5)

        locationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY)

        // displaying coordinates
        coorTextView = findViewById(R.id.coorTextView)

        updateGPS()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){

            PERMISSIONS_FINE_LOCATION ->
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED){

                    updateGPS()
                }
                else{

                    Toast.makeText(this, "Ну дай доступ пж", Toast.LENGTH_SHORT).show()
                    finish()
                }

        }
    }


    private fun updateGPS(){
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)

        if (checkPermission()){

            fusedLocationProviderClient.lastLocation.addOnSuccessListener(this, OnSuccessListener<Location> {

                @Override
                fun onSuccess(location: Location){
                    updateUIValues(location)
                }
            })
        }
        else    {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                requestPermissions(arrayOf(permission.ACCESS_FINE_LOCATION), PERMISSIONS_FINE_LOCATION)
            }
        }
    }

    private fun updateUIValues(location: Location){
        // there is update ui elements: update latitude, longtitude, speed etc

        coorTextView.text = location.latitude.toString()
    }

    private fun checkPermission() : Boolean{

        return ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
    }

    private fun replaceFragment(fragment: Fragment){

        val transaction = supportFragmentManager.beginTransaction()

        transaction.hide(mapsFragment)
        transaction.hide(filtersFragment)
        transaction.hide(tripsFragment)

        if(!fragment.isAdded) {

            transaction.add(R.id.fragment_container, fragment, "current fragment")
        }

        transaction.show(fragment)
        transaction.commit()
    }

}