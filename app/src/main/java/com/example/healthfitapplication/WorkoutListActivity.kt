package com.example.healthfitapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.widget.Toolbar

class WorkoutListActivity : AppCompatActivity() {
    private lateinit var workouts: IntArray;
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout_list)

        workouts = intArrayOf(R.id.pose1, R.id.pose2, R.id.pose3, R.id.pose4, R.id.pose5,
            R.id.pose6, R.id.pose7, R.id.pose8, R.id.pose9, R.id.pose10, R.id.pose11,
            R.id.pose12, R.id.pose13, R.id.pose14, R.id.pose15);
    }

    fun poseClicked(view: View) {
        workouts.forEachIndexed { index, element ->
            if (view.id == element) {
                val value = index + 1;
                Log.i("FIRST", value.toString());
                val i = Intent(this, WorkoutExplain::class.java)
                i.putExtra("workoutNo", value.toString())
                startActivity(i)
            }
        }
    }
}