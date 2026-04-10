package com.example.agriculturalapp.data.mapper

import com.example.agriculturalapp.data.local.ResultEntity
import com.example.agriculturalapp.domain.entity.AIResponse
import javax.inject.Inject

class ResultEntityMapper @Inject constructor() {
    fun toEntity(response: AIResponse): ResultEntity {
        return ResultEntity(
            id = response.id,
            analysisType = response.prompt,
            inputData = "",
            aiResponse = response.response,
            timestamp = response.timestamp,
            title = response.prompt
        )
    }

    fun toDomain(entity: ResultEntity): AIResponse {
        return AIResponse(
            id = entity.id,
            prompt = entity.analysisType,
            response = entity.aiResponse,
            timestamp = entity.timestamp
        )
    }
}

