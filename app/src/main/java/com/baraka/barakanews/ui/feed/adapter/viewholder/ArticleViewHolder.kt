package com.baraka.barakanews.ui.feed.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.baraka.barakanews.App
import com.baraka.barakanews.data.models.Article
import com.baraka.barakanews.databinding.ItemArticleBinding
import com.baraka.barakanews.extensions.getUserFriendlyTime
import com.baraka.barakanews.extensions.loadImageFromUrl
import com.baraka.barakanews.ui.feed.adapter.FeedAdapterListener

class ArticleViewHolder(
    private val itemBinding: ItemArticleBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(data: Article, listener: FeedAdapterListener?) {

        // Logo
        itemBinding.logoView.text = data.source.name

        // Title
        itemBinding.titleView.text = data.title

        // Image
        itemBinding.imageView.loadImageFromUrl(data.urlToImage)
        val layoutParams = itemBinding.imageView.layoutParams
        layoutParams.width = App.screenWidth
        layoutParams.height = App.screenWidth * 9 / 16

        // Description
        itemBinding.descriptionView.text = data.description

        // Date
        itemBinding.dateView.text = data.publishedAt.getUserFriendlyTime()

        // Click Event
        itemBinding.imageView.setOnClickListener {
            listener?.onClickArticle(data)
        }
    }
}

