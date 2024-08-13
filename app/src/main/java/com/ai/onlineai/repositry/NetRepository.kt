package com.ai.onlineai.repositry

import com.ai.onlineai.models.NetModelResponse

interface NetRepository {
    suspend fun detectObjects( input: NetModelResponse):NetModelResponse?
}