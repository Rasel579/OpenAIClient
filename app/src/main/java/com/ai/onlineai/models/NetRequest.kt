package com.ai.onlineai.models

import kotlinx.serialization.Serializable

@Serializable
data class NetRequest(
    val name:String,
    val imageByteArray:String
)
