package com.app.suricatos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.suricatos.model.User
import com.app.suricatos.repository.AuthRepository
import com.app.suricatos.utils.Resource
import kotlinx.coroutines.launch

class SplashViewModel : ViewModel() {
    val authRepository = AuthRepository()

    fun isLogged(): Boolean = authRepository.isLogged()
}