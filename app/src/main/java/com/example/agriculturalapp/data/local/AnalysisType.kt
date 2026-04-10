package com.example.agriculturalapp.data.local

import com.example.agriculturalapp.R

enum class AnalysisType(
    val id: String,
    val titleRes: Int,
    val descriptionRes: Int,
    val icon: String
) {
    IRRIGATION(
        "irrigation",
        R.string.analysis_irrigation,
        R.string.analysis_irrigation,
        "💧"
    ),
    DISEASE(
        "disease",
        R.string.analysis_disease,
        R.string.analysis_disease,
        "🪴"
    ),
    FERTILIZER(
        "fertilizer",
        R.string.analysis_fertilizer,
        R.string.analysis_fertilizer,
        "♻️"
    ),
    CLIMATE(
        "climate",
        R.string.analysis_climate,
        R.string.analysis_climate,
        "🌧️"
    ),
    MARKET(
        "market",
        R.string.analysis_market,
        R.string.analysis_market,
        "📈"
    );

    companion object {
        fun fromId(id: String): AnalysisType? = values().find { it.id == id }
    }
}
