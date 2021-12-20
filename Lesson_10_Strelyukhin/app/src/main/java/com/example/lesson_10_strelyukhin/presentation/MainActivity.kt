package com.example.lesson_10_strelyukhin.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Looper
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lesson_10_strelyukhin.R
import com.example.lesson_10_strelyukhin.data.LoadingState
import com.example.lesson_10_strelyukhin.data.model.Bridge
import com.example.lesson_10_strelyukhin.databinding.ActivityMainBinding
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        const val STATE_LOADING = 0
        const val STATE_ERROR = 1
        const val STATE_DATA = 2
    }

    private val viewModel: MainActivityViewModel by viewModels()
    private val binding by viewBinding(ActivityMainBinding::bind)

    private var map: GoogleMap? = null
    private var markers = mutableMapOf<Marker, Bridge>()
    private var userMarker: Marker? = null

    private val fusedLocationProviderClient by lazy { LocationServices.getFusedLocationProviderClient(this) }
    private val locationRequest by lazy {
        LocationRequest.create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
            .setInterval(5000L)
            .setFastestInterval(1000L)
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            updateUserMarker(result.lastLocation.latitude, result.lastLocation.longitude)
            fusedLocationProviderClient.removeLocationUpdates(this)
        }
    }

    private val locationPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            enableLocation()
        } else {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_COARSE_LOCATION)) {
                Toast.makeText(
                    this,
                    "Необходимо предоставить разрешение в настройках",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel.loadBridges()
        val mapFragment =
            supportFragmentManager.findFragmentById(R.id.fragmentContainer) as SupportMapFragment
        mapFragment.getMapAsync(this)

        viewModel.loadingStateLiveData.observe(this) { state ->
            when (state) {
                is LoadingState.Loading -> setStateLoading()
                is LoadingState.Data -> setStateData(
                    (viewModel.loadingStateLiveData.value as LoadingState.Data).data
                )
                is LoadingState.Error -> setStateError(state.error)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        googleMap.uiSettings.apply {
            isMapToolbarEnabled = false
        }
        enableLocation()
        googleMap.setOnMarkerClickListener { marker ->
            binding.layoutBridge.visibility = View.VISIBLE
            binding.imageViewBridgeState.setImageResource(markers[marker]?.getImageId() ?: R.drawable.ic_bridge_late)
            binding.textViewBridgeName.text = markers[marker]?.name
            binding.textViewBridgeTime.text = buildString {
                markers[marker]?.divorces?.forEach { divorce ->
                    append("${divorce.start} - ${divorce.end}\t\t\t")
                }
            }
            true
        }
        googleMap.setOnCameraMoveListener {
            binding.layoutBridge.visibility = View.GONE
        }
        googleMap.setOnMapClickListener {
            binding.layoutBridge.visibility = View.GONE
        }
        map?.setOnMapLoadedCallback {
            val boundsBuilder = LatLngBounds.builder()
            markers.forEach {
                boundsBuilder.include(it.key.position)
            }
            val bounds = boundsBuilder.build()
            map?.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100))
        }
    }

    private fun updateUserMarker(lat: Double, lng: Double) {
        userMarker?.remove()
        userMarker = map?.addMarker(MarkerOptions().position(LatLng(lat, lng)))
    }

    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    private fun toBitmap(context: Context, imageId: Int): BitmapDescriptor? {
        val image = ContextCompat.getDrawable(context, imageId)
        val bitmap = image?.let {
            Bitmap.createBitmap(
                it.intrinsicWidth,
                it.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
        }
        return if (bitmap != null) {
            image.setBounds(0, 0, Canvas(bitmap).width, Canvas(bitmap).height)
            image.draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        } else {
            null
        }
    }

    private fun Bridge.toMarker(): Marker? {
        return map?.addMarker(
            MarkerOptions()
                .title(name)
                .position(getLatLng())
                .icon(toBitmap(this@MainActivity, getImageId()))
        )
    }

    private fun setMarkers(bridges: List<Bridge>) {
        bridges.forEach { bridge ->
            bridge.toMarker()?.let { markers[it] = bridge }
        }
    }

    private fun enableLocation() {
        when (PackageManager.PERMISSION_GRANTED) {
            ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener { location ->
                    if (location != null) {
                        updateUserMarker(location.latitude, location.longitude)
                    } else {
                        startLocationUpdates()
                    }
                }
            }
            else -> {
                locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }

    private fun setStateLoading() {
        binding.viewFlipper.displayedChild = STATE_LOADING
    }

    private fun setStateData(data: List<Bridge>) {
        if (data.isEmpty()) {
            binding.viewFlipper.displayedChild = STATE_ERROR
            binding.textViewError.text = "Похоже, здесь пусто"
            binding.buttonRefresh.setOnClickListener {
                viewModel.loadBridges()
            }
        } else {
            binding.viewFlipper.displayedChild = STATE_DATA
            setMarkers(data)
        }
    }

    private fun setStateError(error: Exception) {
        binding.viewFlipper.displayedChild = STATE_ERROR
        binding.textViewError.text = error.message
        binding.buttonRefresh.setOnClickListener {
            viewModel.loadBridges()
        }
    }
}