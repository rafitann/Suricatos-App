package com.app.suricatos.repository

import com.app.suricatos.model.Auth
import com.app.suricatos.model.Credencial
import com.app.suricatos.repository.service.SuricatosService
import com.app.suricatos.utils.NoAuthorizationException
import com.auth0.android.jwt.JWT
import io.paperdb.Paper
import java.lang.IllegalStateException
import java.util.*


class AuthRepository : Repository() {

    val service = retrofit.create(SuricatosService::class.java)
    val localCache = Paper.book("session")

    suspend fun login(username: String, password: String): Auth {
        val serviceLogin = service.login(Credencial(username, password))
        if (serviceLogin.isSuccessful) {
            val jwt = serviceLogin.body()?.string()?.let { JWT(it) }
            jwt ?: throw NoAuthorizationException()

            val auth = jwt.toAuth()
            localCache.let {
                it.write(Auth::class.java.simpleName, auth)
                it.write("logged", true)
            }

            return auth
        }

        throw NoAuthorizationException()
    }

    suspend fun logout(): Boolean {
        service.logout()
        localCache.destroy()
        return true
    }

    fun isLogged(): Boolean {
        return isValidAccessToken(localCache.read(Auth::class.java.simpleName))
                && localCache.read<Boolean>("logged") ?: false
    }

    private fun isValidAccessToken(auth: Auth?): Boolean {
        return Date().time < auth?.expiredAt ?: 0
    }

    private fun JWT.toAuth(): Auth {
        return Auth(
            toString(),
            subject,
            expiresAt?.time
        )
    }

}
