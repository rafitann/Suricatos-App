package com.app.suricatos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.suricatos.repository.AuthRepository
import com.app.suricatos.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val loginResponse: MutableLiveData<Resource<Boolean>> = MutableLiveData()

    private val loginRepository = AuthRepository()

    fun postLogin(
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                loginRepository.login(username, password)
                loginResponse.postValue(Resource.success(true))
            } catch (e: Exception) {
                loginResponse.postValue(Resource.error())
            }
        }
    }

}