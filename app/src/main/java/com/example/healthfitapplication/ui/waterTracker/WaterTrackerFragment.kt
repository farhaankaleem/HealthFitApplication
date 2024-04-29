package com.example.healthfitapplication.ui.waterTracker

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.healthfitapplication.databinding.ActivityWaterTrackerBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class WaterTrackerFragment : Fragment() {

    private var _binding: ActivityWaterTrackerBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var waterGlassesGoal: Int = 0
    private var waterCountDB: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityWaterTrackerBinding.inflate(inflater, container, false)
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
            getWaterCount(userId)
            getWaterGoal(userId)
        }

        binding.waterButton.setOnClickListener {
            val incCount = binding.waterCountTextView.text.toString().toInt() + 1
            binding.waterCountTextView.text = incCount.toString()

            val currentUser = auth.currentUser
            currentUser?.let { user ->
                val userId = user.uid
                setWaterCount(userId, incCount)
            }

            if (incCount == (waterGlassesGoal + 1)) {
                Toast.makeText(requireContext(), "Hurray!!! you achieved your goal", Toast.LENGTH_LONG).show()
            }
        }

        binding.resetButton.setOnClickListener {
            binding.waterCountTextView.text = "0"

            val currentUser = auth.currentUser
            currentUser?.let { user ->
                val userId = user.uid
                setWaterCount(userId, 0)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getWaterCount(userId: String) {
        val userWeightRef = database.child("users").child(userId).child("currentWaterCount")
        userWeightRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val waterCount = dataSnapshot.getValue(Int::class.java)
                waterCount?.let {
                    waterCountDB = it
                    binding.waterCountTextView.text = waterCountDB.toString()
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("WorkoutExplain", "Failed to read user's weight: ${databaseError.message}")
            }
        })
    }

    private fun getWaterGoal(userId: String) {
        val userWeightRef = database.child("users").child(userId).child("waterGlassesGoal")
        userWeightRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val waterCount = dataSnapshot.getValue(Int::class.java)
                waterCount?.let {
                    waterGlassesGoal = it
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("WorkoutExplain", "Failed to read user's weight: ${databaseError.message}")
            }
        })
    }

    private fun setWaterCount(userId: String, count: Int) {
        val waterCountRef = database.child("users").child(userId).child("currentWaterCount")
        waterCountRef.setValue(count)
            .addOnSuccessListener {
                Log.d("WaterTrackerFragment", "Water count set successfully")
            }
            .addOnFailureListener { e ->
                Log.e("WaterTrackerFragment", "Failed to update water count: ${e.message}")
                Toast.makeText(requireContext(), "Failed to update water count: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
