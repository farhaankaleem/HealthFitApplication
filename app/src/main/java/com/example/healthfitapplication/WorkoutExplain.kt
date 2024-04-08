package com.example.healthfitapplication

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class WorkoutExplain : AppCompatActivity() {
    private val workoutLayouts = mapOf(
        1 to R.layout.activity_mountclimber,
        2 to R.layout.activity_crunch,
        3 to R.layout.activity_benchdips,
        4 to R.layout.activity_cyclecrunch,
        5 to R.layout.activity_legraise,
        6 to R.layout.activity_altheeltouch,
        7 to R.layout.activity_legupcrunch,
        8 to R.layout.activity_situp,
        9 to R.layout.activity_altvups,
        10 to R.layout.activity_plank,
        11 to R.layout.activity_plankleglift,
        12 to R.layout.activity_russiantwist,
        13 to R.layout.activity_bridge,
        14 to R.layout.activity_vlegcrunch,
        15 to R.layout.activity_windmill
    )
    private lateinit var countDownTimer : CountDownTimer
    private var isTimeRunning = false
    private lateinit var startButton : Button
    private lateinit var stopWatch : Button
    private var timeRemaining : Long = 0
    private var workoutNo : Int = 0

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_explain)
        workoutNo = intent.getStringExtra("workoutNo") ?.toIntOrNull() ?: 0
        val layoutResId = workoutLayouts[workoutNo]
        if (layoutResId != null) {
            setContentView(layoutResId)
        }

        startButton = findViewById(R.id.startButton)
        stopWatch = findViewById(R.id.time)

        startButton.setOnClickListener{
            if (isTimeRunning) {
                stopTimer()
            } else {
                startTimer()
            }
        }


        }

    override fun onBackPressed() {
        super.onBackPressed()

    }

    private fun stopTimer () {
        countDownTimer.cancel();
        isTimeRunning = false
        startButton.setText("START")

    }

    private fun startTimer () {
        var val1 = stopWatch.text
        var num1 = val1.toString()
        var num2 = num1.substring(0,2)
        var num3 = num1.substring(3,5)

        val num = num2.toInt() * 60 + num3.toInt();
        timeRemaining = (num * 1000).toLong()
        countDownTimer = object : CountDownTimer(timeRemaining, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeRemaining = millisUntilFinished
                updateTimerUI()
            }

            override fun onFinish() {
                var newValue = workoutNo + 1
                if (newValue <= 15) {
                    val i = Intent(this@WorkoutExplain, WorkoutExplain::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    i.putExtra("workoutNo", newValue.toString())
                    startActivity(i)
                } else {
                    newValue = 1
                    val i = Intent(this@WorkoutExplain, WorkoutExplain::class.java)
                    i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    i.putExtra("workoutNo", newValue.toString())
                    startActivity(i)
                }
            }
        }.start()
        startButton.setText("PAUSE")
        isTimeRunning = true
    }

    private fun updateTimerUI() {
        var minutes = timeRemaining/60000
        var seconds = timeRemaining%60000 / 1000

        var timeLeft = ""
        if (minutes<10) {
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