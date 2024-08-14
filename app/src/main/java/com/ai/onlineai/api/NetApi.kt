package com.ai.onlineai.api

import com.ai.onlineai.models.NetModelResponse
import com.ai.onlineai.models.NetRequest
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface NetApi {
    @POST("net/detect")
    fun sendFrame(
        @Body pushRequest: NetRequest
    ): Call<NetModelResponse>
}