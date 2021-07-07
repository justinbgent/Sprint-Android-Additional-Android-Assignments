package com.example.mapsproject

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

//        PRE DONE CODE
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


        //Add buttons here to prevent crashes on premature use
        pin_btn.setOnClickListener {
            val latLng = mMap.cameraPosition.target
            mMap.addMarker(MarkerOptions().position(latLng).title("Your Pin"))
        }

        position_btn.setOnClickListener {
            requestPermission()
        }
    }

    //REQUESTS USER FOR PERMISSION TO GET THEIR LOCATION
    fun requestPermission(){
        ActivityCompat
            .requestPermissions(this,
                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                232)
    }

    //Checks if permission is granted then moves camera to user location
    fun getCurrentLocation() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {

            val locationProvider = LocationServices.getFusedLocationProviderClient(this)

            //Need listener because it takes time to get location. This statement alone goes
            //fast than thread getting it "locationProvider.lastLocation.result"
            locationProvider.lastLocation.addOnSuccessListener {
                if (it != null) {
                    val latLng = LatLng(it.latitude, it.longitude)
                    //can call a pin on location right away if desired
                    //mMap.addMarker(MarkerOptions().position(latLng).title("Your Location"))
                    mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng))
                }
            }

        }
    }

//    WHEN LOCATION PERMISSION REQUEST IS SUCCESSFUL, MOVES CAMERA TO LOCATION getCurrentLocation()
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 232) {
            getCurrentLocation()
        }
    }
}
