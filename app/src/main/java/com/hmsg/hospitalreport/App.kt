package com.hmsg.hospitalreport

import android.app.Application
import android.content.Context

class App : Application() {
    companion object{
        lateinit var instance: Application
        fun getContext(): Context {
            return instance!!
        }
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
    }
}