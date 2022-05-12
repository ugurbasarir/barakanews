package com.baraka.barakanews.ui.feed.adapter.viewholder

import android.annotation.SuppressLint
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baraka.barakanews.data.models.Stock
import com.baraka.barakanews.data.models.StockList
import com.baraka.barakanews.databinding.ItemStockBinding
import com.baraka.barakanews.databinding.ItemStockListBinding


class StockListViewHolder(
    private val itemBinding: ItemStockListBinding
) : RecyclerView.ViewHolder(itemBinding.root) {

    private lateinit var data: StockList

    fun bind(data: StockList) {
        this.data = data
        itemBinding.recyclerView.adapter = Adapter()
    }

    fun updateStock(stockId: String) {
        val position = data.list.indexOfFirst { it.id == stockId }
        (itemBinding.recyclerView.adapter as Adapter).notifyItemChanged(position)
    }

    inner class Adapter : RecyclerView.Adapter<Adapter.StockViewHolder>() {

        override fun getItemCount(): Int = data.list.size

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
            return StockViewHolder(
                ItemStockBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }

        override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
            holder.bind(data.list[position])
        }

        inner class StockViewHolder(private val itemBinding: ItemStockBinding) :
            RecyclerView.ViewHolder(itemBinding.root) {

            @SuppressLint("SetTextI18n")
            fun bind(data: Stock) {

                val spannable: Spannable = SpannableString("${data.id} : ${data.price}")
                spannable.setSpan(
                    ForegroundColorSpan(Color.BLACK),
                    0,
                    data.id.length + 2,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannable.setSpan(
                    ForegroundColorSpan(if (data.price > 0) Color.GREEN else Color.RED),
                    data.id.length + 2,
                    spannable.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                itemBinding.textView.text = spannable
            }
        }

    }
}


