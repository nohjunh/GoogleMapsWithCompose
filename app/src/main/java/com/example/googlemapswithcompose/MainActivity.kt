package com.example.googlemapswithcompose

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.pm.PackageManager
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

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val requestPermissionLauncher =
        // 사용자로부터 위치 접근 권한을 요청하고 그 결과를 받아 처리하기 위한 코드
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) { // 사용자가 권한을 승인하면
                Timber.tag("Activity").d("위치 접근 권한 승인")
            }
        }

    // 사용자로부터 위치 접근 권한이 부여되어 있는지 확인하고, 부여되어 있지 않다면 권한을 요청
    private fun askPermissions() {
        if (PackageManager.PERMISSION_GRANTED != ContextCompat.checkSelfPermission(this, ACCESS_FINE_LOCATION)) {
            requestPermissionLauncher.launch(ACCESS_FINE_LOCATION)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        askPermissions()

        setContent {
            MainScreen()
        }
    }
}
