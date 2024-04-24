package com.example.healthfitapplication.ui.workout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.healthfitapplication.WorkoutExplain
import com.example.healthfitapplication.databinding.ActivityWorkoutListBinding

class WorkoutListFragment : Fragment() {
    private lateinit var binding: ActivityWorkoutListBinding


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = ActivityWorkoutListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWorkoutClickListeners()
    }

    private fun setupWorkoutClickListeners() {
        val workoutViews = listOf(
            binding.pose1, binding.pose2, binding.pose3, binding.pose4, binding.pose5,
            binding.pose6, binding.pose7, binding.pose8, binding.pose9, binding.pose10,
            binding.pose11, binding.pose12, binding.pose13, binding.pose14, binding.pose15
        )

        workoutViews.forEachIndexed { index, workoutView ->
            workoutView.setOnClickListener { poseClicked(index + 1) }
        }
    }

    private fun poseClicked(value: Int) {
        Log.i("FIRST", value.toString())
        val intent = Intent(requireContext(), WorkoutExplain::class.java)
        intent.putExtra("workoutNo", value.toString())
        startActivity(intent)
    }
}

