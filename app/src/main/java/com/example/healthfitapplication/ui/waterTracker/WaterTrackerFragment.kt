package com.example.healthfitapplication.ui.waterTracker

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.healthfitapplication.databinding.ActivityWaterTrackerBinding
import java.time.LocalDate

class WaterTrackerFragment : Fragment() {

    private var _binding: ActivityWaterTrackerBinding? = null
    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = ActivityWaterTrackerBinding.inflate(inflater, container, false)
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val sharedPreferences = requireActivity().getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)

        // Retrieve waterCount as a string
        val waterCountStr = sharedPreferences.getString("WATERCOUNT", "0")
        // Convert waterCount from string to integer
        val waterCount = waterCountStr?.toIntOrNull() ?: 0
        binding.waterCountTextView.text = waterCount.toString()

        binding.waterButton.setOnClickListener {
            val incCount = binding.waterCountTextView.text.toString().toInt() + 1
            binding.waterCountTextView.text = incCount.toString()
            sharedPreferences.edit().putString("WATERCOUNT", incCount.toString()).apply()

            // Get this 8 from DB when they sign up
            if (incCount == (8 + 1)) {
                Toast.makeText(requireContext(), "Hurray!!! you achieved your goal", Toast.LENGTH_LONG).show()
            }
        }

        binding.resetButton.setOnClickListener {
            binding.waterCountTextView.text = "0"
            sharedPreferences.edit().putString("WATERCOUNT", "0").apply()
        }

        val date = LocalDate.now().toString()
        val lastLoggedTime = sharedPreferences.getString("LASTLOGGEDTIME", date)
        if (lastLoggedTime != date) {
            sharedPreferences.edit().putString("LASTLOGGEDTIME", date).apply()
            sharedPreferences.edit().putString("WATERCOUNT", "0").apply()
            binding.waterCountTextView.text = "0"
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
