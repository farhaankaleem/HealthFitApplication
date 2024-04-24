package com.example.healthfitapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import com.example.healthfitapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var up: Animation
    private lateinit var down: Animation

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        binding.fitnesstracker.apply {
            up = AnimationUtils.loadAnimation(context, R.anim.up)
            animation = up
        }

        binding.title.apply {
            down = AnimationUtils.loadAnimation(context, R.anim.down)
            animation = down
        }

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, HomepageActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}