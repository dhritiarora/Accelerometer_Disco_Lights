package com.example.accelerometer_disco_lights

import android.content.Context
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

lateinit var sensorEventListener: SensorEventListener
lateinit var sm : SensorManager
lateinit var accelSensor:Sensor
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sm= getSystemService(Context.SENSOR_SERVICE) as SensorManager
        accelSensor=sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        sensorEventListener=object : SensorEventListener{
            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                Log.d("SENSOR","OnAccuracyChanged")
            }

            override fun onSensorChanged(event: SensorEvent?) {
               event?.values?.let {
                   val bgcolor= accelToColor(it[0],it[1],it[2])
                   bgLayout.setBackgroundColor(bgcolor)
               }
            }

        }
    }

    private fun accelToColor(ax: Float, ay: Float, az: Float): Int {

            val R =(((ax+12)/24)*255).toInt()
            val G =(((ay+12)/24)*255).toInt()
             val B =(((az+12)/24)*255).toInt()
        return Color.rgb(R,G,B)
    }

    override fun onResume() {
        super.onResume()
        sm.registerListener( sensorEventListener , accelSensor ,1000*1000)
    }

    override fun onPause() {

        sm.unregisterListener(sensorEventListener)
        super.onPause()
    }
}

