package com.example.agriculturalapp.data

/**
 * Quick Reference Card for AgriInsight AI Data Layer
 */

// ============================================================================
// 1. QUICK START - Using the Data Layer in a ViewModel
// ============================================================================

/*
@HiltViewModel
class MyViewModel @Inject constructor(
    private val generateAI: GenerateAIResponseUseCase,
    private val saveResult: SaveAnalysisResultUseCase,
    private val getResults: GetSavedResultsUseCase,
    private val deleteResult: DeleteAnalysisResultUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<UiState>(UiState.Idle)
    val state: StateFlow<UiState> = _state

    fun performAnalysis(analysisType: String, inputs: Map<String, String>) {
        viewModelScope.launch {
            _state.value = UiState.Loading
            try {
                val prompt = DataUtils.buildAnalysisPrompt(analysisType, inputs)
                val aiResponse = generateAI(prompt)
                val result = AnalysisResult(
                    analysisType = AnalysisType.valueOf(analysisType),
                    inputData = DataUtils.mapToJson(inputs),
                    aiResponse = aiResponse
                )
                saveResult(result)
                _state.value = UiState.Success(aiResponse)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Unknown error")
            }
        }
    }

    fun loadHistory() {
        viewModelScope.launch {
            try {
                val results = getResults()
                _state.value = UiState.History(results)
            } catch (e: Exception) {
                _state.value = UiState.Error(e.message ?: "Failed to load history")
            }
        }
    }

    fun removeResult(id: Long) {
        viewModelScope.launch {
            deleteResult(id)
            loadHistory()
        }
    }
}

sealed class UiState {
    object Idle : UiState()
    object Loading : UiState()
    data class Success(val response: String) : UiState()
    data class History(val results: List<AnalysisResult>) : UiState()
    data class Error(val message: String) : UiState()
}
*/

// ============================================================================
// 2. KEY IMPORTS FOR YOUR VIEWMODELS
// ============================================================================

/*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

// Use Cases
import com.example.agriculturalapp.domain.usecase.*

// Domain Models
import com.example.agriculturalapp.domain.entity.AnalysisType
import com.example.agriculturalapp.domain.entity.AnalysisResult

// Data Utils
import com.example.agriculturalapp.data.utils.DataUtils
import com.example.agriculturalapp.data.local.StaticDataProvider
*/

// ============================================================================
// 3. STATIC DATA PROVIDER - Common Queries
// ============================================================================

/*
// Get analysis types
StaticDataProvider.analysisTypes
// Returns: ["IRRIGATION", "DISEASE", "FERTILIZER", "CLIMATE", "MARKET"]

// Get fields for specific type
StaticDataProvider.getFieldsForAnalysisType("MARKET")
// Returns: ["country", "crop", "currentPrice", "productionCost", "demand", "competition"]

// Get dropdown options
StaticDataProvider.getDropdownOptions("country")
// Returns: ["Egypt", "Saudi Arabia", "UAE", "Morocco"]

StaticDataProvider.getDropdownOptions("demand")
// Returns: ["High", "Medium", "Low"]

// Get field description
StaticDataProvider.getFieldDescription("soilType")
// Returns: "Type of soil in the field"

// Build prompt
val inputs = mapOf("crop" to "Wheat", "waterAmount" to "100mm")
StaticDataProvider.buildPromptTemplate("IRRIGATION", inputs)
*/

// ============================================================================
// 4. DATA UTILS - Common Operations
// ============================================================================

/*
// Validate inputs
val (isValid, missingFields) = DataUtils.validateInputFields("IRRIGATION", inputMap)
if (!isValid) {
    println("Missing: ${missingFields.joinToString(", ")}")
}

// Convert Map to JSON for storage
val json = DataUtils.mapToJson(inputMap)

// Convert JSON back to Map
val map = DataUtils.jsonToMap(json)

// Build prompt
val prompt = DataUtils.buildAnalysisPrompt("IRRIGATION", inputMap)

// Format field name for UI
DataUtils.getFieldDisplayName("soilType") // Returns "Soil Type"

// Format timestamp
DataUtils.formatTimestamp(System.currentTimeMillis()) // Returns "Apr 04, 2026 10:30"

// Get key insights
DataUtils.extractKeyInsights(aiResponse) // First 2 sentences
*/

// ============================================================================
// 5. ANALYSIS RESULT MODEL
// ============================================================================

/*
AnalysisResult(
    id = 0,                               // Auto-generated
    analysisType = AnalysisType.IRRIGATION,
    inputData = "{\"crop\": \"Wheat\"}", // Can be JSON
    aiResponse = "AI response text...",
    timestamp = System.currentTimeMillis() // Auto-set
)
*/

// ============================================================================
// 6. COMMON USE CASES
// ============================================================================

/*
// 1. Generate AI Response
val response = generateAIResponseUseCase("Your prompt here")

// 2. Save Result
val result = AnalysisResult(...)
val id = saveAnalysisResultUseCase(result)

// 3. Get All Results
val results = getSavedResultsUseCase()

// 4. Get Specific Result
val result = getResultByIdUseCase(5)

// 5. Delete Result
val deleted = deleteAnalysisResultUseCase(5)
*/

// ============================================================================
// 7. ANALYSIS TYPES & FIELDS REFERENCE
// ============================================================================

/*
IRRIGATION:
  - crop: "Rice", "Wheat", "Corn", etc.
  - waterAmount: "100mm", "50L/m²", etc.
  - soilType: from dropdown
  - growthStage: from dropdown
  - weather: "Sunny", "Rainy", etc.

DISEASE:
  - crop: Type of crop
  - symptoms: Description of visible disease
  - season: from dropdown (Spring, Summer, Autumn, Winter)
  - humidity: "60%", "80%", etc.

FERTILIZER:
  - soilNPK: "10-20-30", "High Nitrogen", etc.
  - crop: Type of crop
  - growthStage: from dropdown
  - location: "Egypt", "Saudi Arabia", etc.

CLIMATE:
  - weatherForecast: Description of forecast
  - crop: Type of crop
  - soilType: from dropdown
  - location: Geographic location

MARKET:
  - country: from dropdown (Egypt, Saudi Arabia, UAE, Morocco)
  - crop: Type of crop
  - currentPrice: "50 EGP/kg", "$10/lb", etc.
  - productionCost: "30 EGP/kg", "$6/lb", etc.
  - demand: from dropdown (High, Medium, Low)
  - competition: from dropdown (High, Medium, Low)
*/

// ============================================================================
// 8. DATABASE OPERATIONS
// ============================================================================

/*
// The repository handles all database operations:

// Insert (returns ID)
val id = repository.saveResult(result)

// Read All (sorted by timestamp DESC)
val results = repository.getSavedResults()

// Read One
val result = repository.getResultById(5)

// Delete
val deletedCount = repository.deleteResult(5)
*/

// ============================================================================
// 9. ERROR HANDLING PATTERN
// ============================================================================

/*
try {
    val response = generateAIResponseUseCase(prompt)
    _state.value = UiState.Success(response)
} catch (e: IllegalStateException) {
    // API error or invalid response
    _state.value = UiState.Error("AI Error: ${e.message}")
} catch (e: Exception) {
    // Network or database error
    _state.value = UiState.Error("Error: ${e.message}")
}
*/

// ============================================================================
// 10. DROPDOWN OPTIONS MAPPING
// ============================================================================

/*
Countries: ["Egypt", "Saudi Arabia", "UAE", "Morocco"]
Demand: ["High", "Medium", "Low"]
Competition: ["High", "Medium", "Low"]
Seasons: ["Spring", "Summer", "Autumn", "Winter"]
Soil Types: ["Clay", "Sandy", "Loamy", "Silty"]
Growth Stages: ["Germination", "Seedling", "Vegetative", "Flowering", "Fruiting", "Maturity"]
*/

// ============================================================================
// 11. FILE LOCATIONS
// ============================================================================

/*
Core Business Logic:
  - domain/entity/AnalysisResult.kt
  - domain/repository/AnalysisRepository.kt
  - domain/usecase/AnalysisUseCases.kt

Data Access:
  - data/remote/RemoteDataSource.kt (Gemini API)
  - data/local/LocalDataSource.kt (Room Database)
  - data/local/StaticDataProvider.kt (In-Memory Data)

Configuration:
  - di/DataModule.kt (Hilt setup)
  - di/UseCaseModule.kt (Use case injection)

Examples:
  - presentation/example/AnalysisExampleViewModel.kt
*/

// ============================================================================
// 12. TESTING UTILITIES
// ============================================================================

/*
// Mock repository for testing
class MockAnalysisRepository : AnalysisRepository {
    override suspend fun generateAIResponse(prompt: String): String {
        return "Mock AI response for: $prompt"
    }

    override suspend fun saveResult(result: AnalysisResult): Long = 1L
    override suspend fun getSavedResults(): List<AnalysisResult> = emptyList()
    override suspend fun deleteResult(id: Long): Int = 1
    override suspend fun getResultById(id: Long): AnalysisResult? = null
}

// Use in tests
@get:Rule
val instantTaskExecutorRule = InstantTaskExecutorRule()

@Test
fun testAnalysis() = runBlocking {
    val mockRepo = MockAnalysisRepository()
    val response = mockRepo.generateAIResponse("test")
    assertEquals("Mock AI response for: test", response)
}
*/

// ============================================================================
// 13. CONFIGURATION NEEDED
// ============================================================================

/*
1. API Key in local.properties:
   GEMINI_API_KEY=your_api_key_here

2. Permissions in AndroidManifest.xml:
   <uses-permission android:name="android.permission.INTERNET" />

3. Hilt setup in MyApp.kt:
   @HiltAndroidApp
   class MyApp : Application()

4. ViewModel injection in Activity/Fragment:
   val viewModel: MyViewModel by viewModels()
*/

// ============================================================================
// 14. STATE MANAGEMENT PATTERN
// ============================================================================

/*
sealed class ResultState {
    object Loading : ResultState()
    data class Success(val results: List<AnalysisResult>) : ResultState()
    data class Error(val exception: Throwable) : ResultState()
}

private val _results = MutableStateFlow<ResultState>(ResultState.Loading)
val results: StateFlow<ResultState> = _results

fun loadResults() {
    viewModelScope.launch {
        _results.value = ResultState.Loading
        try {
            val data = getSavedResultsUseCase()
            _results.value = ResultState.Success(data)
        } catch (e: Exception) {
            _results.value = ResultState.Error(e)
        }
    }
}
*/

// ============================================================================
// 15. API RESPONSE FLOW
// ============================================================================

/*
Request to Gemini:
{
  "contents": [{
    "parts": [{
      "text": "Your prompt here..."
    }]
  }]
}

Response from Gemini:
{
  "candidates": [{
    "content": {
      "parts": [{
        "text": "AI generated response..."
      }]
    }
  }]
}

Extracted: "AI generated response..."
*/

