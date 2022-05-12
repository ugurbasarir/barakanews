package com.baraka.barakanews.data.repositories

import com.baraka.barakanews.data.models.News
import com.baraka.barakanews.data.remote.Api
import com.baraka.barakanews.data.remote.ApiService
import retrofit2.Response
import javax.inject.Inject

class NewsRepository @Inject constructor(private val api: Api) : ApiService {

    private fun getApi(): ApiService = api.get(ApiService::class.java)

    override suspend fun getNews(): Response<News> = getApi().getNews()

}