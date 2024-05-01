package com.example.healthfitapplication

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*

class WorkoutExplain : AppCompatActivity() {
    private val metValues = mapOf(
        1 to 8.0, 2 to 3.8, 3 to 3.8, 4 to 8.0, 5 to 6.0, 6 to 4.0,
        7 to 6.0, 8 to 3.8, 9 to 7.0, 10 to 2.5, 11 to 3.5, 12 to 5.8,
        13 to 3.0, 14 to 6.0, 15 to 3.0
    )
    private val workoutLayouts = mapOf(
        1 to R.layout.activity_mountclimber, 2 to R.layout.activity_crunch,
        3 to R.layout.activity_benchdips, 4 to R.layout.activity_cyclecrunch,
        5 to R.layout.activity_legraise, 6 to R.layout.activity_altheeltouch,
        7 to R.layout.activity_legupcrunch, 8 to R.layout.activity_situp,
        9 to R.layout.activity_altvups, 10 to R.layout.activity_plank,
        11 to R.layout.activity_plankleglift, 12 to R.layout.activity_russiantwist,
        13 to R.layout.activity_bridge, 14 to R.layout.activity_vlegcrunch,
        15 to R.layout.activity_windmill
    )
    private lateinit var countDownTimer: CountDownTimer
    private var isTimeRunning = false
    private lateinit var startButton: Button
    private lateinit var backButton: ImageView
    private lateinit var stopWatch: Button
    private var timeRemaining: Long = 0
    private var workoutNo: Int = 0

    private lateinit var auth: FirebaseAuth
    private lateinit var database: DatabaseReference
    private var userWeightKg: Double = 0.0

    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_explain)

        auth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance().reference

        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val userId = user.uid
            fetchUserWeight(userId)
        }

        workoutNo = intent.getStringExtra("workoutNo")?.toIntOrNull() ?: 0
        val metValue = metValues[workoutNo] ?: 0.0
        val layoutResId = workoutLayouts[workoutNo]
        layoutResId?.let { setContentView(it) }

        startButton = findViewById(R.id.startButton)
        stopWatch = findViewById(R.id.time)
        backButton = findViewById(R.id.back)

        backButton.setOnClickListener { finish() }
        startButton.setOnClickListener {
            if (isTimeRunning) {
                stopTimer()
            } else {
                startTimer(metValue)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finish()
    }

    private fun fetchUserWeight(userId: String) {
        val userWeightRef = database.child("users").child(userId).child("weight")
        userWeightRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val weight = dataSnapshot.getValue(Double::class.java)
                weight?.let { userWeightKg = it }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                Log.e("WorkoutExplain", "Failed to read user's weight: ${databaseError.message}")
            }
        })
    }

    private fun stopTimer() {
        countDownTimer.cancel()
        isTimeRunning = false
        startButton.text = "START"
    }

    private fun startTimer(metValue: Double) {
        val val1 = stopWatch.text
        val num1 = val1.toString()
        val num2 = num1.substring(0, 2)
        val num3 = num1.substring(3, 5)

        val caloriesBurnedPerMinute = metValue * userWeightKg * 3.5 / 200

        val num = num2.toInt() * 60 + num3.toInt()
        timeRemaining = (num * 1000).toLong()
        countDownTimer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                updateTimerUI()
            }

            override fun onFinish() {
                updateTotalCaloriesBurnt(caloriesBurnedPerMinute)
            }
        }.start()
        startButton.text = "PAUSE"
        isTimeRunning = true
    }

    private fun updateTotalCaloriesBurnt(caloriesBurnedPerMinute: Double) {
        val currentUser = auth.currentUser
        currentUser?.let { user ->
            val userId = user.uid
            val calBurntRef =
                database.child("users").child(userId).child("totalCaloriesBurned")
            calBurntRef.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    val currentTotalCalories = dataSnapshot.getValue(Double::class.java) ?: 0.0
                    val newTotalCalories = currentTotalCalories + caloriesBurnedPerMinute
                    calBurntRef.setValue(newTotalCalories)
                        .addOnSuccessListener {
                            Log.d("WorkoutExplain", "Total Calories Burnt updated successfully")
                        }
                        .addOnFailureListener { e ->
                            Log.e("WorkoutExplain", "Failed to update Total Calories Burnt: ${e.message}")
                        }
                    finish()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    Log.e("WorkoutExplain", "Failed to read Total Calories Burnt: ${databaseError.message}")
                }
            })
        }
    }

    private fun updateTimerUI() {
        var minutes = timeRemaining / 60000
        var seconds = timeRemaining % 60000 / 1000

        var timeLeft = ""
        if (minutes < 10) {
            timeLeft = "0"
        }
        timeLeft = timeLeft + minutes + ":"
        if (seconds < 10) {
            timeLeft += "0"
        }
        timeLeft += seconds
        stopWatch.text = timeLeft
    }
}