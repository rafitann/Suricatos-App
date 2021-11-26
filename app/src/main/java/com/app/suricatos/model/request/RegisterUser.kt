package com.app.suricatos.model.request

import java.io.Serializable

data class RegisterUser(
    val name: String,
    val birthday: String,
    val type: String,
    val biography: String,
    val phone: Phone,
    val email: String,
    val password: String,
) : Serializable