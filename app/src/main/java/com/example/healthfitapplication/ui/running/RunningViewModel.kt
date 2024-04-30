package com.example.healthfitapplication.ui.running

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class RunningViewModel : ViewModel() {

    private lateinit var userId: String
    private lateinit var database: DatabaseReference

    val stepsGoal: MutableLiveData<Int> = MutableLiveData()
    val stepsTaken: MutableLiveData<Int> = MutableLiveData()
    val progressPercentage: MutableLiveData<Float> = MutableLiveData()

    @RequiresApi(Build.VERSION_CODES.O)
    fun fetchDataAndUpdate() {
        val currentUser = FirebaseAuth.getInstance().currentUser
        currentUser?.let { user ->
            userId = user.uid
            database = FirebaseDatabase.getInstance().reference

            val stepsGoalRef = database.child("users").child(userId).child("stepsGoal")
            val stepsTakenRef = database.child("users").child(userId).child("stepsTaken")

            stepsGoalRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val goal = dataSnapshot.getValue(Int::class.java) ?: 0
                    stepsGoal.value = goal
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("RunningViewModel", "Failed to read steps goal: ${databaseError.message}")
                }
            })

            stepsTakenRef.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val taken = dataSnapshot.getValue(Int::class.java) ?: 0
                    stepsTaken.value = taken
                    val progress = (taken.toFloat() / stepsGoal.value!!) * 100
                    progressPercentage.value = progress
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("RunningViewModel", "Failed to read steps taken: ${databaseError.message}")
                }
            })
        }
    }

    fun updateStepsTaken(steps: Float) {
        val stepsTakenRef = database.child("users").child(userId).child("stepsTaken")
        stepsTakenRef.setValue(steps.toInt())
            .addOnSuccessListener {
                Log.d("RunningViewModel", "Steps count updated successfully")
            }
            .addOnFailureListener { e ->
                Log.e("RunningViewModel", "Failed to update steps count: ${e.message}")
            }
    }
}
