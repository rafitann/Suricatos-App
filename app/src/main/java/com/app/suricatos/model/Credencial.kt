package com.app.suricatos.model

import java.io.Serializable

data class Credencial(
    val username: String,
    val password: String
) : Serializable