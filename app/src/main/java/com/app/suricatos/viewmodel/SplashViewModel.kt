package com.app.suricatos.viewmodel

import androidx.lifecycle.ViewModel
import com.app.suricatos.repository.AuthRepository

class SplashViewModel : ViewModel() {
    val authRepository = AuthRepository()

    fun isLogged(): Boolean = authRepository.isLogged()
}