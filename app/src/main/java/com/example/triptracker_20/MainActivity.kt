package com.example.triptracker_20

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.triptracker_20.screens.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val mapsFragment = MapsFragment()
    private val filtersFragment = FiltersFragment()
    private  val tripsFragment = TripsFragment()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        replaceFragment(mapsFragment)

        val bottom_Navigation = findViewById(R.id.bottom_navigation)  as com.google.android.material.bottomnavigation.BottomNavigationView

        bottom_Navigation.setOnNavigationItemReselectedListener {

            when(it.itemId){

                R.id.ic_filters -> replaceFragment(filtersFragment)
                R.id.ic_map -> replaceFragment(mapsFragment)
                R.id.ic_trips -> replaceFragment(tripsFragment)
        }
            true

    }

    }

    private fun replaceFragment(fragment: Fragment){

        if(fragment != null){
            val transaction = supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_container, fragment)
            transaction.commit()
        }
    }

}