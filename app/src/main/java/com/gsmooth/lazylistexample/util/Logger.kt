package com.gsmooth.lazylistexample.util

import android.util.Log

object Logger {

    private const val TAG = "CitySceneApp"

    fun debug(message: String) {
        Log.d(TAG, message)
    }

    fun error(message: String) {
        Log.e(TAG, message)
    }

}