package com.app.suricatos.repository

import com.app.suricatos.model.*
import com.app.suricatos.model.response.*
import com.app.suricatos.repository.service.SuricatosService
import com.app.suricatos.utils.extension.toDate
import java.util.*

class PostRepository : Repository() {
    val service = retrofit.create(SuricatosService::class.java)

    suspend fun getPosts(): List<Post> {
        return service.getPosts().map { it.toPost() }
    }


    ///////// Conversao

    private fun PostResponse.toPost(): Post {
        return Post(
            post.id,
            post.description,
            post.like,
            post.slug,
            post.status,
            post.title,
            post.type,
            post.createdAt.toDate(),
            post.updateAt.toDate(),
            post.address.toAddress(),
            post.user.toUser(),
            comments.map { it.toComment() },
            images,
            postReply.map { it.toPostReply() },
            userImage
        )
    }

    private fun UserDto.toUser(): User {
        return User(
            id,
            name,
            birthday.toDate(),
            email,
            biography,
            type,
            null,
            null,
            createdAt.toDate(),
            updateAt.toDate()
        )
    }

    private fun CommentDto.toComment(): Comment {
        return Comment(
            id,
            message,
            user.toUser(),
            createdAt.toDate(),
            updateAt.toDate()
        )
    }

    private fun AddressDto.toAddress(): Address {
        return Address(
            id,
            street,
            neighborhood,
            zipCode,
            complement,
            state,
            city,
            createdAt.toDate(),
            updateAt.toDate()
        )
    }

    private fun PostReplyDto.toPostReply(): PostReply {
        return PostReply(
            id.toString(),
            createdAt,
            updateAt,
            description,
            externalLink,
            externalProtocol,
            user.toUser()
        )
    }

//    suspend fun createPost(post: Post): Post {
//        return service.createPost(post).toPost()
//    }


//    private fun FeedsDto.toPost(): Post {
//        return Post(
//            id,
//            title,
//            category,
//            description,
//            Author(author.name),
//            favorited,
//            comments
//        )
//    }
}