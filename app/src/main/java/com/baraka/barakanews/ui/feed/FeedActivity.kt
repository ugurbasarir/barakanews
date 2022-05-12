package com.baraka.barakanews.ui.feed

import android.content.Intent
import android.net.Uri
import androidx.activity.viewModels
import com.baraka.barakanews.data.models.*
import com.baraka.barakanews.databinding.ActivityFeedBinding
import com.baraka.barakanews.ui.base.BaseActivity
import com.baraka.barakanews.ui.feed.adapter.FeedAdapter
import com.baraka.barakanews.ui.feed.adapter.FeedAdapterListener
import com.baraka.barakanews.ui.feed.adapter.viewholder.StockListViewHolder
import com.baraka.barakanews.utils.observe
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class FeedActivity : BaseActivity() {

    private val feedViewModel: FeedViewModel by viewModels()
    private lateinit var binding: ActivityFeedBinding

    override fun initViewBinding() {
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.refreshView.setOnRefreshListener {
            feedViewModel.getNews()
        }

        binding.refreshView.isRefreshing = true
        feedViewModel.getNews()
    }

    private fun handleFeedList(list: List<Feed>) {
        binding.refreshView.isRefreshing = false
        val adapter = FeedAdapter(list, object : FeedAdapterListener {
            override fun onClickArticle(article: Article) {
                feedViewModel.onClickArticle(article)
            }
        })
        binding.recyclerView.adapter = adapter
    }

    private fun handleUpdateStock(stockId: String) {
        val viewHolder = binding.recyclerView.findViewHolderForAdapterPosition(0)
        if (viewHolder !is StockListViewHolder) return
        viewHolder.updateStock(stockId)
    }

    private fun handleUrl(url: String) {
        val i = Intent(Intent.ACTION_VIEW)
        i.data = Uri.parse(url)
        startActivity(i)
    }

    override fun observeViewModel() {
        observe(feedViewModel.feedLiveData, ::handleFeedList)
        observe(feedViewModel.updatedStockLiveData, ::handleUpdateStock)
        observe(feedViewModel.urlLiveData, ::handleUrl)
    }

}