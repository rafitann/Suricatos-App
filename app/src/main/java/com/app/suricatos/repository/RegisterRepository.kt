package com.app.suricatos.repository

import com.app.suricatos.model.User
import com.app.suricatos.repository.service.SuricatosService
import com.app.suricatos.utils.Cache

class RegisterRepository : Repository() {
    val service = retrofit.create(SuricatosService::class.java)

    suspend fun register(user: User): String {
        val serviceRegister = service.register(user)
        Cache.token = serviceRegister.token

        return serviceRegister.token
    }
}