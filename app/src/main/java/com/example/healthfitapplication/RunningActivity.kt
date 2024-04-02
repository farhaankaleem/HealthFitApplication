package com.example.healthfitapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast

class RunningActivity : AppCompatActivity(), SensorEventListener {

    var sensor: Sensor? = null
    var sensorMgr: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_running)

        sensorMgr = getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorMgr!!.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    override fun onResume() {
        super.onResume()
        if (sensor == null) {
            Toast.makeText(this, "Sensor not found", Toast.LENGTH_SHORT).show()
        } else {
            sensorMgr?.registerListener(this, sensor, SensorManager.SENSOR_DELAY_NORMAL)
        }
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val stepsTaken = p0!!.values[0]
        var steps = findViewById<TextView>(R.id.stepsTaken)
        steps.text = stepsTaken.toString()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }
}