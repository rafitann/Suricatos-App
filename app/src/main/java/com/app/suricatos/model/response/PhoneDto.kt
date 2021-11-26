package com.app.suricatos.model.response

import java.io.Serializable

data class PhoneDto(
    val id: Int,
    val ddd: Int,
    val number: Int,
    val createdAt: String,
    val updateAt: String,
    val type: String,
) : Serializable