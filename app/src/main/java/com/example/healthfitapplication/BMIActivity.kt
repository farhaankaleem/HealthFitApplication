package com.example.healthfitapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.healthfitapplication.databinding.ActivityBmiBinding

class BMIActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBmiBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBmiBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonCalcBMI.setOnClickListener {
            val height = binding.editHeight.text.toString().toFloat() / 100
            val weight = binding.editWeight.text.toString().toFloat()
            val bmi = weight / (height * height)

            val healthCondition = when {
                bmi < 18.5 -> "underweight"
                bmi >= 18.5 && bmi < 24.9 -> "healthy"
                bmi >= 25.0 && bmi < 29.99 -> "overweight"
                else -> "obese"
            }

            binding.resultBMI.text = "BMI = $bmi. \nDepending on your BMI, you are $healthCondition."
        }
    }
}