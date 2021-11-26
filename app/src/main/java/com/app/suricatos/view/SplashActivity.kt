package com.app.suricatos.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.app.suricatos.R
import com.app.suricatos.databinding.ActivitySplashBinding
import com.app.suricatos.viewmodel.SplashViewModel

class SplashActivity: AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding
    private val viewModel: SplashViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)

        Handler(Looper.getMainLooper()).postDelayed({
            verifyStatusApp()
        }, 2000)

    }

    private fun verifyStatusApp() {
        if(viewModel.isLogged())
            navigateToHome()
        else
            navigateToLogin()
    }

    private fun navigateToLogin() {
        val intent = Intent(this, BaseActivity::class.java)
        intent.putExtra("NAVIGATION", R.id.action_homeFragment_to_loginFragment)
        startActivity(intent)
        finish()
    }

    private fun navigateToHome() {
        startActivity(Intent(this, BaseActivity::class.java))
        finish()
    }
}