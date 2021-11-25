package com.app.suricatos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.suricatos.repository.LoginRepository
import com.app.suricatos.utils.Resource
import kotlinx.coroutines.launch

class LoginViewModel : ViewModel() {
    val loginResponse: MutableLiveData<Resource<Pair<String, String>>> = MutableLiveData()
    val loginRepository = LoginRepository()

    fun postLogin(
        username: String,
        password: String
    ) {
        viewModelScope.launch {
            try {
                val data = loginRepository.login(username, password)
                loginResponse.postValue(Resource.success(data))
            } catch (e: Exception) {
                loginResponse.postValue(Resource.error())
            }
        }
    }

}