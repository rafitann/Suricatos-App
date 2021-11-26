package com.app.suricatos.model

data class Auth(
    val token: String,
    val userId: String?,
    val expiredAt: Long?

)