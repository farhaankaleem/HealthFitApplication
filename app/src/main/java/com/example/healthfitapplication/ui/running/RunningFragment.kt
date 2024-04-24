package com.example.healthfitapplication.ui.running

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.healthfitapplication.databinding.ActivityRunningBinding

class RunningFragment : Fragment(), SensorEventListener {

    private var _binding: ActivityRunningBinding? = null
    private val binding get() = _binding!!
    private var sensor: Sensor? = null
    private var sensorMgr: SensorManager? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityRunningBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorMgr = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorMgr!!.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)
    }

    override fun onResume() {
        super.onResume()
        if (sensor == null) {
            Toast.makeText(requireContext(), "Sensor not found", Toast.LENGTH_SHORT).show()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
