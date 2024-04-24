package com.example.healthfitapplication

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.healthfitapplication.databinding.ActivityWaterTrackerBinding
import java.time.LocalDate

class WaterTrackerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWaterTrackerBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWaterTrackerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)
        val waterCount = sharedPreferences.getString("WATERCOUNT", "0")
        val editor = sharedPreferences.edit()

        binding.waterCountTextView.text = waterCount

        binding.waterButton.setOnClickListener{
            val incCount = binding.waterCountTextView.text.toString().toInt() + 1
            binding.waterCountTextView.text = incCount.toString()
            editor.putString("WATERCOUNT", incCount.toString())
            editor.apply()

            //Get this 8 from DB when they signup
            if (incCount == (8 + 1)) {
                Toast.makeText(this, "Hurray!!! you achieved your goal", Toast.LENGTH_LONG).show()
            }
        }

        binding.resetButton.setOnClickListener{
            binding.waterCountTextView.text = "0"
            editor.putString("WATERCOUNT", "0")
            editor.apply()
        }

        val date = LocalDate.now()
        val lastLoggedTime = sharedPreferences.getString("LASTLOGGEDTIME", date.toString()).toString()
        if (lastLoggedTime != date.toString())
        {
            editor.putString("LASTLOGGEDTIME", date.toString())
            editor.putString("WATERCOUNT", "0")
            editor.apply()
            binding.waterCountTextView.text = "0"
        }
    }
}