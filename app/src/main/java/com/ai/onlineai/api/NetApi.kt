package com.ai.onlineai.api

import com.ai.onlineai.models.NetModelResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NetApi {
    @POST("net/detect")
    fun sendFrame(
        @Body pushRequest: NetModelResponse
    ): Call<NetModelResponse>
}