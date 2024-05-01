package com.example.healthfitapplication.ui.home

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.healthfitapplication.databinding.FragmentHomeBinding
import com.google.firebase.database.*

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var homeViewModel: HomeViewModel

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)


        homeViewModel.userName.observe(viewLifecycleOwner) { userName ->
            binding.userName.text = userName
        }

        homeViewModel.BMI.observe(viewLifecycleOwner) { BMI ->
            binding.BMIValue.text = BMI.toString()
        }

        homeViewModel.healthCondition.observe(viewLifecycleOwner) { healthCondition ->
            binding.health.text = healthCondition
        }

        homeViewModel.caloriesGoal.observe(viewLifecycleOwner) {
            homeViewModel.caloriesGoal.value?.let { goal ->
                homeViewModel.caloriesBurnt.value?.let { burnt ->
                    homeViewModel.remainingCalories.value = goal - burnt
                    homeViewModel.remainingCaloriesPercent.value = (burnt / goal) * 100
                }
            }
        }

        homeViewModel.caloriesBurnt.observe(viewLifecycleOwner) {
            homeViewModel.caloriesGoal.value?.let { goal ->
                homeViewModel.caloriesBurnt.value?.let { burnt ->
                    homeViewModel.remainingCalories.value = goal - burnt
                    homeViewModel.remainingCaloriesPercent.value = (burnt / goal) * 100
                }
            }
        }

        homeViewModel.stepsTaken.observe(viewLifecycleOwner) {
            homeViewModel.stepsGoal.value?.let { goal ->
                homeViewModel.stepsTaken.value?.let { steps ->
                    homeViewModel.remainingSteps.value = goal - steps
                    homeViewModel.remainingStepsPercent.value = (steps / goal) * 100
                }
            }
        }

        homeViewModel.stepsGoal.observe(viewLifecycleOwner) {
            homeViewModel.stepsGoal.value?.let { goal ->
                homeViewModel.stepsTaken.value?.let { steps ->
                    homeViewModel.remainingSteps.value = goal - steps
                    homeViewModel.remainingStepsPercent.value = (steps / goal) * 100
                }
            }
        }

        homeViewModel.waterDrank.observe(viewLifecycleOwner) {
            homeViewModel.waterGoal.value?.let { goal ->
                homeViewModel.waterDrank.value?.let { water ->
                    homeViewModel.remainingWater.value = goal - water
                    homeViewModel.remainingWaterPercent.value = (water / goal) * 100
                }
            }
        }

        homeViewModel.waterGoal.observe(viewLifecycleOwner) {
            homeViewModel.waterGoal.value?.let { goal ->
                homeViewModel.waterDrank.value?.let { water ->
                    homeViewModel.remainingWater.value = goal - water
                    homeViewModel.remainingWaterPercent.value = (water / goal) * 100
                }
            }
        }

        homeViewModel.remainingCalories.observe(viewLifecycleOwner) { remainingCalories ->
            val remainingCaloriesFormatted: String = String.format("%.2f", remainingCalories)
            binding.caloriesRemain.text = "$remainingCaloriesFormatted"
        }

        homeViewModel.remainingCaloriesPercent.observe(viewLifecycleOwner) { percent ->
            binding.circularProgressBarCalories.setProgressWithAnimation(percent, 1000)
        }

        homeViewModel.remainingSteps.observe(viewLifecycleOwner) { remainingSteps ->
            val remainingStepsFormatted: String = String.format("%.2f", remainingSteps)
            binding.stepRemain.text = "$remainingStepsFormatted"
        }

        homeViewModel.remainingStepsPercent.observe(viewLifecycleOwner) { percent ->
            binding.circularProgressBarSteps.setProgressWithAnimation(percent, 1000)
        }

        homeViewModel.remainingWater.observe(viewLifecycleOwner) { remainingWater ->
            val formattedRemainingWater: String = String.format("%.2f", remainingWater)
            binding.waterRemain.text = "${formattedRemainingWater.toDouble().toInt()}"
        }

        homeViewModel.remainingWaterPercent.observe(viewLifecycleOwner) { percent ->
            binding.circularProgressBarWater.setProgressWithAnimation(percent, 1000)
        }

        homeViewModel.fetchDataAndUpdate()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
