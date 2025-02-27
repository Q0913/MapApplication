package com.creator.mapapplication

import android.os.Bundle
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatActivity
import com.creator.mapapplication.databinding.ActivityMainBinding
import org.osmdroid.config.Configuration
import org.osmdroid.events.MapEventsReceiver
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.overlay.MapEventsOverlay
import org.osmdroid.views.overlay.Marker


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
// Configure osmdroid
        // Configure osmdroid
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))

        // Initialize the map view

        // Initialize the map view
        var mapView = binding.mapView
        mapView.setTileSource(TileSourceFactory.OpenTopo) // You can choose different tile sources

        mapView.setBuiltInZoomControls(true)

        // Set initial map center

        // Set initial map center
        val startPoint = GeoPoint(0, 0) // Set your initial coordinates

        mapView.getController().setCenter(startPoint)
        mapView.getController().setZoom(10) // Se


// Add map click event
        // Add map click event
        mapView.overlays.add(MapEventsOverlay(object : MapEventsReceiver {
            override fun singleTapConfirmedHelper(geoPoint: GeoPoint): Boolean {
                // Handle single tap event
                val startMarker = Marker(mapView)
                startMarker.position = geoPoint
                startMarker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_CENTER)
                mapView.getOverlays().add(startMarker)
//                showToast("Single tap: " + geoPoint.latitude + ", " + geoPoint.longitude)
                return true
            }

            override fun longPressHelper(geoPoint: GeoPoint): Boolean {
                // Handle long press event
//                showToast("Long press: " + geoPoint.latitude + ", " + geoPoint.longitude)
                return false
            }
        }))

    }

    /**
     * A native method that is implemented by the 'mapapplication' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {
        // Used to load the 'mapapplication' library on application startup.
        init {
            System.loadLibrary("mapapplication")
        }
    }
}