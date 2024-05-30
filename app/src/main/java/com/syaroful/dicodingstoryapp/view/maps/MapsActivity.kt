package com.syaroful.dicodingstoryapp.view.maps

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.syaroful.dicodingstoryapp.R
import com.syaroful.dicodingstoryapp.data.ResultState
import com.syaroful.dicodingstoryapp.data.response.ListStoryItem
import com.syaroful.dicodingstoryapp.databinding.ActivityMapsBinding
import com.syaroful.dicodingstoryapp.view.ViewModelFactory

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var data: List<ListStoryItem>

    private val boundsBuilder = LatLngBounds.Builder()

    private val viewModel by viewModels<MapsViewModel> {
        ViewModelFactory.getInstance(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
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

        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        viewModel.getStoriesWithLocation().observe(this) { result ->
            if (result != null) {
                when (result) {
                    is ResultState.Loading -> {

                    }

                    is ResultState.Error -> {
                        Toast.makeText(this, "Failed to load data", Toast.LENGTH_SHORT).show()
                    }

                    is ResultState.Success -> {
                        if (result.data.isEmpty()) {
                            Toast.makeText(this, "no stories with location", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            data = result.data
                            data.forEach { data ->
                                val latLng = LatLng(data.lat!!, data.lon!!)
                                mMap.addMarker(
                                    MarkerOptions()
                                        .position(latLng)
                                        .title(data.name)
                                        .snippet(data.description)
                                )
                                boundsBuilder.include(latLng)
                            }
                            val bounds: LatLngBounds = boundsBuilder.build()
                            mMap.animateCamera(
                                CameraUpdateFactory.newLatLngBounds(
                                    bounds,
                                    resources.displayMetrics.widthPixels,
                                    resources.displayMetrics.heightPixels,
                                    10
                                )
                            )
                        }
                    }
                }

            }
        }


    }
}