package com.app.suricatos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.suricatos.model.User
import com.app.suricatos.repository.AuthRepository
import com.app.suricatos.repository.UserRepository
import com.app.suricatos.utils.AuthenticationRequiredException
import com.app.suricatos.utils.Resource
import kotlinx.coroutines.launch

class SettingsViewModel : ViewModel() {
    val user: MutableLiveData<Resource<User>> = MutableLiveData()
    val logout: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    private val authRepository = AuthRepository()
    private val userRepository = UserRepository()

    fun getUser() {
        viewModelScope.launch {
            try {
                user.postValue(Resource.success(userRepository.getUser()))
            } catch (e: Exception) {
                user.postValue(Resource.error("Não autorizado", AuthenticationRequiredException()))
            }
        }
    }

    fun logout() {
        viewModelScope.launch {
            try {
                logout.postValue(Resource.success(authRepository.logout()))
            } catch (e: Exception) {
                logout.postValue(Resource.error("Não autorizado", AuthenticationRequiredException()))
            }
        }
    }
}