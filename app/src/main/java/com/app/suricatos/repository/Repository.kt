package com.app.suricatos.repository

import com.app.suricatos.BuildConfig
import com.app.suricatos.model.Auth
import io.paperdb.Paper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "https://suricatos-fiap.herokuapp.com/api/"
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

            if (BuildConfig.DEBUG) {
                val logging = HttpLoggingInterceptor()
                logging.setLevel(HttpLoggingInterceptor.Level.BODY)
                client.addInterceptor(logging)
            }

            client.addInterceptor {
                val builder = it.request().newBuilder()

                val localCache = Paper.book("session")
                if (localCache.read("logged", false)) {
                    val auth = localCache.read<Auth>(Auth::class.java.simpleName)
                    builder.addHeader("Authorization", "Bearer ${auth.token}")
                }

                it.proceed(builder.build())
            }

            return client.build()
        }
    }
}