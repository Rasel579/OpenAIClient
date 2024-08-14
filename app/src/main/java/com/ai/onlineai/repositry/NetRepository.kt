package com.ai.onlineai.repositry

import com.ai.onlineai.models.NetModelResponse
import com.ai.onlineai.models.NetRequest

interface NetRepository {
    suspend fun detectObjects( input: NetRequest):NetModelResponse?
}