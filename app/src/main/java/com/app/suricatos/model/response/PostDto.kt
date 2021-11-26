package com.app.suricatos.model.response

data class PostResponse(
    val comments: List<CommentDto>,
    val images: List<String>,
    val post: PostDto,
    val postReply: List<PostReplyDto>,
    val userImage: String,
)

data class CommentDto(
    val id: Int,
    val message: String,
    val user: UserDto,
    val createdAt: String,
    val updateAt: String
)

data class PostDto(
    val id: Int,
    val description: String,
    val like: Int,
    val slug: String,
    val status: String,
    val title: String,
    val type: String,
    val createdAt: String,
    val updateAt: String,
    val address: AddressDto,
    val user: UserDto
)

data class AddressDto(
    val id: Int,
    val street: String,
    val neighborhood: String,
    val zipCode: String,
    val complement: String,
    val state: String,
    val city: String,
    val createdAt: String,
    val updateAt: String,
)

data class PostReplyDto(
    val id: Int,
    val createdAt: String,
    val updateAt: String,
    val description: String,
    val externalLink: String,
    val externalProtocol: String,
    val user: UserDto
)