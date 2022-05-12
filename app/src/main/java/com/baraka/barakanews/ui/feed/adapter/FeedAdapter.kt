package com.baraka.barakanews.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baraka.barakanews.data.models.Article
import com.baraka.barakanews.data.models.ArticleList
import com.baraka.barakanews.data.models.Feed
import com.baraka.barakanews.data.models.StockList
import com.baraka.barakanews.databinding.ItemArticleBinding
import com.baraka.barakanews.databinding.ItemArticleListBinding
import com.baraka.barakanews.databinding.ItemEmptyBinding
import com.baraka.barakanews.databinding.ItemStockListBinding
import com.baraka.barakanews.ui.feed.FeedViewModel
import com.baraka.barakanews.ui.feed.adapter.viewholder.ArticleListViewHolder
import com.baraka.barakanews.ui.feed.adapter.viewholder.ArticleViewHolder
import com.baraka.barakanews.ui.feed.adapter.viewholder.EmptyViewHolder
import com.baraka.barakanews.ui.feed.adapter.viewholder.StockListViewHolder

class FeedAdapter(
    private val feedList: List<Feed>,
    private val listener: FeedAdapterListener?
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemCount(): Int = feedList.size

    override fun getItemViewType(position: Int): Int {
        return when (feedList[position]) {
            is StockList -> FeedType.STOCK_LIST.ordinal
            is Article -> FeedType.ARTICLE.ordinal
            is ArticleList -> FeedType.ARTICLE_LIST.ordinal
            else -> FeedType.EMPTY.ordinal
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            FeedType.STOCK_LIST.ordinal -> {
                StockListViewHolder(
                    ItemStockListBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            FeedType.ARTICLE.ordinal -> {
                ArticleViewHolder(
                    ItemArticleBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            FeedType.ARTICLE_LIST.ordinal -> {
                ArticleListViewHolder(
                    ItemArticleListBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
            else -> {
                EmptyViewHolder(
                    ItemEmptyBinding.inflate(
                        LayoutInflater.from(parent.context), parent, false
                    )
                )
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is StockListViewHolder -> {
                holder.bind(feedList[position] as StockList)
            }
            is ArticleViewHolder -> {
                holder.bind(feedList[position] as Article, listener)
            }
            is ArticleListViewHolder -> {
                holder.bind(feedList[position] as ArticleList, listener)
            }
        }
    }

}

