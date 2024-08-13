package com.ai.onlineai

import android.app.Application
import com.ai.onlineai.di.AppContainer
import com.ai.onlineai.di.DefaultAppContainer

class OnlineAiClientApplication: Application() {
    lateinit var appContainer: AppContainer

    override fun onCreate() {
        super.onCreate()
        appContainer = DefaultAppContainer()
    }
}