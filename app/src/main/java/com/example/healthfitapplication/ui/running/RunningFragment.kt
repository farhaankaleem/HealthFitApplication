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
import com.example.healthfitapplication.databinding.ActivityRunningBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate

class RunningFragment : Fragment(), SensorEventListener {

    private var _binding: ActivityRunningBinding? = null
    private val binding get() = _binding!!
    private var sensor: Sensor? = null
    private var sensorMgr: SensorManager? = null

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var totalSteps: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityRunningBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val userId = user.uid
            val stepsCountRef = database.child("users").child(userId).child("stepsGoal")

            stepsCountRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    totalSteps = snapshot.getValue(Int::class.java) ?: 0
                    binding.totalSteps.text = "/"+totalSteps.toString()
                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }

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
            val currentUser = auth.currentUser
            currentUser?.let { user ->
                val userId = user.uid
                val stepsTakenRef = database.child("users").child(userId).child("stepsTaken")
                stepsTakenRef.setValue(stepsTaken)
                    .addOnSuccessListener {
                        Log.d("RunningFragment", "Steps count updated successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("RunningFragment", "Failed to update steps count: ${e.message}")
                        Toast.makeText(requireContext(), "Failed to update steps count: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
