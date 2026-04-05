package com.example.agriculturalapp.presentation.example

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agriculturalapp.data.local.StaticDataProvider
import com.example.agriculturalapp.data.utils.DataUtils
import com.example.agriculturalapp.domain.entity.AnalysisResult
import com.example.agriculturalapp.domain.entity.AnalysisType
import com.example.agriculturalapp.domain.usecase.DeleteAnalysisResultUseCase
import com.example.agriculturalapp.domain.usecase.GenerateAIResponseUseCase
import com.example.agriculturalapp.domain.usecase.GetResultByIdUseCase
import com.example.agriculturalapp.domain.usecase.GetSavedResultsUseCase
import com.example.agriculturalapp.domain.usecase.SaveAnalysisResultUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Example ViewModel demonstrating how to use the data layer architecture
 * This shows all the key operations: Generate, Save, Retrieve, and Delete
 */
@HiltViewModel
class AnalysisExampleViewModel @Inject constructor(
    private val generateAIResponseUseCase: GenerateAIResponseUseCase,
    private val saveAnalysisResultUseCase: SaveAnalysisResultUseCase,
    private val getSavedResultsUseCase: GetSavedResultsUseCase,
    private val deleteAnalysisResultUseCase: DeleteAnalysisResultUseCase,
    private val getResultByIdUseCase: GetResultByIdUseCase
) : ViewModel() {

    private val _analysisState = MutableStateFlow<AnalysisState>(AnalysisState.Idle)
    val analysisState: StateFlow<AnalysisState> = _analysisState

    private val _savedResults = MutableStateFlow<List<AnalysisResult>>(emptyList())
    val savedResults: StateFlow<List<AnalysisResult>> = _savedResults

    // Example 1: Generate AI Response for Irrigation Analysis
    fun performIrrigationAnalysis(
        crop: String,
        waterAmount: String,
        soilType: String,
        growthStage: String,
        weather: String
    ) {
        viewModelScope.launch {
            try {
                _analysisState.value = AnalysisState.Loading

                // Build input data map
                val inputData = mapOf(
                    "crop" to crop,
                    "waterAmount" to waterAmount,
                    "soilType" to soilType,
                    "growthStage" to growthStage,
                    "weather" to weather
                )

                // Validate input
                val (isValid, missingFields) = DataUtils.validateInputFields("IRRIGATION", inputData)
                if (!isValid) {
                    _analysisState.value = AnalysisState.Error("Missing fields: ${missingFields.joinToString(", ")}")
                    return@launch
                }

                // Build prompt using static data provider
                val prompt = DataUtils.buildAnalysisPrompt("IRRIGATION", inputData)

                // Generate AI response
                val aiResponse = generateAIResponseUseCase(prompt)

                // Save result locally
                val result = AnalysisResult(
                    analysisType = AnalysisType.IRRIGATION,
                    inputData = DataUtils.mapToJson(inputData),
                    aiResponse = aiResponse
                )
                val savedId = saveAnalysisResultUseCase(result)

                _analysisState.value = AnalysisState.Success(result, savedId)

                // Refresh saved results
                loadSavedResults()

            } catch (e: Exception) {
                _analysisState.value = AnalysisState.Error(e.message ?: "Unknown error occurred")
            }
        }
    }

    // Example 2: Load all saved results
    fun loadSavedResults() {
        viewModelScope.launch {
            try {
                val results = getSavedResultsUseCase()
                _savedResults.value = results
            } catch (e: Exception) {
                _analysisState.value = AnalysisState.Error("Failed to load results: ${e.message}")
            }
        }
    }

    // Example 3: Delete a specific result
    fun deleteResult(id: Long) {
        viewModelScope.launch {
            try {
                val deletedCount = deleteAnalysisResultUseCase(id)
                if (deletedCount > 0) {
                    _analysisState.value = AnalysisState.Deleted(id)
                    loadSavedResults()
                } else {
                    _analysisState.value = AnalysisState.Error("Result not found")
                }
            } catch (e: Exception) {
                _analysisState.value = AnalysisState.Error("Failed to delete: ${e.message}")
            }
        }
    }

    // Example 4: Get static analysis types
    fun getAvailableAnalysisTypes(): List<String> {
        return StaticDataProvider.analysisTypes
    }

    // Example 5: Get input fields for a specific analysis type
    fun getFieldsForAnalysisType(analysisType: String): List<String> {
        return StaticDataProvider.getFieldsForAnalysisType(analysisType)
    }

    // Example 6: Get dropdown options for a field
    fun getDropdownOptions(fieldName: String): List<String>? {
        return StaticDataProvider.getDropdownOptions(fieldName)
    }

    // Example 7: Get specific result
    fun getResultById(id: Long) {
        viewModelScope.launch {
            try {
                val result = getResultByIdUseCase(id)
                if (result != null) {
                    _analysisState.value = AnalysisState.Success(result, id)
                } else {
                    _analysisState.value = AnalysisState.Error("Result not found")
                }
            } catch (e: Exception) {
                _analysisState.value = AnalysisState.Error("Error loading result: ${e.message}")
            }
        }
    }
}

/**
 * Sealed class representing different analysis states
 */
sealed class AnalysisState {
    object Idle : AnalysisState()
    object Loading : AnalysisState()
    data class Success(val result: AnalysisResult, val id: Long) : AnalysisState()
    data class Error(val message: String) : AnalysisState()
    data class Deleted(val id: Long) : AnalysisState()
}

