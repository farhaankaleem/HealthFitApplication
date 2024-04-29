package com.example.healthfitapplication.ui.bmi

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.healthfitapplication.databinding.ActivityBmiBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class BMIFragment : Fragment() {
    private var _binding: ActivityBmiBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityBmiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonCalcBMI.setOnClickListener {
            val height = binding.editHeight.text.toString().toFloat() / 100
            val weight = binding.editWeight.text.toString().toFloat()
            val bmi = weight / (height * height)

            val healthCondition = when {
                bmi < 18.5 -> "underweight"
                bmi >= 18.5 && bmi < 24.9 -> "healthy"
                bmi >= 25.0 && bmi < 29.99 -> "overweight"
                else -> "obese"
            }

            auth = FirebaseAuth.getInstance()
            database = FirebaseDatabase.getInstance().reference

            val currentUser = auth.currentUser

            currentUser?.let { user ->
                val userId = user.uid

                val BMIRef = database.child("users").child(userId).child("BMI")
                BMIRef.setValue(bmi)
                    .addOnSuccessListener {
                        Log.d("BMIActivity", "BMI saved successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("BMIActivity", "Failed to save BMI: ${e.message}")
                    }

                val healthConditionRef = database.child("users").child(userId).child("healthCondition")
                healthConditionRef.setValue(healthCondition)
                    .addOnSuccessListener {
                        Log.d("BMIActivity", "Health Condition saved successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("BMIActivity", "Failed to save Health Condition: ${e.message}")
                    }

                val weightRef = database.child("users").child(userId).child("weight")
                weightRef.setValue(weight)
                    .addOnSuccessListener {
                        Log.d("BMIActivity", "Weight saved successfully")
                    }
                    .addOnFailureListener { e ->
                        Log.e("BMIActivity", "Failed to save Weight: ${e.message}")
                    }
            }

            binding.resultBMI.text = "BMI = $bmi. \nHealth Condition = $healthCondition."
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
