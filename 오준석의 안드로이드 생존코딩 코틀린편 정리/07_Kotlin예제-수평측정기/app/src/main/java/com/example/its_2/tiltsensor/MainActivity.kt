package com.example.its_2.tiltsensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.WindowManager

class MainActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var tiltView: TiltView // 늦은 초기화 선언
    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        // 화면이 꺼지지 않도록 수정
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        // 화면을 가로 모드로 고정
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)

        tiltView = TiltView(this)
        setContentView(tiltView)
    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER/*가속도 센서*/)
                , SensorManager.SENSOR_DELAY_NORMAL) // 센서 등록
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this) // 센서 해제
    }

    // 센서값이 변경되면 호출
    override fun onSensorChanged(event: SensorEvent?) {
        // values [0] : x축 값 : 위로 기울이면 -10~0, 아래로 기울이면 0~10
        // values [1] : y축 값 : 왼쪽으로 기울이면 -10~0, 오른쪽으로 기울이면 0~10
        // values [2] : z축 값 : 미사용

        event?.let {
            Log.d("MainActivity", "onSensorChanged : x :" + "${event.values[0]}, y : ${event.values[1]}, z : ${event.values[2]}")
            tiltView.onSensorEvent(event)
        }
    }

    // 센서 정밀도가 변경되면 호출
    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}
