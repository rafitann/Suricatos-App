package com.app.suricatos.repository

import com.app.suricatos.utils.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://suricatos-fiap.herokuapp.com"
private const val VIACEP = "https://viacep.com.br/ws/"

//--> /categories
//----> /feeds


open class Repository() {

    val retrofit: Retrofit by lazy {
        builder(BASE_URL)
    }

    val retrofitViaCEP: Retrofit by lazy {
        builder(VIACEP)
    }

    private fun builder(url: String):Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .client(httpClient())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    companion object {
        fun httpClient(): OkHttpClient {
            val client = OkHttpClient.Builder()

            val logging = HttpLoggingInterceptor()
            logging.setLevel(HttpLoggingInterceptor.Level.BODY)
            client.addInterceptor(logging)
            client.addInterceptor {
                val builder = it.request().newBuilder()
                if(Cache.token.isNotBlank())
                    builder.addHeader("Authorization", Cache.token)
                it.proceed(builder.build())
            }

            return client.build()
        }
    }
}