package com.ai.onlineai.models

import kotlinx.serialization.Serializable

@Serializable
data class NetModelResponse(
    val name:String,
    val imageByteArray: String
)
