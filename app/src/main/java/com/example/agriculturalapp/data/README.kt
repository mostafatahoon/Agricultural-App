package com.example.agriculturalapp.data

/**
 * # AgriInsight AI - Data Layer Architecture
 *
 * This document describes the complete data layer architecture for the AgriInsight AI application,
 * an AI-powered agriculture assistant built with Kotlin, Retrofit, and Room database.
 *
 * ## Architecture Overview
 *
 * The data layer follows Clean Architecture principles with three main data sources:
 *
 * ### 1. Remote Data Source (Gemini API)
 * - **File**: RemoteDataSource.kt
 * - **Components**:
 *   - GeminiApi: Retrofit interface for API communication
 *   - GeminiRemoteDataSource: Implementation handling API calls
 * - **Responsibility**: Send structured prompts to Google Gemini API and extract responses
 * - **DTOs**: GeminiRequest, GeminiResponse and related data classes
 *
 * ### 2. Local Data Source (Room Database)
 * - **Files**: AnalysisResultEntity.kt, AnalysisResultDao.kt, AgriInsightDatabase.kt, LocalDataSource.kt
 * - **Components**:
 *   - Entity: AnalysisResultEntity for database storage
 *   - DAO: AnalysisResultDao for CRUD operations
 *   - Database: AgriInsightDatabase as Room database instance
 *   - LocalDataSource: Abstraction layer for local data access
 * - **Responsibility**: Persist analysis results locally with support for querying and deletion
 *
 * ### 3. Static Data Source (In-Memory)
 * - **File**: StaticDataProvider.kt
 * - **Components**:
 *   - Analysis types: IRRIGATION, DISEASE, FERTILIZER, CLIMATE, MARKET
 *   - Input fields: Predefined fields per analysis type
 *   - Dropdown options: Countries, demand levels, competition levels, etc.
 * - **Responsibility**: Provide predefined data and prompt templates for analysis
 *
 * ## Project Structure
 *
 * ```
 * com.example.agriculturalapp/
 * ├── data/
 * │   ├── dtomodel/
 * │   │   ├── GeminiRequest.kt      (DTO for API request)
 * │   │   └── GeminiResponse.kt     (DTO for API response)
 * │   ├── remote/
 * │   │   ├── apiservice.kt         (Retrofit API interface)
 * │   │   └── RemoteDataSource.kt   (Remote data implementation)
 * │   ├── local/
 * │   │   ├── AnalysisResultEntity.kt    (Room entity)
 * │   │   ├── AnalysisResultDao.kt       (Room DAO)
 * │   │   ├── AgriInsightDatabase.kt     (Room database)
 * │   │   ├── LocalDataSource.kt         (Local data abstraction)
 * │   │   └── StaticDataProvider.kt      (Static data provider)
 * │   └── repositoryimpl/
 * │       └── AnalysisRepositoryImpl.kt   (Repository implementation)
 * ├── domain/
 * │   ├── entity/
 * │   │   └── AnalysisResult.kt     (Domain model)
 * │   ├── repository/
 * │   │   └── AnalysisRepository.kt (Repository interface)
 * │   └── usecase/
 * │       └── AnalysisUseCases.kt   (Use cases)
 * └── di/
 *     ├── DataModule.kt             (Hilt dependency injection)
 *     └── UseCaseModule.kt          (Use case injection)
 * ```
 *
 * ## Key Features
 *
 * ### Analysis Types
 * - **IRRIGATION**: Recommendations for water management
 * - **DISEASE**: Crop disease identification and treatment
 * - **FERTILIZER**: Soil nutrient analysis and fertilization strategy
 * - **CLIMATE**: Climate impact and weather adaptation
 * - **MARKET**: Market analysis and profitability assessment
 *
 * ### Input Fields Example
 * 
 * #### Irrigation Analysis
 * - crop: Type of crop being analyzed
 * - waterAmount: Amount of water to apply (mm/liters)
 * - soilType: Type of soil in the field
 * - growthStage: Current growth stage
 * - weather: Current weather conditions
 *
 * #### Market Analysis
 * - country: Egypt, Saudi Arabia, UAE, Morocco
 * - crop: Type of crop
 * - currentPrice: Current market price
 * - productionCost: Cost per unit
 * - demand: High, Medium, Low
 * - competition: High, Medium, Low
 *
 * ## Usage Examples
 *
 * ### 1. Generate AI Response
 * ```kotlin
 * @Inject
 * lateinit var generateAIResponseUseCase: GenerateAIResponseUseCase
 *
 * val response = generateAIResponseUseCase("Your prompt here")
 * ```
 *
 * ### 2. Save Analysis Result
 * ```kotlin
 * @Inject
 * lateinit var saveResultUseCase: SaveAnalysisResultUseCase
 *
 * val result = AnalysisResult(
 *     analysisType = AnalysisType.IRRIGATION,
 *     inputData = "{\"crop\": \"Wheat\"}",
 *     aiResponse = "AI generated response"
 * )
 * val id = saveResultUseCase(result)
 * ```
 *
 * ### 3. Retrieve Saved Results
 * ```kotlin
 * @Inject
 * lateinit var getSavedResultsUseCase: GetSavedResultsUseCase
 *
 * val results = getSavedResultsUseCase()
 * ```
 *
 * ### 4. Delete Result
 * ```kotlin
 * @Inject
 * lateinit var deleteResultUseCase: DeleteAnalysisResultUseCase
 *
 * val deletedCount = deleteResultUseCase(resultId)
 * ```
 *
 * ### 5. Get Static Data
 * ```kotlin
 * // Get fields for specific analysis type
 * val fields = StaticDataProvider.getFieldsForAnalysisType("IRRIGATION")
 *
 * // Get dropdown options
 * val countries = StaticDataProvider.getDropdownOptions("country")
 *
 * // Build prompt template
 * val inputData = mapOf(
 *     "crop" to "Wheat",
 *     "waterAmount" to "100",
 *     "soilType" to "Clay"
 * )
 * val prompt = StaticDataProvider.buildPromptTemplate("IRRIGATION", inputData)
 * ```
 *
 * ## Data Models
 *
 * ### Domain Entity (AnalysisResult)
 * - id: Unique identifier
 * - analysisType: Type of analysis performed
 * - inputData: User input (JSON or string)
 * - aiResponse: AI generated response
 * - timestamp: Creation time
 *
 * ### Database Entity (AnalysisResultEntity)
 * - Stored in "analysis_results" table
 * - Maps to AnalysisResult domain model
 * - Indexed by timestamp for chronological queries
 *
 * ## Dependency Injection
 *
 * The project uses Hilt for dependency injection. All components are configured in:
 * - **DataModule.kt**: Provides Retrofit, Database, and RemoteDataSource instances
 * - **UseCaseModule.kt**: Provides all use case instances
 *
 * Inject into any Activity/Fragment/ViewModel:
 * ```kotlin
 * @Inject
 * lateinit var analysisRepository: AnalysisRepository
 * ```
 *
 * ## Coroutines
 *
 * All database and network operations are suspend functions:
 * - Safe to call from coroutine scope (ViewModel, etc.)
 * - Use with Dispatcher.IO for I/O operations
 * - Handle exceptions properly in UI layer
 *
 * ## Error Handling
 *
 * - Remote calls wrapped in try-catch with meaningful error messages
 * - Database operations handled by Room framework
 * - Validation in repository layer
 * - UI layer responsible for user-facing error handling
 *
 * ## Security Considerations
 *
 * - API key stored in BuildConfig (from gradle.properties)
 * - Network communication over HTTPS only (Retrofit default)
 * - Room database local encryption can be added if needed
 * - Validate user input before building prompts
 *
 * ## Future Enhancements
 *
 * - Add caching layer for frequently accessed data
 * - Implement offline mode support
 * - Add data migration strategies
 * - Expand to support multiple AI models
 * - Add analytics tracking
 * - Implement real-time sync with backend
 */

