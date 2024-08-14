package com.ai.onlineai.repositry

import android.util.Log
import com.ai.onlineai.api.NetApi
import com.ai.onlineai.models.NetModelResponse
import com.ai.onlineai.models.NetRequest
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetRepositoryImpl(
    private val apiService: NetApi
) : NetRepository {
    private var model: NetModelResponse? = null
    override suspend fun detectObjects(input: NetRequest): NetModelResponse? {
        apiService.sendFrame(input).enqueue(object: Callback<NetModelResponse>{
            override fun onResponse(
                call: Call<NetModelResponse>,
                response: Response<NetModelResponse>
            ) {
                model = response.body()
            }

            override fun onFailure(call: Call<NetModelResponse>, t: Throwable) {
                Log.i("ERROR RESPONSE", t.message.toString())

            }

        })
        Log.e("REPOSITORY", model.toString())

        return model
    }
}