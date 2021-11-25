package com.app.suricatos.repository

import com.app.suricatos.model.Credencial
import com.app.suricatos.repository.service.SuricatosService
import com.app.suricatos.utils.Cache

class LoginRepository: Repository() {

    val service = retrofit.create(SuricatosService::class.java)

    suspend fun login(username:String, password:String): Pair<String, String>{
        val serviceLogin = service.login(Credencial(username,password))
        Cache.token = serviceLogin.token
        Cache.userName = serviceLogin.name

        return serviceLogin.token to serviceLogin.name
    }
}
