package com.example.googlemapswithcompose

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MainScreen(
    viewModel: MainViewModel = hiltViewModel()
) {
    val locationUpdates by viewModel.locationUpdates.collectAsState()

    DisposableEffect(Unit) {
        viewModel.startLocationUpdates()

        onDispose {
            viewModel.stopLocationUpdates()
        }
    }

    val university = LatLng(35.887957, 128.606700)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(university, 17.5f)
    }
    Column(
        modifier = Modifier.fillMaxSize()
    ){
        GoogleMap(
            modifier = Modifier.height(300.dp).fillMaxWidth(),
            cameraPositionState = cameraPositionState
        ) {
            Polyline(
                points = listOf(
                    LatLng(35.888114, 128.606161),
                    LatLng(35.888412, 128.606123),
                    LatLng(35.888536, 128.606900),
                    LatLng(35.888128, 128.607003),
                    LatLng(35.887592, 128.607152),
                    LatLng(35.887482, 128.606342),
                    LatLng(35.888010, 128.606182)
                )
                ,color = Color.Red
            )
        }
        LazyColumn {
            locationUpdates.forEach { location ->
                item {
                    Text("latitude: ${location.lastLocation!!.latitude}")
                    Text("longitude: ${location.lastLocation!!.longitude}")
                    Spacer(modifier = Modifier.size(10.dp))
                }
            }
        }
    }
    Button(
        onClick = {
            cameraPositionState.move(CameraUpdateFactory.zoomIn())
        }
    ) {
        Text(text = "Zoom In")
    }

}
