package com.example.healthfitapplication

import android.content.Context
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import java.time.LocalDate

class WaterTrackerActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_water_tracker)

        val sharedPreferences = getSharedPreferences("SP_INFO", MODE_PRIVATE)
        val waterCount = sharedPreferences.getString("WATERCOUNT", "0")
        val editor = sharedPreferences.edit()

        val addWater = findViewById<Button>(R.id.waterButton)
        val count = findViewById<TextView>(R.id.waterCountTextView)
        count.text = waterCount

        addWater.setOnClickListener{
            val incCount = count.text.toString().toInt() + 1
            count.text = "$incCount"
            editor.putString("WATERCOUNT", count.text.toString())
            editor.apply()

            //Get this 8 from DB when they signup
            if (count.text.toString().toInt() == (8 + 1)) {
                Toast.makeText(this, "Hurray!!! you achieved your goal", Toast.LENGTH_LONG).show()
            }
        }

        val reset = findViewById<Button>(R.id.resetButton)
        reset.setOnClickListener{
            count.text = "0"
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
            count.text = "0"
        }
    }
}