package com.example.healthfitapplication

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.healthfitapplication.databinding.ActivityRunningBinding
import android.widget.Toast

class RunningActivity : AppCompatActivity(), SensorEventListener {

    private lateinit var binding: ActivityRunningBinding
    private var sensor: Sensor? = null
    private var sensorMgr: SensorManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRunningBinding.inflate(layoutInflater)
        setContentView(binding.root)

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

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let {
            val stepsTaken = it.values[0]
            binding.stepsTaken.text = stepsTaken.toString()
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }
}