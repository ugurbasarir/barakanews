package com.baraka.barakanews.extensions

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

@SuppressLint("SimpleDateFormat")
fun String.getUserFriendlyTime(): String {
    val parser = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss")
    return try {
        val date = parser.parse(this) ?: return this
        val formatter = SimpleDateFormat("HH:mm, dd.MM.yyyy")
        formatter.format(date)
    } catch (e: Exception) {
        this
    }
}