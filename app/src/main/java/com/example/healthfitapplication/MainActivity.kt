package com.example.healthfitapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private lateinit var up: Animation
    private lateinit var down: Animation
    private lateinit var imgView: ImageView
    private lateinit var textView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()
        imgView = findViewById(R.id.fitnesstracker)
        up = AnimationUtils.loadAnimation(this, R.anim.up)
        imgView.setAnimation(up)


        textView = findViewById(R.id.title)
        down = AnimationUtils.loadAnimation(this, R.anim.down)
        textView.setAnimation(down)

        Handler(Looper.getMainLooper()).postDelayed(
            {
                val i = Intent(this, HomeActivity::class.java)
                startActivity(i)
            } , 3000)
    }
}