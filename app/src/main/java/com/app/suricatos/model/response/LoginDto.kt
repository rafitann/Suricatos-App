package com.app.suricatos.model.response

import java.io.Serializable

data class LoginDto (
    val token:String,
    val name:String,
    val username:String
):Serializable

