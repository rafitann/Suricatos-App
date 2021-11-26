package com.app.suricatos.repository

import com.app.suricatos.model.request.RegisterUser
import com.app.suricatos.model.response.UserDto
import com.app.suricatos.repository.service.SuricatosService

class RegisterRepository : Repository() {
    val service = retrofit.create(SuricatosService::class.java)
    
    suspend fun register(user: RegisterUser): UserDto {
        return service.register(user)
    }
}