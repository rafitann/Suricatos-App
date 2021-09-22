package com.app.suricatos.model

import java.io.Serializable

data class User(
    val name: String,
    val email: String,
    val address: Address?,
    var password: String
) : Serializable


data class Address(
    val street: String,
    val district: String,
    val zipcode: String,
    val city: String,
    val country: String
):Serializable