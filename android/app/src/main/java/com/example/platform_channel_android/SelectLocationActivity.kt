package com.example.platform_channel_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions

class SelectLocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var btnSelectLocation: Button
    private lateinit var marker: Marker
    private var visibleFlag:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_select_location)


        btnSelectLocation = findViewById(R.id.button)
        btnSelectLocation.setOnClickListener {
            intent.putExtra("latitude", marker.position.latitude)
            intent.putExtra("longitude", marker.position.longitude)
            setResult(1, intent)
            finish()
        }
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    //Function to add markets
    private fun addMarker(latlng: LatLng, title: String?) {
        val markerOptions = MarkerOptions().position(latlng).title(title)
        marker = mMap.addMarker(markerOptions)
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        mMap.setOnCameraMoveListener {
            mMap.clear()
            visibleFlag = true
        }

        mMap.setOnCameraIdleListener {
            if(visibleFlag){
                Log.d("SAaad", "dasdasdasd")
                addMarker(mMap.cameraPosition.target, "Custom")
                visibleFlag = false
            }
        }

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(intent.getDoubleExtra("latitude", 12.2), intent.getDoubleExtra("longitude", 12.2))
        addMarker(sydney, "Custom")
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }
}