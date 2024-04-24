package com.example.healthfitapplication.ui.bmi

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.healthfitapplication.databinding.ActivityBmiBinding

class BMIFragment : Fragment() {
    private var _binding: ActivityBmiBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityBmiBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
