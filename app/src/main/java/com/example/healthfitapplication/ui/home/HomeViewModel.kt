package com.example.healthfitapplication.ui.home

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.time.LocalDate

class HomeViewModel : ViewModel() {
    // Define LiveData objects to hold data
    val userName: MutableLiveData<String> = MutableLiveData()
    val BMI: MutableLiveData<Float> = MutableLiveData()
    val healthCondition: MutableLiveData<String> = MutableLiveData()
    val caloriesGoal: MutableLiveData<Float> = MutableLiveData()
    val caloriesBurnt: MutableLiveData<Float> = MutableLiveData()
    var remainingCalories: MutableLiveData<Float> = MutableLiveData()
    var remainingCaloriesPercent: MutableLiveData<Float> = MutableLiveData()

    var stepsGoal: MutableLiveData<Float> = MutableLiveData()
    var stepsTaken: MutableLiveData<Float> = MutableLiveData()
    var remainingSteps: MutableLiveData<Float> = MutableLiveData()
    var remainingStepsPercent: MutableLiveData<Float> = MutableLiveData()

    var waterGoal: MutableLiveData<Float> = MutableLiveData()
    var waterDrank: MutableLiveData<Float> = MutableLiveData()
    var remainingWater: MutableLiveData<Float> = MutableLiveData()
    var remainingWaterPercent: MutableLiveData<Float> = MutableLiveData()

    // Method to fetch data from database and update LiveData objects
    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchDataAndUpdate() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            val userId = user.uid
            val database = FirebaseDatabase.getInstance().reference

            getUserString(userId, "firstName", userName)

            getUserString(userId, "healthCondition", healthCondition)
            getUserFloat(userId, "BMI", BMI)

            getUserFloat(userId, "caloriesGoal", caloriesGoal)
            getUserFloat(userId, "totalCaloriesBurned", caloriesBurnt)

            getUserFloat(userId, "stepsGoal", stepsGoal)
            getUserFloat(userId, "stepsTaken", stepsTaken)

            getUserFloat(userId, "waterGlassesGoal", waterGoal)
            getUserFloat(userId, "currentWaterCount", waterDrank)

            val currentDate = LocalDate.now().toString()
            val loggedDateRef = database.child("users").child(userId).child("loggedDate")
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
    }

    private fun updateLoggedDate(userId: String, currentDate: String) {
        val logDateRef =
            FirebaseDatabase.getInstance().reference.child("users").child(userId).child("loggedDate")
        logDateRef.setValue(currentDate)
            .addOnSuccessListener {
                Log.d("WorkoutExplain", "Logged Date set successfully")
            }
            .addOnFailureListener { e ->
                Log.e("WorkoutExplain", "Failed to set logged date: ${e.message}")
            }
    }

    private fun getUserString(userId: String, dbAttribute: String, liveData: MutableLiveData<String>) {
        val attributeRef = FirebaseDatabase.getInstance().reference.child("users").child(userId).child(dbAttribute)
        attributeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(String::class.java)
                value?.let {
                    liveData.value = it
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("WorkoutExplain", "Failed to read $dbAttribute: ${databaseError.message}")
            }
        })
    }

    private fun getUserFloat(userId: String, dbAttribute: String, liveData: MutableLiveData<Float>) {
        val attributeRef = FirebaseDatabase.getInstance().reference.child("users").child(userId).child(dbAttribute)
        attributeRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val value = dataSnapshot.getValue(Float::class.java)
                value?.let {
                    liveData.value = it
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("WorkoutExplain", "Failed to read $dbAttribute: ${databaseError.message}")
            }
        })
    }


    private fun resetToZero(userId: String) {
        val database = FirebaseDatabase.getInstance().reference
        val calBurntRef = database.child("users").child(userId).child("totalCaloriesBurned")
        val waterCountRef = database.child("users").child(userId).child("currentWaterCount")
        val stepsRef = database.child("users").child(userId).child("stepsTaken")

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

//class HomeViewModel : ViewModel() {
//
//    private val _text = MutableLiveData<String>().apply {
//        value = ""
//    }
//    val text: LiveData<String> = _text
//}