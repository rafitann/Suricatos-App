package com.app.suricatos.model

import java.io.Serializable
import java.util.*

data class User(
    val id: Int,
    val name: String,
    val birthday: Date,
    val email: String,
    val biography: String,
    val type: String,
    val phone: Phone?,
    val image: String?,
    val createdAt: Date,
    val updateAt: Date,
) : Serializable

data class Phone(
    val id: Int,
    val ddd: String,
    val number: String,
    val type: String,
    val createdAt: Date,
    val updateAt: Date,
): Serializable