package com.app.suricatos.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.app.suricatos.R
import com.app.suricatos.databinding.ActivitySplashBinding
import com.app.suricatos.utils.Cache

class SplashActivity: Activity() {

    private lateinit var binding: ActivitySplashBinding

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
        if(isLogged(baseContext))
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

    private fun isLogged(context: Context): Boolean {
        val sharedPref = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", "")


        val contains = !token.isNullOrEmpty()

        if(contains) {
            Cache.token = token!!
            Cache.userName = sharedPref.getString("username", "")!!
        }

        return contains
    }
}