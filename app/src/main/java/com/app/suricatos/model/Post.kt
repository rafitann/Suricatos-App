package com.app.suricatos.model

import java.io.Serializable

data class Post(
    var id: String,
    var createdAt: String,
    var updateAt: String,
    var slug: String? = "slug",
    val title: String,
    val description: String,
    val user: User,
    val address: Address,
    val type: String,
    val status: String,
    val like: Int,
    val postReply: Array<PostReply>? = null,
    val comments: Array<Comment>? = null
) : Serializable

data class PostReply(
    var id: String,
    var createdAt: String,
    var updateAt: String,
    val description: String,
    val externalLink: String,
    val externalProtocol:String,
    val user: User,
) : Serializable