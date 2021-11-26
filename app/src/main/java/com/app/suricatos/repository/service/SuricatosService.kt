package com.app.suricatos.repository.service

import com.app.suricatos.model.Credencial
import okhttp3.ResponseBody
import retrofit2.Response
import com.app.suricatos.model.request.RegisterUser
import com.app.suricatos.model.response.LoginDto
import com.app.suricatos.model.response.RegisterDto
import com.app.suricatos.model.response.UserDto
import retrofit2.http.Body
import retrofit2.http.POST

interface SuricatosService {
    @POST("login")
    suspend fun login(@Body auth: Credencial): Response<ResponseBody>

    @POST("user")
    suspend fun register(@Body user: RegisterUser): UserDto
//
//    @POST("feed")
//    suspend fun createPost(@Body post: Post): FeedsDto
//
//    @GET("feed")
//    suspend fun getFeed(): List<FeedsDto>

}