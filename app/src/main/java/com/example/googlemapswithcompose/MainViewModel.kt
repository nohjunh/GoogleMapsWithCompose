package com.example.googlemapswithcompose

import androidx.lifecycle.ViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import dagger.hilt.android.lifecycle.HiltViewModel
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(

): ViewModel() {

    fun getDeviceLocation(
        fusedLocationProviderClient: FusedLocationProviderClient
    ) {
        try {
            val locationResult = fusedLocationProviderClient.lastLocation
            locationResult.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Timber.tag("GOOGLE LOCATION").e("고도: ${task.result.altitude}")
                    Timber.tag("GOOGLE LOCATION").e("위도: ${task.result.latitude}")
                    Timber.tag("GOOGLE LOCATION").e("경도: ${task.result.longitude}")
                    Timber.tag("GOOGLE LOCATION").e("속도: ${task.result.speed}")
                }
            }
        } catch (e: SecurityException) {
            Timber.tag("Error").e(e)
        }
    }


}