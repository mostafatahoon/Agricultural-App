package com.example.agriculturalapp.data.mapper

import com.example.agriculturalapp.data.local.ResultEntity
import com.example.agriculturalapp.domain.entity.AIResponse

fun AIResponse.toEntity(): ResultEntity {
    return ResultEntity(
        id = id,
        analysisType = prompt,
        inputData = "", // Added missing required field
        aiResponse = response,
        timestamp = timestamp,
        title = "Analysis"
    )
}

fun ResultEntity.toDomain(): AIResponse {
    return AIResponse(
        id = id,
        prompt = analysisType,
        response = aiResponse,
        timestamp = timestamp
    )
}
