package com.example.healthfitapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.widget.ImageView
import android.widget.Toast

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        supportActionBar?.hide()

        val bmi = findViewById<ImageView>(R.id.imgBMI)
        val run = findViewById<ImageView>(R.id.imgRun)
        val water = findViewById<ImageView>(R.id.imgWater)

        bmi.setOnClickListener{
            val i = Intent(this, BMIActivity::class.java)
            startActivity(i)
        }

        run.setOnClickListener{
            val i = Intent(this, RunningActivity::class.java)
            startActivity(i)
        }

        water.setOnClickListener{
            val i = Intent(this, WaterTrackerActivity::class.java)
            startActivity(i)
        }
    }
}