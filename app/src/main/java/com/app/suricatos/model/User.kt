package com.app.suricatos.model

import java.io.Serializable

data class User(
    val id: Int,
    val name: String,
    val createdAt: String,
    val updateAt: String,
    val birthday: String,
    val biography: String,
    val type: String,
    val email: String
) : Serializable


data class Address(
    val state: String,
    val number: String,
    val city: String,
    val complement: String,
    val zipcode: String,
    val street: String,
    val neighborhood: String
) : Serializable