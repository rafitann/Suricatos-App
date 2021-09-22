package com.app.suricatos.model

import java.io.Serializable

data class Post(
    var id: String? = "",
    val title: String,
    val category: String,
    val description: String?,
    val author: Author,
    val favorited: Boolean? = false,
    val comments: Array<Comment>? = null
) : Serializable
