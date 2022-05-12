package com.baraka.barakanews.data.remote

import com.baraka.barakanews.data.models.News
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("NewsAPI/everything/cnn.json")
    suspend fun getNews(): Response<News>

}
