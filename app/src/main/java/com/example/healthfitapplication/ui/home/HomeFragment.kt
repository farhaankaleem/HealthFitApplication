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
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        homeViewModel.userName.observe(viewLifecycleOwner) { userName ->
            binding.userName.text = userName
        }

        homeViewModel.BMI.observe(viewLifecycleOwner) { BMI ->
            binding.BMIValue.text = BMI.toString()
        }

        homeViewModel.healthCondition.observe(viewLifecycleOwner) { healthCondition ->
            binding.health.text = healthCondition
        }

        homeViewModel.caloriesGoal.observe(viewLifecycleOwner) {
            homeViewModel.caloriesGoal.value?.let { goal ->
                homeViewModel.caloriesBurnt.value?.let { burnt ->
                    homeViewModel.remainingCalories.value = goal - burnt
                    homeViewModel.remainingCaloriesPercent.value = (burnt / goal) * 100
                }
            }
        }

        homeViewModel.caloriesBurnt.observe(viewLifecycleOwner) {
            homeViewModel.caloriesGoal.value?.let { goal ->
                homeViewModel.caloriesBurnt.value?.let { burnt ->
                    homeViewModel.remainingCalories.value = goal - burnt
                    homeViewModel.remainingCaloriesPercent.value = (burnt / goal) * 100
                }
            }
        }

        homeViewModel.stepsTaken.observe(viewLifecycleOwner) {
            homeViewModel.stepsGoal.value?.let { goal ->
                homeViewModel.stepsTaken.value?.let { steps ->
                    homeViewModel.remainingSteps.value = goal - steps
                    homeViewModel.remainingStepsPercent.value = (steps / goal) * 100
                }
            }
        }

        homeViewModel.stepsGoal.observe(viewLifecycleOwner) {
            homeViewModel.stepsGoal.value?.let { goal ->
                homeViewModel.stepsTaken.value?.let { steps ->
                    homeViewModel.remainingSteps.value = goal - steps
                    homeViewModel.remainingStepsPercent.value = (steps / goal) * 100
                }
            }
        }

        homeViewModel.waterDrank.observe(viewLifecycleOwner) {
            homeViewModel.waterGoal.value?.let { goal ->
                homeViewModel.waterDrank.value?.let { water ->
                    homeViewModel.remainingWater.value = goal - water
                    homeViewModel.remainingWaterPercent.value = (water / goal) * 100
                }
            }
        }

        homeViewModel.waterGoal.observe(viewLifecycleOwner) {
            homeViewModel.waterGoal.value?.let { goal ->
                homeViewModel.waterDrank.value?.let { water ->
                    homeViewModel.remainingWater.value = goal - water
                    homeViewModel.remainingWaterPercent.value = (water / goal) * 100
                }
            }
        }

        homeViewModel.remainingCalories.observe(viewLifecycleOwner) { remainingCalories ->
            val remainingCaloriesFormatted: String = String.format("%.2f", remainingCalories)
            binding.caloriesRemain.text = "$remainingCaloriesFormatted"
        }

        homeViewModel.remainingCaloriesPercent.observe(viewLifecycleOwner) { percent ->
            binding.circularProgressBarCalories.setProgressWithAnimation(percent, 1000)
        }

        homeViewModel.remainingSteps.observe(viewLifecycleOwner) { remainingSteps ->
            val remainingStepsFormatted: String = String.format("%.2f", remainingSteps)
            binding.stepRemain.text = "$remainingStepsFormatted"
        }

        homeViewModel.remainingStepsPercent.observe(viewLifecycleOwner) { percent ->
            binding.circularProgressBarSteps.setProgressWithAnimation(percent, 1000)
        }

        homeViewModel.remainingWater.observe(viewLifecycleOwner) { remainingWater ->
            val formattedRemainingWater: String = String.format("%.2f", remainingWater)
            binding.waterRemain.text = "${formattedRemainingWater.toDouble().toInt()}"
        }

        homeViewModel.remainingWaterPercent.observe(viewLifecycleOwner) { percent ->
            binding.circularProgressBarWater.setProgressWithAnimation(percent, 1000)
        }

        homeViewModel.fetchDataAndUpdate()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}


//class HomeFragment : Fragment() {
//
//    private var _binding: FragmentHomeBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var auth: FirebaseAuth
//    private lateinit var database: DatabaseReference
//
//    private var caloriesGoal: Double = 0.0
//    private var caloriesBurnt: Double = 0.0
//
//    @RequiresApi(Build.VERSION_CODES.O)
//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        val homeViewModel =
//            ViewModelProvider(this).get(HomeViewModel::class.java)
//
//        _binding = FragmentHomeBinding.inflate(inflater, container, false)
//        val root: View = binding.root
//
//        auth = FirebaseAuth.getInstance()
//        database = FirebaseDatabase.getInstance().reference
//
//        val currentDate = LocalDate.now().toString()
//        val currentUser = auth.currentUser
//        currentUser?.let { user ->
//            val userId = user.uid
//            getUserString(userId, "firstName", binding.userName)
//            getUserString(userId, "healthCondition", binding.health)
//            setCalories(userId)
//            //getUserInt(userId, "caloriesGoal", bin)
//            getUserFloat(userId, "BMI", binding.BMIValue)
//            val loggedDateRef =
//                database.child("users").child(userId).child("loggedDate")
//            loggedDateRef.addListenerForSingleValueEvent(object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    val loggedDate = dataSnapshot.getValue(String::class.java)
//                    if (loggedDate != currentDate) {
//                        updateLoggedDate(userId, currentDate)
//                        resetToZero(userId)
//                    }
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    Log.e(
//                        "WorkoutExplain",
//                        "Failed to read logged-in date: ${databaseError.message}"
//                    )
//                }
//            })
//        }
//
////        val textView: TextView = binding.textHome
////        homeViewModel.text.observe(viewLifecycleOwner) {
////            textView.text = it
////        }
//        return root
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//
//    private fun updateLoggedDate(userId: String, currentDate: String) {
//        val LogDateRef =
//            database.child("users").child(userId).child("loggedDate")
//        LogDateRef.setValue(currentDate)
//            .addOnSuccessListener {
//                Log.d("WorkoutExplain", "Logged Date set successfully")
//            }
//            .addOnFailureListener { e ->
//                Log.e("WorkoutExplain", "Failed to set logged date: ${e.message}")
//            }
//    }
//
//    private fun getUserString(userId: String, dbAttribute: String, uiAttribute: TextView) {
//        val userNameRef = database.child("users").child(userId).child(dbAttribute)
//        userNameRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val name = dataSnapshot.getValue(String::class.java)
//                name?.let {
//                    uiAttribute.text = it
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.e("WorkoutExplain", "Failed to read user's weight: ${databaseError.message}")
//            }
//        })
//    }
//
//    private fun getUserInt(userId: String, dbAttribute: String, uiAttribute: TextView) {
//        val userNameRef = database.child("users").child(userId).child(dbAttribute)
//        userNameRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val name = dataSnapshot.getValue(Int::class.java)
//                name?.let {
//                    uiAttribute.text = it.toString()
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.e("WorkoutExplain", "Failed to read user's weight: ${databaseError.message}")
//            }
//        })
//    }
//
//    private fun getUserFloat(userId: String, dbAttribute: String, uiAttribute: TextView) {
//        val userNameRef = database.child("users").child(userId).child(dbAttribute)
//        userNameRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val name = dataSnapshot.getValue(Float::class.java)
//                name?.let {
//                    uiAttribute.text = it.toString()
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.e("WorkoutExplain", "Failed to read user's weight: ${databaseError.message}")
//            }
//        })
//    }
//
//    private fun getUserTotalCalories(userId: String, dbAttribute: String) {
//        val userNameRef = database.child("users").child(userId).child(dbAttribute)
//        userNameRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val name = dataSnapshot.getValue(Double::class.java)
//                name?.let {
//                    caloriesGoal = it
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.e("WorkoutExplain", "Failed to read user's weight: ${databaseError.message}")
//            }
//        })
//    }
//
//    private fun getUserTotalCaloriesBurnt(userId: String, dbAttribute: String) {
//        val userNameRef = database.child("users").child(userId).child(dbAttribute)
//        userNameRef.addListenerForSingleValueEvent(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                val name = dataSnapshot.getValue(Double::class.java)
//                name?.let {
//                    caloriesBurnt = it
//                }
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                Log.e("WorkoutExplain", "Failed to read user's weight: ${databaseError.message}")
//            }
//        })
//    }
//    private fun setCalories(userId: String) {
//        getUserTotalCalories(userId, "caloriesGoal")
//        getUserTotalCaloriesBurnt(userId, "totalCaloriesBurned")
//
//        val remainingCalories = caloriesGoal - caloriesBurnt
//
//        binding.caloriesRemain.text = "$remainingCalories Remaining"
//
//        val remainingCaloriesPercent = caloriesBurnt/caloriesGoal * 100
//
//        binding.circularProgressBarCalories.setProgressWithAnimation(remainingCaloriesPercent.toFloat(), 1000)
//    }
//    private fun resetToZero(userId: String) {
//        val calBurntRef =
//            database.child("users").child(userId).child("totalCaloriesBurned")
//
//        val waterCountRef =
//            database.child("users").child(userId).child("currentWaterCount")
//
//        val stepsRef =
//            database.child("users").child(userId).child("stepsTaken")
//
//        calBurntRef.setValue(0)
//            .addOnSuccessListener {
//                Log.d("HomeFragment", "Total Calories Burnt reset successfully")
//            }
//            .addOnFailureListener { e ->
//                Log.e("HomeFragment", "Failed to reset Total Calories Burnt: ${e.message}")
//            }
//
//        waterCountRef.setValue(0)
//            .addOnSuccessListener {
//                Log.d("HomeFragment", "Water count reset successfully")
//            }
//            .addOnFailureListener { e ->
//                Log.e("HomeFragment", "Failed to reset Water count: ${e.message}")
//            }
//
//        stepsRef.setValue(0)
//            .addOnSuccessListener {
//                Log.d("HomeFragment", "Total Steps reset successfully")
//            }
//            .addOnFailureListener { e ->
//                Log.e("HomeFragment", "Failed to reset Total Steps: ${e.message}")
//            }
//    }
//}