package com.baraka.barakanews.data

sealed class DataStatus {
    object Success : DataStatus()
    object Fail : DataStatus()
    object Loading : DataStatus()
}
