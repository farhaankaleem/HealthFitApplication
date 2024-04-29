package com.example.healthfitapplication.ui.home

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.healthfitapplication.databinding.FragmentHomeBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import java.time.LocalDate

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val currentDate = LocalDate.now().toString()
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val userId = user.uid
            val loggedDateRef =
                database.child("users").child(userId).child("loggedDate")
            loggedDateRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val loggedDate = dataSnapshot.getValue(String::class.java)
                    if (loggedDate != currentDate) {
                        updateLoggedDate(userId, currentDate)
                        resetToZero(userId)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e(
                        "WorkoutExplain",
                        "Failed to read logged-in date: ${databaseError.message}"
                    )
                }
            })
        }

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun updateLoggedDate(userId: String, currentDate: String) {
        val LogDateRef =
            database.child("users").child(userId).child("loggedDate")
        LogDateRef.setValue(currentDate)
            .addOnSuccessListener {
                Log.d("WorkoutExplain", "Logged Date set successfully")
            }
            .addOnFailureListener { e ->
                Log.e("WorkoutExplain", "Failed to set logged date: ${e.message}")
            }
    }

    private fun resetToZero(userId: String) {
        val calBurntRef =
            database.child("users").child(userId).child("totalCaloriesBurned")

        val waterCountRef =
            database.child("users").child(userId).child("currentWaterCount")

        val stepsRef =
            database.child("users").child(userId).child("stepsTaken")

        calBurntRef.setValue(0)
            .addOnSuccessListener {
                Log.d("HomeFragment", "Total Calories Burnt reset successfully")
            }
            .addOnFailureListener { e ->
                Log.e("HomeFragment", "Failed to reset Total Calories Burnt: ${e.message}")
            }

        waterCountRef.setValue(0)
            .addOnSuccessListener {
                Log.d("HomeFragment", "Water count reset successfully")
            }
            .addOnFailureListener { e ->
                Log.e("HomeFragment", "Failed to reset Water count: ${e.message}")
            }

        stepsRef.setValue(0)
            .addOnSuccessListener {
                Log.d("HomeFragment", "Total Steps reset successfully")
            }
            .addOnFailureListener { e ->
                Log.e("HomeFragment", "Failed to reset Total Steps: ${e.message}")
            }
    }
}