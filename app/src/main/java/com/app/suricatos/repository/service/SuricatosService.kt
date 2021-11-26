package com.app.suricatos.repository.service

import com.app.suricatos.model.Credencial
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface SuricatosService {
    @POST("login")
    suspend fun login(@Body auth: Credencial): Response<ResponseBody>

//    @POST("user")
//    suspend fun register(@Body user: User): RegisterDto
//
//    @POST("feed")
//    suspend fun createPost(@Body post: Post): FeedsDto
//
//    @GET("feed")
//    suspend fun getFeed(): List<FeedsDto>

}