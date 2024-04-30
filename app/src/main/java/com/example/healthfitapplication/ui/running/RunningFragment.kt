package com.example.healthfitapplication.ui.running

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.healthfitapplication.databinding.ActivityRunningBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RunningFragment : Fragment(), SensorEventListener {

    private var _binding: ActivityRunningBinding? = null
    private val binding get() = _binding!!
    private var sensor: Sensor? = null
    private var sensorMgr: SensorManager? = null

    private lateinit var viewModel: RunningViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityRunningBinding.inflate(inflater, container, false)
        viewModel = ViewModelProvider(this).get(RunningViewModel::class.java)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        sensorMgr = requireActivity().getSystemService(Context.SENSOR_SERVICE) as SensorManager
        sensor = sensorMgr!!.getDefaultSensor(Sensor.TYPE_STEP_COUNTER)

        viewModel.fetchDataAndUpdate()
        viewModel.stepsGoal.observe(viewLifecycleOwner) { stepsGoal ->
            binding.totalSteps.text = "/$stepsGoal"
        }
        viewModel.stepsTaken.observe(viewLifecycleOwner) { stepsTaken ->
            binding.stepsTaken.text = stepsTaken.toString()
        }
        viewModel.progressPercentage.observe(viewLifecycleOwner) { progressPercentage ->
            binding.circularProgressBar.setProgressWithAnimation(progressPercentage, 1000)
        }
    }

    override fun onResume() {
        super.onResume()
        sensor?.let {
            sensorMgr?.registerListener(this, it, SensorManager.SENSOR_DELAY_NORMAL)
        } ?: Toast.makeText(requireContext(), "Sensor not found", Toast.LENGTH_SHORT).show()
    }

    override fun onSensorChanged(event: SensorEvent?) {
        event?.let { sensorEvent ->
            val stepsTaken = sensorEvent.values[0]
            viewModel.updateStepsTaken(stepsTaken)
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
