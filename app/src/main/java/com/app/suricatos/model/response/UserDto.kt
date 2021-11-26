package com.app.suricatos.model.response

import com.app.suricatos.model.request.Phone
import java.io.Serializable

data class UserDto(
    val id: Int,
    val name: String,
    val createdAt: String,
    val updateAt: String,
    val birthday: String,
    val biography: String,
    val type: String,
    val email: String,
    val phone: Phone,
    val token: String? = null

) : Serializable