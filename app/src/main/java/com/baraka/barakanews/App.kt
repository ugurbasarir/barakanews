package com.baraka.barakanews

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
open class App : Application() {
    companion object {
        var screenWidth = 0
        var screenHeight = 0
    }

    override fun onCreate() {
        super.onCreate()

        setScreenDimensionValues()
    }

    private fun setScreenDimensionValues() {
        val dm = resources.displayMetrics
        screenWidth = dm.widthPixels
        screenHeight = dm.heightPixels
    }
}
