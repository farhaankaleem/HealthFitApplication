package com.example.healthfitapplication.ui.workout

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.SearchView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.healthfitapplication.R
import com.example.healthfitapplication.WorkoutExplain

class WorkoutListFragment : Fragment() {
    private lateinit var searchBar: SearchView
    private lateinit var workoutListContainer: LinearLayout
    data class Workout(val name: String, val imageResId: Int)
    private val allWorkouts = mutableListOf<Workout>()
    private var filteredWorkouts = mutableListOf<Workout>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.activity_workout_list, container, false)
        searchBar = view.findViewById(R.id.searchBar)
        workoutListContainer = view.findViewById(R.id.workoutListContainer)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupWorkoutList()
        setupSearchBar()
    }

    private fun setupWorkoutList() {
        allWorkouts.apply {
            add(Workout("Mountain Climber", R.drawable.exersice_1))
            add(Workout("Basic Crunches", R.drawable.exersice_2))
            add(Workout("Bench Dips", R.drawable.exersice_3))
            add(Workout("Bicycle Crunches", R.drawable.exersice_4))
            add(Workout("Leg Raise", R.drawable.exersice_5))
            add(Workout("Alternative Heel Touch", R.drawable.exersice_6))
            add(Workout("Leg up Crunches", R.drawable.exersice_7))
            add(Workout("Sit Up", R.drawable.exersice_8))
            add(Workout("Alternative V UPS", R.drawable.exersice_9))
            add(Workout("Planks", R.drawable.exersice_10))
            add(Workout("Planks with Leg Lift", R.drawable.exersice_11))
            add(Workout("Russian Twist", R.drawable.exersice_12))
            add(Workout("Bridge", R.drawable.exersice_13))
            add(Workout("Vertical Leg Crunches", R.drawable.exersice_14))
            add(Workout("Wind Mill", R.drawable.exersice_15))

        }

        filteredWorkouts.addAll(allWorkouts)
        updateWorkoutList()
    }

    private fun setupSearchBar() {
        searchBar.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                filterWorkouts(newText.orEmpty())
                return true
            }
        })
    }

    private fun filterWorkouts(query: String) {
        filteredWorkouts.clear()
        if (query.isEmpty()) {
            filteredWorkouts.addAll(allWorkouts)
        } else {
            allWorkouts.forEach { workout ->
                if (workout.name.contains(query, ignoreCase = true)) {
                    filteredWorkouts.add(workout)
                }
            }
        }
        updateWorkoutList()
    }

    private fun updateWorkoutList() {
        workoutListContainer.removeAllViews()

        filteredWorkouts.forEach { workout ->
            val workoutView = layoutInflater.inflate(R.layout.workout_item, workoutListContainer, false)
            val workoutNameTextView = workoutView.findViewById<TextView>(R.id.textViewWorkoutName)
            workoutNameTextView.text = workout.name

            val workoutNameImageView = workoutView.findViewById<pl.droidsonroids.gif.GifImageView>(R.id.imageViewWorkoutImage)
            val resourceId = resources.getIdentifier(workout.imageResId.toString(), "drawable", requireActivity().packageName)
            workoutNameImageView.setImageResource(resourceId)
            workoutView.setOnClickListener {
                Log.d("WorkoutListFragment", "Clicked on ${workout.name}")
                val resourceName = resources.getResourceName(workout.imageResId)
                val number = resourceName.substringAfterLast("_")
                val intent = Intent(requireContext(), WorkoutExplain::class.java)
                intent.putExtra("workoutNo", number)
                startActivity(intent)
            }

            workoutListContainer.addView(workoutView)
        }
    }
}
