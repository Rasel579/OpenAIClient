package com.ai.onlineai.di

import com.ai.onlineai.api.NetApi
import com.ai.onlineai.repositry.NetRepository
import com.ai.onlineai.repositry.NetRepositoryImpl
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * Implementation for the Dependency Injection container at the application level.
 *
 * Variables are initialized lazily and the same instance is shared across the whole app.
 */
class DefaultAppContainer : AppContainer {
    private val baseUrl = "http://192.168.0.10:8080/"

    /**
     * Use the Retrofit builder to build a retrofit object using a kotlinx.serialization converter
     */
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
        .client(
            OkHttpClient()
                .newBuilder().apply {
                    addInterceptor(
                        HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY

                        })
                }.build()
        )
        .baseUrl(baseUrl).build()

    private val netService: NetApi by lazy {
        retrofit.create(NetApi::class.java)
    }

    override val netRepository: NetRepository by lazy {
        NetRepositoryImpl(netService)
    }
}