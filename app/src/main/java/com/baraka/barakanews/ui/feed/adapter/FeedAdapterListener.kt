package com.baraka.barakanews.ui.feed.adapter

import com.baraka.barakanews.data.models.Article

interface FeedAdapterListener {
    fun onClickArticle(article: Article)
}