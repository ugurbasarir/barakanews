package com.baraka.barakanews.ui.feed.adapter.viewholder

import androidx.recyclerview.widget.RecyclerView
import com.baraka.barakanews.data.models.ArticleList
import com.baraka.barakanews.databinding.ItemArticleListBinding
import com.baraka.barakanews.ui.feed.adapter.FeedAdapter
import com.baraka.barakanews.ui.feed.adapter.FeedAdapterListener

class ArticleListViewHolder(
    private val itemBinding: ItemArticleListBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(data: ArticleList, listener: FeedAdapterListener?) {
        itemBinding.recyclerView.adapter = FeedAdapter(data.list, listener)
    }
}


