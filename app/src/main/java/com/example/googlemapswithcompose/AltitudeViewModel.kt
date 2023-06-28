package com.example.googlemapswithcompose

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class AltitudeViewModel @Inject constructor(
    @ApplicationContext application: Context,
) : ViewModel() {
    private val sensorManager: SensorManager = application.getSystemService(SENSOR_SERVICE) as SensorManager
    private val pressureSensor: Sensor? = sensorManager.getDefaultSensor(Sensor.TYPE_PRESSURE)

    private val _altitudeStateFlow: MutableStateFlow<Float?> = MutableStateFlow(null)
    val altitudeStateFlow: StateFlow<Float?> get() = _altitudeStateFlow

    // 고도 값을 업데이트하는 메서드
    private fun updateAltitude(altitude: Float) {
        Timber.tag("고도").e("$altitude")
        _altitudeStateFlow.value = altitude
    }

    // 기압 센서 이벤트 처리
    private val sensorEventListener: SensorEventListener = object : SensorEventListener {
        override fun onSensorChanged(event: SensorEvent) {
            if (event.sensor.type == Sensor.TYPE_PRESSURE) {
                val pressure = event.values[0]
                val altitude = SensorManager.getAltitude(SensorManager.PRESSURE_STANDARD_ATMOSPHERE, pressure)
                updateAltitude(altitude)
            }
        }

        override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {
            // 센서 정확도 변경 시 호출되는 콜백 메서드
        }
    }

    // 고도 측정 시작
    fun startAltitudeMeasurement() {
        // 기압 센서 등록 등의 로직 수행


        // 고도 갱신 리스너 등록
        sensorManager.registerListener(sensorEventListener, pressureSensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    // 고도 측정 종료
    fun stopAltitudeMeasurement() {
        // 기압 센서 등록 해제 등의 로직 수행

        // 고도 갱신 리스너 등록 해제
        sensorManager.unregisterListener(sensorEventListener)
    }
}