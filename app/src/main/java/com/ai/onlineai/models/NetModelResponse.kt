package com.ai.onlineai.models

import kotlinx.serialization.Serializable

@Serializable
data class NetModelResponse(
    val name:String,
    val topX: Double,
    val topY: Double,
    val bottomX: Double,
    val bottomY: Double,
    val errorCode:Int?,
    val errorMessage: String?,
    val imageByteArray: String?
)
