package com.example.reply.ui

import android.app.Application
import android.content.Context


class LocationApplication : Application() {

    companion object {
        lateinit var mContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        mContext = applicationContext
    }

}
