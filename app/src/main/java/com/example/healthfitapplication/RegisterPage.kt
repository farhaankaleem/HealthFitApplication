package com.example.healthfitapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.healthfitapplication.databinding.ActivityRegisterPageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.database.FirebaseDatabase

class RegisterPage : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterPageBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var database: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()
        database = FirebaseDatabase.getInstance()

        binding.signIn.setOnClickListener {
            val intent = Intent(this, LoginPage::class.java)
            startActivity(intent)
            finish()
        }

        binding.signUp.setOnClickListener {
            var email: String = binding.email.text.toString()
            var password: String = binding.password.text.toString()
            var fName: String = binding.firstName.text.toString()
            var lName: String = binding.lastName.text.toString()
            var waterGlasses: String = binding.waterGlass.text.toString()
            var calories: String = binding.calories.text.toString()
            var steps: String = binding.steps.text.toString()

            if (TextUtils.isEmpty(fName)) {
                Toast.makeText(this, "Enter First Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(lName)) {
                Toast.makeText(this, "Enter Last Name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password.length < 6) {
                Toast.makeText(this, "Password should be at least 6 characters", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(waterGlasses)) {
                Toast.makeText(this, "Enter Glasses of Water you want to Drink Each Day", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(calories)) {
                Toast.makeText(this, "Enter Calories you want to Burn Each Day", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(steps)) {
                Toast.makeText(this, "Enter Steps you want to Walk Each Day", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val name = "$fName $lName"

            binding.progressBar.visibility = View.VISIBLE

            firebaseAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                binding.progressBar.visibility = View.INVISIBLE
                if (it.isSuccessful) {
                    val user = firebaseAuth.currentUser
                    val userId = user?.uid ?: ""
                    val usersRef = database.reference.child("users").child(userId)

                    val userData = HashMap<String, Any>()
                    userData["firstName"] = fName
                    userData["lastName"] = lName
                    userData["email"] = email
                    userData["waterGlassesGoal"] = waterGlasses.toInt()
                    userData["caloriesGoal"] = calories.toInt()
                    userData["stepsGoal"] = steps.toInt()

                    usersRef.setValue(userData).addOnCompleteListener { databaseTask ->
                        if (databaseTask.isSuccessful) {
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()

                            user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Log.d("Registration", "User profile updated.")
                                        Toast.makeText(this, "Registration Successful", Toast.LENGTH_SHORT).show()
                                        val intent = Intent(this, LoginPage::class.java)
                                        startActivity(intent)
                                        finish()
                                    } else {
                                        Log.e("Registration", "Failed to update user profile.")
                                        Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            Log.e("Registration", "Failed to store user data in database.")
                            Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Registration Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}