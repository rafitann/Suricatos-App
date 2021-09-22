package com.app.suricatos.model.response

import java.io.Serializable

data class RegisterDto(
    val username: String,
    val token: String
):Serializable