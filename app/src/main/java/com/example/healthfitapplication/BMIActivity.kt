package com.example.healthfitapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class BMIActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bmi)

        val height = findViewById<EditText>(R.id.editHeight)
        val weight = findViewById<EditText>(R.id.editWeight)
        val calcBtn = findViewById<Button>(R.id.buttonCalcBMI)
        val result = findViewById<TextView>(R.id.resultBMI)

        calcBtn.setOnClickListener{
            val ht = height.text.toString().toFloat() / 100
            val wt = weight.text.toString().toFloat()
            val res = wt / (ht * ht)

            var healthCondition = ""

            if (res < 18.5) {
                healthCondition = "underweight"
            } else if (res > 30) {
                healthCondition = "obese"
            } else if (res > 18.5 && res < 24.9) {
                healthCondition = "healthy"
            } else if (res > 25.0 && res < 29.99) {
                healthCondition = "overweight"
            }

            result.text = "BMI = $res. \nDepending on your BMI, you are $healthCondition."
        }
    }
}