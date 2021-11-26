package com.app.suricatos.repository.service

import com.app.suricatos.model.Credencial
import com.app.suricatos.model.response.PostResponse
import com.app.suricatos.model.response.UserResponse
import okhttp3.ResponseBody
import retrofit2.Response
import com.app.suricatos.model.request.RegisterUser
import com.app.suricatos.model.response.RegisterDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface SuricatosService {
    @POST("/login")
    suspend fun login(@Body auth: Credencial): Response<ResponseBody>

    @POST("/logout")
    suspend fun logout(): Response<ResponseBody>

    @GET("user/logged")
    suspend fun getUser(): UserResponse

    @GET("post")
    suspend fun getPosts(): List<PostResponse>

    @POST("/user")
    suspend fun register(@Body user: RegisterUser): UserResponse
//
//    @POST("feed")
//    suspend fun createPost(@Body post: Post): FeedsDto
//
//    @GET("feed")
//    suspend fun getFeed(): List<FeedsDto>

}