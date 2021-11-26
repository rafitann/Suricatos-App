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

class ProfileViewModel : ViewModel() {
    val user: MutableLiveData<Resource<User>> = MutableLiveData()

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
}