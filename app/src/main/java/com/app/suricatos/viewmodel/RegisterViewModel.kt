package com.app.suricatos.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.suricatos.model.User
import com.app.suricatos.repository.RegisterRepository
import com.app.suricatos.utils.Resource
import kotlinx.coroutines.launch

class RegisterViewModel : ViewModel() {
    val registerResponse: MutableLiveData<Resource<String>> = MutableLiveData()
    val registerRepository = RegisterRepository()

//    fun register(
//        user: User,
//    ) {
//        viewModelScope.launch {
//            try {
//                val data = registerRepository.register(user)
//                registerResponse.postValue(Resource.success(data))
//            } catch (e: Exception) {
//                registerResponse.postValue(Resource.error())
//            }
//        }
//    }
}