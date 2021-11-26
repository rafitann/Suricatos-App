package com.app.suricatos.repository

import com.app.suricatos.repository.service.SuricatosService

class PostRepository : Repository() {
    val service = retrofit.create(SuricatosService::class.java)

//    suspend fun getPosts(): List<Post> {
//        val feedsDto = service.getFeed()
//        return feedsDto.map { it.toPost() }
//    }

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