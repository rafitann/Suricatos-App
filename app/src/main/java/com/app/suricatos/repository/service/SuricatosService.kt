package com.app.suricatos.repository.service

import com.app.suricatos.model.Credencial
import com.app.suricatos.model.Post
import com.app.suricatos.model.User
import com.app.suricatos.model.response.FeedsDto
import com.app.suricatos.model.response.LoginDto
import com.app.suricatos.model.response.RegisterDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface SuricatosService {
    @POST("user/login")
    suspend fun login(@Body auth: Credencial): LoginDto

    @POST("user")
    suspend fun register(@Body user: User): RegisterDto

    @POST("feed")
    suspend fun createPost(@Body post: Post): FeedsDto

    @GET("feed")
    suspend fun getFeed(): List<FeedsDto>

}