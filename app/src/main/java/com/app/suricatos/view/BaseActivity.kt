package com.app.suricatos.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.app.suricatos.R
import com.app.suricatos.databinding.ActivityBaseBinding
import com.google.android.material.bottomnavigation.BottomNavigationView

open class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navController = findNavController(R.id.nav_host_fragment)
        val menu = binding.bottomNavigation

        menu.setupWithNavController(navController)
    }


    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        val extras = intent.extras
        if(extras?.containsKey("NAVIGATION") == true) {
            val navController = Navigation.findNavController(this, R.id.nav_host_fragment)
            navController.navigate(extras.getInt("NAVIGATION"))
        }
    }


    fun showBottonNavigation() {
        val menu = binding.bottomNavigation
        menu.isVisible = true
    }

    fun hideBottonNavigation() {
        val menu = binding.bottomNavigation
        menu.isVisible = false
    }

}
