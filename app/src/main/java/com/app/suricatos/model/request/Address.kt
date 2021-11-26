package com.app.suricatos.model.request

import java.io.Serializable


data class Address(
    val state: String,
    val number: String,
    val city: String,
    val complement: String,
    val zipcode: String,
    val street: String,
    val neighborhood: String
) : Serializable