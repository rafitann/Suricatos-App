package com.app.suricatos.model

import java.io.Serializable

data class Credencial(
    val name: String,
    val password: String
) : Serializable