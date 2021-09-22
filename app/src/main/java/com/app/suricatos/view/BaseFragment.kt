package com.app.suricatos.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupBottomNavigation()
    }


    private fun setupBottomNavigation() {
        if (activity !is BaseActivity) return

       /* try {
            when (this) {
                is SplashActivity -> (activity as BaseActivity).hideBottonNavigation()
                else -> (activity as BaseActivity).showBottonNavigation()
            }
        } catch (e: Exception) {}*/

    }

}