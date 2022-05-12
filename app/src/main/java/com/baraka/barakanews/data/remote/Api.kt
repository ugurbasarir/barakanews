package com.baraka.barakanews.data.remote

import com.baraka.barakanews.API_CONNECT_TIMEOUT
import com.baraka.barakanews.API_READ_TIMEOUT
import com.baraka.barakanews.API_WRITE_TIMEOUT
import com.baraka.barakanews.BASE_URL
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class Api @Inject constructor(){
    private val engine: Retrofit by lazy {
        val client = OkHttpClient.Builder()
            .readTimeout(API_READ_TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(API_CONNECT_TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(API_WRITE_TIMEOUT, TimeUnit.SECONDS)

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(
                GsonConverterFactory.create(
                    GsonBuilder().setPrettyPrinting().create()
                )
            )
            .client(client.build())
            .build()
    }

    fun <T> get(serviceClass: Class<T>): T {
        return engine.create(serviceClass)
    }
}