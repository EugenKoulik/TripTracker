package com.example.triptracker_20

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.Window
import android.view.WindowManager
import androidx.fragment.app.Fragment
import com.example.triptracker_20.screens.*
import com.google.android.gms.maps.GoogleMap
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val mapsFragment = MapsFragment()
    private val filtersFragment = FiltersFragment()
    private  val tripsFragment = TripsFragment()


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