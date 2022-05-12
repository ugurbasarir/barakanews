package com.baraka.barakanews.ui.feed

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.baraka.barakanews.data.models.*
import com.baraka.barakanews.data.repositories.CSVRepository
import com.baraka.barakanews.data.repositories.NewsRepository
import com.baraka.barakanews.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject


@HiltViewModel
open class FeedViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val csvRepository: CSVRepository
) : BaseViewModel() {

    val feedLiveData = MutableLiveData<List<Feed>>()
    val updatedStockLiveData = MutableLiveData<String>()
    val urlLiveData = MutableLiveData<String>()

    private lateinit var stockList: List<Stock>

    private var job: Job? = null

    open fun getNews() {
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                val stocks = async { csvRepository.getStockList() }
                val news = async { newsRepository.getNews().body() ?: ArrayList<Any>() }
                feedLiveData.postValue(createFeedList(awaitAll(stocks, news)))
            }
        }
    }

    private fun createFeedList(list: List<Any>): List<Feed> {
        val feedList = ArrayList<Feed>()
        list.forEach {
            when (it) {
                is StockList -> {
                    it.let {
                        setAllStocks(it)
                        val uniqueStocks = it.list.distinctBy { stock -> stock.id }
                        feedList.add(StockList(uniqueStocks))
                        job?.cancel()
                        job = setStockTimer()
                    }
                }
                is News -> {
                    it.let {
                        // Horizontal List
                        feedList.add(ArticleList(it.articles.take(6)))

                        // Vertical List
                        feedList.addAll(it.articles.drop(6))
                    }
                }
            }
        }
        return feedList
    }

    private fun setAllStocks(stockList: StockList) {
        this.stockList = stockList.list
    }

    private fun setStockTimer(): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (NonCancellable.isActive) {
                if (!feedLiveData.value.isNullOrEmpty() && feedLiveData.value!![0] is StockList) {
                    val randomStock = stockList[(stockList.indices).random()]
                    val stockInFeedLiveData =
                        (feedLiveData.value!![0] as StockList).list.find { it.id == randomStock.id }
                    if (stockInFeedLiveData != null) {
                        stockInFeedLiveData.price = randomStock.price
                        updatedStockLiveData.postValue(randomStock.id)
                    }
                }
                delay(1000L)
            }
        }
    }

    fun onClickArticle(article: Article) {
        urlLiveData.value = article.url
    }

}
