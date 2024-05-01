package com.example.healthfitapplication

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.ImageButton
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.healthfitapplication.databinding.ActivityHomepageBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuth.AuthStateListener

class HomepageActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityHomepageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomepageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.appBarHomepage.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val menuButton: ImageButton = binding.appBarHomepage.buttonDrawerToggle
        val nightSwitch: Switch = binding.appBarHomepage.darkModeSwitch

        menuButton.setOnClickListener{
            drawerLayout.open()
        }

        nightSwitch.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }


        val navView: NavigationView = binding.navView
        val headerView = navView.getHeaderView(0)
        val userNameTextView: TextView = headerView.findViewById(R.id.userName)
        val userEmailTextView: TextView = headerView.findViewById(R.id.userEmail)

        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseUser = firebaseAuth.currentUser

        if (firebaseUser != null) {
            val userEmail = firebaseUser.email
            val userName = firebaseUser.displayName

            userEmailTextView.text = userEmail
            userNameTextView.text = userName

            Log.d("User Info", "Email: $userEmail")
            Log.d("User Info", "Name: $userName")
        } else {
            Log.e("User Info", "User is not logged in")
        }

        val navController = findNavController(R.id.nav_host_fragment_content_homepage)
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_home, R.id.nav_run, R.id.nav_water, R.id.nav_BMI, R.id.nav_workout
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.logout -> {
                logout(item)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun logout(menuItem: MenuItem) {
        val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
        val authStateListener = AuthStateListener { firebaseAuth ->
            if (firebaseAuth.currentUser == null) {
                Toast.makeText(this, "User logged out", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, LoginPage::class.java)
                startActivity(intent)
                finish()
            }
        }
        firebaseAuth.addAuthStateListener(authStateListener)
        firebaseAuth.signOut()
        firebaseAuth.removeAuthStateListener(authStateListener)
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.homepage, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_homepage)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }
}