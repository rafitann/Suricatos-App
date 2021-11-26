package com.app.suricatos.model.response

import java.io.Serializable

data class UserResponse(
    val user: UserDto,
    val phone: PhoneDto,
    val image: String
)


data class UserDto(
    val id: Int,
    val name: String,
    val createdAt: String,
    val updateAt: String,
    val birthday: String,
    val biography: String,
    val type: String,
    val email: String
) : Serializable


data class PhoneDto(
    val id: Int,
    val ddd: String,
    val number: String,
    val type: String,
    val createdAt: String,
    val updateAt: String
)