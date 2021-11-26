package com.app.suricatos.model

import java.io.Serializable
import java.util.*

data class Post(
    val id: Int,
    val description: String,
    val like: Int,
    val slug: String,
    val status: String,
    val title: String,
    val type: String,
    val createdAt: Date,
    val updateAt: Date,
    val address: Address,
    val user: User,
    val comments: List<Comment>,
    val images: List<String>,
    val postReply: List<PostReply>,
    val userImage: String?,
): Serializable

data class Comment(
    val id: Int,
    val message: String,
    val user: User,
    val createdAt: Date,
    val updateAt: Date
): Serializable

data class PostReply(
    val id: Int,
    val description: String,
    val externalLink: String,
    val externalProtocol: String,
    val user: User
): Serializable

data class Address(
    val id: Int,
    val street: String,
    val neighborhood: String,
    val zipCode: String,
    val complement: String,
    val state: String?,
    val city: String,
    val createdAt: Date,
    val updateAt: Date,
): Serializable
