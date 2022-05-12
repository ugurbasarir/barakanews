package com.baraka.barakanews.data.repositories

import android.content.Context
import com.baraka.barakanews.data.models.Stock
import com.baraka.barakanews.data.models.StockList
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

class CSVRepository @Inject constructor(
    @ApplicationContext private val context: Context
) {

    fun getStockList(): StockList {
        val isr = InputStreamReader(context.assets.open("stock.csv"))

        val stockList = ArrayList<Stock>()
        val reader = BufferedReader(isr)
        reader.readLine()
        var line: String?
        while (reader.readLine().also { line = it } != null) {
            if (line == null) continue
            val stock = Stock(
                line!!.split(",")[0].replace("\"", ""),
                line!!.split(",")[1].toFloat()
            )
            stockList.add(stock)
        }
        return StockList(stockList)
    }

}