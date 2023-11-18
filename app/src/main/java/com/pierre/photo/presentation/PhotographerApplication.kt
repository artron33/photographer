package com.pierre.photo.presentation

import android.app.Application
import android.content.Context


class PhotographerApplication : Application() {

    companion object {
        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }

}
