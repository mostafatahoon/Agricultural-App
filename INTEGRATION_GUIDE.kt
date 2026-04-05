/**
 * # AgriInsight AI - Data Layer Integration Guide
 *
 * ## Quick Start
 *
 * ### Step 1: Configure API Key
 * Add the following to your `local.properties` file:
 * ```
 * GEMINI_API_KEY=your_actual_api_key_here
 * ```
 *
 * ### Step 2: Update AndroidManifest.xml
 * Add Internet permission:
 * ```xml
 * <uses-permission android:name="android.permission.INTERNET" />
 * ```
 *
 * ### Step 3: Initialize in Application
 * The MyApp.kt file already has @HiltAndroidApp annotation.
 * No additional setup needed!
 *
 * ## Data Flow Diagram
 *
 * ```
 * User Input (UI)
 *     ↓
 * ViewModel (uses Use Cases)
 *     ↓
 * Repository (AnalysisRepositoryImpl)
 *     ├── Remote Data Source (Gemini API)
 *     │   ├── Retrofit (GeminiApi)
 *     │   └── Network Request/Response
 *     │
 *     └── Local Data Source (Room Database)
 *         ├── DAO (AnalysisResultDao)
 *         └── Entity Storage
 * ```
 *
 * ## File Organization
 *
 * ### Data Layer (data/)
 * 
 * **dtomodel/**
 * - GeminiRequest.kt: Request DTO for Gemini API
 * - GeminiResponse.kt: Response DTO from Gemini API
 *
 * **remote/**
 * - apiservice.kt: Retrofit interface (GeminiApi)
 * - RemoteDataSource.kt: Remote data source implementation
 *
 * **local/**
 * - AnalysisResultEntity.kt: Room database entity
 * - AnalysisResultDao.kt: Database access object
 * - AgriInsightDatabase.kt: Room database instance
 * - LocalDataSource.kt: Local data source abstraction
 * - StaticDataProvider.kt: Predefined analysis data
 *
 * **repositoryimpl/**
 * - AnalysisRepositoryImpl.kt: Repository combining all sources
 *
 * **utils/**
 * - DataUtils.kt: Helper utilities for data operations
 *
 * ### Domain Layer (domain/)
 *
 * **entity/**
 * - AnalysisResult.kt: Domain model for analysis results
 *
 * **repository/**
 * - AnalysisRepository.kt: Repository interface (contract)
 *
 * **usecase/**
 * - AnalysisUseCases.kt: Use cases for different operations
 *
 * ### Dependency Injection (di/)
 * - DataModule.kt: Hilt configuration for data layer
 * - UseCaseModule.kt: Hilt configuration for use cases
 *
 * ### Presentation Example (presentation/example/)
 * - AnalysisExampleViewModel.kt: Example of using the data layer
 *
 * ## Complete Code Examples
 *
 * ### Example 1: Perform Analysis and Save Result
 *
 * ```kotlin
 * @HiltViewModel
 * class MyViewModel @Inject constructor(
 *     private val generateAIResponseUseCase: GenerateAIResponseUseCase,
 *     private val saveResultUseCase: SaveAnalysisResultUseCase
 * ) : ViewModel() {
 *
 *     fun analyzeIrrigation(inputData: Map<String, String>) {
 *         viewModelScope.launch {
 *             try {
 *                 // Build prompt
 *                 val prompt = DataUtils.buildAnalysisPrompt("IRRIGATION", inputData)
 *
 *                 // Get AI response
 *                 val aiResponse = generateAIResponseUseCase(prompt)
 *
 *                 // Save result
 *                 val result = AnalysisResult(
 *                     analysisType = AnalysisType.IRRIGATION,
 *                     inputData = DataUtils.mapToJson(inputData),
 *                     aiResponse = aiResponse
 *                 )
 *                 val id = saveResultUseCase(result)
 *
 *                 // Handle success
 *                 println("Result saved with ID: $id")
 *
 *             } catch (e: Exception) {
 *                 // Handle error
 *                 println("Error: ${e.message}")
 *             }
 *         }
 *     }
 * }
 * ```
 *
 * ### Example 2: Load and Display Saved Results
 *
 * ```kotlin
 * @HiltViewModel
 * class ResultsViewModel @Inject constructor(
 *     private val getSavedResultsUseCase: GetSavedResultsUseCase
 * ) : ViewModel() {
 *
 *     private val _results = MutableStateFlow<List<AnalysisResult>>(emptyList())
 *     val results: StateFlow<List<AnalysisResult>> = _results
 *
 *     init {
 *         loadResults()
 *     }
 *
 *     private fun loadResults() {
 *         viewModelScope.launch {
 *             try {
 *                 val allResults = getSavedResultsUseCase()
 *                 _results.value = allResults
 *             } catch (e: Exception) {
 *                 println("Error loading results: ${e.message}")
 *             }
 *         }
 *     }
 * }
 * ```
 *
 * ### Example 3: Delete Result
 *
 * ```kotlin
 * @HiltViewModel
 * class HistoryViewModel @Inject constructor(
 *     private val deleteResultUseCase: DeleteAnalysisResultUseCase,
 *     private val getSavedResultsUseCase: GetSavedResultsUseCase
 * ) : ViewModel() {
 *
 *     fun deleteResult(id: Long) {
 *         viewModelScope.launch {
 *             try {
 *                 val deletedCount = deleteResultUseCase(id)
 *                 if (deletedCount > 0) {
 *                     println("Result deleted successfully")
 *                     // Refresh list
 *                     val updatedResults = getSavedResultsUseCase()
 *                     // Update UI
 *                 }
 *             } catch (e: Exception) {
 *                 println("Error deleting: ${e.message}")
 *             }
 *         }
 *     }
 * }
 * ```
 *
 * ### Example 4: Get Static Data for UI Forms
 *
 * ```kotlin
 * // Get all analysis types
 * val types = StaticDataProvider.analysisTypes
 *
 * // Get fields for specific type
 * val fields = StaticDataProvider.getFieldsForAnalysisType("MARKET")
 * // Returns: ["country", "crop", "currentPrice", "productionCost", "demand", "competition"]
 *
 * // Get dropdown options for field
 * val countries = StaticDataProvider.getDropdownOptions("country")
 * // Returns: ["Egypt", "Saudi Arabia", "UAE", "Morocco"]
 *
 * // Get field description for hints
 * val description = StaticDataProvider.getFieldDescription("soilType")
 * // Returns: "Type of soil in the field"
 * ```
 *
 * ## Database Schema
 *
 * ### analysis_results table
 * ```
 * Column Name    | Type       | Description
 * ─────────────────────────────────────────────────
 * id             | INTEGER    | Primary key (auto-increment)
 * analysisType   | TEXT       | Type of analysis (IRRIGATION, DISEASE, etc.)
 * inputData      | TEXT       | JSON or string input
 * aiResponse     | TEXT       | AI-generated response
 * timestamp      | INTEGER    | Milliseconds since epoch
 * ```
 *
 * ## API Communication
 *
 * ### Request Format to Gemini API
 * ```json
 * {
 *   "contents": [
 *     {
 *       "parts": [
 *         {
 *           "text": "Your prompt here..."
 *         }
 *       ]
 *     }
 *   ]
 * }
 * ```
 *
 * ### Response Format from Gemini API
 * ```json
 * {
 *   "candidates": [
 *     {
 *       "content": {
 *         "parts": [
 *           {
 *             "text": "AI response here..."
 *           }
 *         ]
 *       }
 *     }
 *   ]
 * }
 * ```
 *
 * ## Error Handling
 *
 * ### Common Error Scenarios
 *
 * 1. **Network Error**: Check internet connection and API key validity
 * 2. **Missing Fields**: Validate input before sending to API
 * 3. **Database Error**: Check database permissions and storage space
 * 4. **API Rate Limit**: Implement retry logic with exponential backoff
 * 5. **Invalid Prompt**: Ensure prompt follows API requirements
 *
 * ### Error Handling Pattern\n * ```kotlin
 * viewModelScope.launch {
 *     try {
 *         val response = useCase()
 *         // Handle success
 *     } catch (e: IOException) {
 *         // Network error
 *         showError(\"Network error. Check your connection.\")
 *     } catch (e: HttpException) {
 *         // API error
 *         showError(\"API error: ${e.code()}\")
 *     } catch (e: Exception) {
 *         // Unknown error
 *         showError(\"An error occurred: ${e.message}\")
 *     }
 * }
 * ```
 *
 * ## Testing the Data Layer
 *
 * ### Unit Test Example
 * ```kotlin
 * @Test
 * fun testBuildPrompt() {
 *     val inputData = mapOf(
 *         \"crop\" to \"Wheat\",
 *         \"waterAmount\" to \"100\"
 *     )
 *     val prompt = StaticDataProvider.buildPromptTemplate(\"IRRIGATION\", inputData)
 *     assertTrue(prompt.contains(\"Wheat\"))
 *     assertTrue(prompt.contains(\"100\"))
 * }
 * ```\n *
 * ### Integration Test Example
 * ```kotlin
 * @Test
 * fun testSaveAndRetrieveResult() = runBlocking {
 *     val result = AnalysisResult(
 *         analysisType = AnalysisType.IRRIGATION,
 *         inputData = \"test\",
 *         aiResponse = \"test response\"
 *     )
 *
 *     val id = repository.saveResult(result)
 *     val retrieved = repository.getResultById(id)
 *
 *     assertEquals(result.aiResponse, retrieved?.aiResponse)
 * }
 * ```
 *
 * ## Performance Optimization
 *
 * 1. **Caching**: Consider adding a caching layer for frequently accessed data
 * 2. **Pagination**: Implement pagination for large result lists
 * 3. **Batch Operations**: Group multiple database operations
 * 4. **Network Optimization**: Use OkHttp interceptors for logging and caching
 *
 * ## Security Best Practices
 *
 * 1. **API Key**: Never hardcode in source; use BuildConfig
 * 2. **Data Validation**: Validate all user inputs
 * 3. **HTTPS**: Always use encrypted connections
 * 4. **Sensitive Data**: Avoid logging sensitive information
 * 5. **Database**: Consider encryption for sensitive stored data
 *
 * ## Debugging Tips\n *
 * 1. Enable network logging with OkHttp interceptor
 * 2. Use Room Inspector to view database contents
 * 3. Add Timber for detailed logging
 * 4. Use Android Studio's database debugger
 * 5. Test API calls with Postman before integration\n *
 * ## Troubleshooting
 *
 * ### Issue: "Unable to create GeminiApi" error
 * **Solution**: Check that Retrofit is properly configured in DataModule
 *
 * ### Issue: "No results from API"
 * **Solution**: Verify prompt format and API key in BuildConfig
 *
 * ### Issue: "Database locked" error
 * **Solution**: Ensure database operations are on IO dispatcher
 *
 * ### Issue: "Missing fields validation"
 * **Solution**: Use DataUtils.validateInputFields before processing
 *
 * ## Next Steps
 *
 * 1. ✅ Data Layer implemented
 * 2. 📝 Create ViewModels using use cases
 * 3. 🎨 Build Compose UI screens
 * 4. 🧪 Write unit tests for data layer\n * 5. 📊 Add analytics tracking
 * 6. 🔐 Enhance security measures
 * 7. 🚀 Deploy to production
 *
 * ## References
n * - Gemini API Docs: https://ai.google.dev/
 * - Retrofit: https://square.github.io/retrofit/
n * - Room Database: https://developer.android.com/topic/libraries/architecture/room
 * - Hilt DI: https://developer.android.com/training/dependency-injection/hilt-android
 * - Kotlin Coroutines: https://kotlinlang.org/docs/coroutines-overview.html
 */

