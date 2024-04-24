package com.example.healthfitapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.healthfitapplication.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.imgBMI.setOnClickListener{
            val i = Intent(this, BMIActivity::class.java)
            startActivity(i)
        }

        binding.imgRun.setOnClickListener{
            val i = Intent(this, RunningActivity::class.java)
            startActivity(i)
        }

        binding.imgWater.setOnClickListener{
            val i = Intent(this, WaterTrackerActivity::class.java)
            startActivity(i)
        }

        binding.imgYoga.setOnClickListener{
            val i = Intent(this, WorkoutListActivity::class.java)
            startActivity(i)
        }
    }
}