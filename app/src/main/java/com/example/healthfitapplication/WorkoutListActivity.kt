package com.example.healthfitapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.healthfitapplication.databinding.ActivityWorkoutListBinding

class WorkoutListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWorkoutListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWorkoutListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupWorkoutClickListeners()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val i = Intent(this, HomeActivity::class.java)
        startActivity(i)
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
        val intent = Intent(this, WorkoutExplain::class.java)
        intent.putExtra("workoutNo", value.toString())
        startActivity(intent)
    }
}
