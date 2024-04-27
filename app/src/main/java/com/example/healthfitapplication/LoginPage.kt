package com.example.healthfitapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.example.healthfitapplication.databinding.ActivityLoginPageBinding
import com.google.firebase.auth.FirebaseAuth

class LoginPage : AppCompatActivity() {
    private lateinit var binding: ActivityLoginPageBinding
    private lateinit var firebaseAuth:FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signUp.setOnClickListener {
            val intent = Intent(this, RegisterPage::class.java)
            startActivity(intent)
            finish()
        }

        binding.signIn.setOnClickListener {
            var email: String = binding.email.text.toString()
            var password: String = binding.password.editText?.text.toString()

            if (TextUtils.isEmpty(email)) {
                Toast.makeText(this, "Enter Email", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (TextUtils.isEmpty(password)) {
                Toast.makeText(this, "Enter Password", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            binding.progressBar.visibility = View.VISIBLE

            firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                binding.progressBar.visibility = View.INVISIBLE
                if (it.isSuccessful) {
                    Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, HomepageActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Authentication Failed", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}