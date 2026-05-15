package com.stadiumvisit

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.stadiumvisit.data.StadiumDatabase
import com.stadiumvisit.databinding.ActivityMainBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: StadiumDatabase
    private var map: GoogleMap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        database = StadiumDatabase.getInstance(this)

        val mapFragment = supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        CoroutineScope(Dispatchers.IO).launch {
            val stadiums = database.stadiumDao().getAllStadiums()
            launch(Dispatchers.Main) {
                stadiums.take(40).forEach { stadium ->
                    val position = LatLng(stadium.latitude, stadium.longitude)
                    googleMap.addMarker(
                        MarkerOptions()
                            .position(position)
                            .title(stadium.name)
                            .snippet("${stadium.country} • ${stadium.league} • ${stadium.capacity}")
                    )
                }

                val center = LatLng(50.0755, 14.4378)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(center, 4.0f))
            }
        }
    }
}
