package com.example.agriculturalapp.data.local

enum class AnalysisType(val id: String) {
    IRRIGATION("irrigation"),
    PLANTING_ADVICE("planting_advice"),
    PEST_CONTROL("pest_control"),
    SOIL_HEALTH("soil_health");

    companion object {
        fun fromId(id: String): AnalysisType? {
            val normalized = id.trim().lowercase()
            return entries.firstOrNull { type ->
                normalized == type.id ||
                    normalized == type.name.lowercase() ||
                    normalized == type.id.replace("_", " ")
            }
        }
    }
}

