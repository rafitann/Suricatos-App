package com.app.suricatos.repository

import com.app.suricatos.repository.service.SuricatosService

class RegisterRepository : Repository() {
    val service = retrofit.create(SuricatosService::class.java)

//    suspend fun register(user: User): String {
//        val serviceRegister = service.register(user)
//        Cache.token = serviceRegister.token
//
//        return serviceRegister.token
//    }
}