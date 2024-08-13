package com.ai.onlineai.di

import com.ai.onlineai.repositry.NetRepository

interface AppContainer {
    val netRepository: NetRepository
}