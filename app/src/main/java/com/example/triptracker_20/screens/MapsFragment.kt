package com.example.triptracker_20.screens

import android.Manifest
import android.Manifest.permission.*
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.triptracker_20.R
import com.example.triptracker_20.other.Constants.Companion.REQUEST_CODE_LOCATION_PERMISSION
import com.example.triptracker_20.other.TrackingUtility
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.model.CameraPosition

import com.google.android.gms.maps.model.MarkerOptions

import com.google.android.gms.maps.GoogleMap

import com.google.android.gms.maps.OnMapReadyCallback

import com.google.android.gms.maps.MapsInitializer

import com.google.android.gms.maps.MapView

import android.view.ViewGroup

import android.view.LayoutInflater
import java.lang.Exception


import android.view.View;
import androidx.annotation.Nullable
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.Marker



class MapsFragment : Fragment(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks {

    private var mMap: GoogleMap? = null
    private var mMarcadorActual: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View = inflater.inflate(R.layout.fragment_maps, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        return rootView
    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        requestPermissions()

        //ask for permissions..
        mMap!!.setMyLocationEnabled(true)
        val sydney = LatLng(-34.0, 151.0)

        mMarcadorActual = mMap!!.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
    }


    private fun requestPermissions() {
        if (TrackingUtility.hasLocationPermissions(requireContext())) {
            return
        }
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permission to use this app",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        } else {
            EasyPermissions.requestPermissions(
                this,
                "You need to accept location permissions to use this app",
                REQUEST_CODE_LOCATION_PERMISSION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_BACKGROUND_LOCATION
            )
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).setThemeResId(R.style.ThemeOverlay_MaterialComponents_Light).build().show()
        } else {
            requestPermissions()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

}


