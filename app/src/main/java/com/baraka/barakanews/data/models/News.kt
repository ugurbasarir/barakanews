package com.baraka.barakanews.data.models

data class News(
    val status: String,
    val totalResults: Int,
    val articles: List<Article>
)
