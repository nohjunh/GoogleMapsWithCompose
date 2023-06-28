package com.example.googlemapswithcompose

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

/*
https://github.com/googlemaps/android-maps-compose
https://www.youtube.com/watch?v=hhSTYK7I78c&t=1492s&ab_channel=CodingWithMitch
 */

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

//    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
//    private val viewModel: MainViewModel by viewModels()
//
//    private val requestPermissionLauncher =
//        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
//            if (isGranted) {
//                viewModel.getDeviceLocation(fusedLocationProviderClient)
//            }
//        }
//
//    private fun askPermissions() = when (PackageManager.PERMISSION_GRANTED) {
//        ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION) -> {
//            viewModel.getDeviceLocation(fusedLocationProviderClient)
//        }
//        else -> {
//            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
//        }
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
//        askPermissions()
        val hasPressureSensor = packageManager.hasSystemFeature(PackageManager.FEATURE_SENSOR_BAROMETER)
        if (hasPressureSensor) {
            Timber.tag("기압").e("존재")
        } else {
            // 기압 센서를 지원하지 않는 경우에 대한 예외 처리
            Timber.tag("기압").e("미존재")
        }

        setContent {
            AltitudeScreen()
        }
    }
}
