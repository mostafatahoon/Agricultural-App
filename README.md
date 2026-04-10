# Agricultural AI Assistant - Complete Implementation Guide

## Project Overview

A production-ready Android application built with **Kotlin** and **Jetpack Compose** that provides AI-powered agricultural insights using the Gemini API. The app follows **Clean Architecture** and **MVVM** principles with full support for English and Arabic (RTL).

## Architecture Overview

### Clean Architecture Layers

```
┌─────────────────────────────────────┐
│     PRESENTATION LAYER              │ (UI, ViewModels, Screens)
│  ├── Home Screen                    │
│  ├── Analysis Selection Screen      │
│  ├── Dynamic Form Screen            │
│  ├── Result Screen                  │
│  ├── History Screen                 │
│  └── Loading Screen                 │
├─────────────────────────────────────┤
│     DOMAIN LAYER                    │ (Business Logic, Entities, Use Cases)
│  ├── Entities                       │
│  ├── Repository Interfaces          │
│  └── Use Cases                      │
├─────────────────────────────────────┤
│     DATA LAYER                      │ (Data Sources, Mappers)
│  ├── Remote (Gemini API)            │
│  ├── Local (Room Database)          │
│  ├── Static Data                    │
│  └── Mappers                        │
└─────────────────────────────────────┘
```

## Project Structure

```
app/src/main/java/com/example/agriculturalapp/
├── di/                                 # Dependency Injection (Hilt)
│   ├── AppModule.kt
│   ├── LocalDataModule.kt
│   ├── NetworkModule.kt
│   ├── RepositoryModule.kt
│   └── UseCaseModule.kt
│
├── data/                               # Data Layer
│   ├── local/                          # Room Database
│   │   ├── AppDatabase.kt
│   │   ├── ResponseDao.kt
│   │   ├── ResultEntity.kt
│   │   ├── AnalysisType.kt
│   │   └── SettingsManager.kt
│   ├── remote/                         # Retrofit API
│   │   └── GeminiApi.kt
│   ├── dtomodel/                       # Data Transfer Objects
│   │   ├── GeminiRequestDto.kt
│   │   └── GeminiResponseDto.kt
│   ├── static/                         # Static Data
│   │   └── StaticDataProvider.kt
│   ├── mapper/                         # Data Mappers
│   │   ├── GeminiMapper.kt
│   │   ├── ResponseMapper.kt
│   │   └── ResultEntityMapper.kt
│   ├── repository/                     # Repository Implementations
│   │   └── AIRepositoryImpl.kt
│   └── repositoryimpl/
│       └── AIRepositoryImpl.kt
│
├── domain/                             # Domain Layer
│   ├── entity/                         # Domain Models
│   │   ├── AIResponse.kt
│   │   ├── AnalysisInput.kt
│   │   ├── Prompt.kt
│   │   ├── Topic.kt
│   │   └── Word.kt
│   ├── repository/                     # Repository Interfaces
│   │   └── AIRepository.kt
│   └── usecase/                        # Use Cases
│       ├── GeneratePromptUseCase.kt
│       ├── SendPromptUseCase.kt
│       ├── SaveResultUseCase.kt
│       ├── GetResultsUseCase.kt
│       └── GetSavedResponsesUseCase.kt
│
├── presentation/                       # Presentation Layer
│   ├── main/
│   │   ├── MainActivity.kt
│   │   └── MainViewModel.kt
│   ├── navigation/
│   │   ├── AppNavigation.kt
│   │   ├── NavGraph.kt
│   │   └── ScreensRoute.kt
│   ├── home/
│   │   ├── HomeScreen.kt
│   │   ├── HomeViewModel.kt
│   │   └── BottomNavigationBar.kt
│   ├── analyze/
│   │   ├── AnalysisSelectionScreen.kt
│   │   ├── SelectionViewModel.kt
│   │   └── AnalyzeScreen.kt
│   ├── userInput/
│   │   ├── DynamicFormScreen.kt
│   │   └── FormViewModel.kt
│   ├── aiResponse/
│   │   ├── ResultScreen.kt
│   │   └── ResultViewModel.kt
│   ├── history/
│   │   ├── HistoryScreen.kt
│   │   └── HistoryViewModel.kt
│   ├── splash/
│   │   └── SplashScreen.kt
│   └── ui/
│       ├── components/
│       │   └── CommonComponents.kt
│       └── theme/
│           └── Theme.kt
│
├── MyApp.kt                            # Application class (Hilt entry point)
│
└── res/
    ├── values/
    │   └── strings.xml                 # English strings
    └── values-ar/
        └── strings.xml                 # Arabic strings
```

## Analysis Types & Input Fields

### 1. **Irrigation Management** (💧)
- Crop Type
- Water Amount (mm/day)
- Soil Type
- Growth Stage
- Current Weather

### 2. **Disease Diagnosis** (🌿)
- Crop Type
- Symptoms
- Current Season
- Humidity Level (%)

### 3. **Fertilizer Planning** (🌱)
- Soil Analysis Results
- Crop Type
- Growth Stage
- Farm Location

### 4. **Climate Risk Assessment** (🌤️)
- Current Weather
- Crop Type
- Soil Type
- Farm Location

### 5. **Market Planning** (📊)
- Country (Egypt, Saudi Arabia, UAE, Morocco)
- Crop Type
- Current Price (USD/unit)
- Production Costs (USD/unit)
- Market Demand (High/Medium/Low)
- Competition Level (High/Medium/Low)

## Technologies Used

### Core
- **Kotlin** - Programming Language
- **Jetpack Compose** - Modern UI Framework
- **MVVM + Clean Architecture** - Design Pattern

### Networking & Data
- **Retrofit 2.9.0** - HTTP Client
- **Gson 2.10.1** - JSON Serialization
- **Coroutines** - Async Programming

### Local Storage
- **Room Database** - Local Data Persistence
- **DataStore** - User Preferences

### Dependency Injection
- **Hilt** - Dependency Injection Framework

### AI Integration
- **Gemini API** - AI Model for Analysis
- **google-generative-ai-kotlin** - Gemini SDK

### Navigation
- **Jetpack Navigation** - Screen Navigation

## Setup Instructions

### 1. Prerequisites
- Android Studio Jellyfish or later
- Kotlin 1.9+
- Gradle 8.0+
- Gemini API Key (from Google AI Studio)

### 2. Configuration

#### Add Gemini API Key
Create or update `local.properties`:
```properties
GEMINI_API_KEY=your_api_key_here
```

Update `build.gradle.kts`:
```kotlin
buildTypes {
    all {
        buildConfigField("String", "GEMINI_API_KEY", "\"${getApiKey()}\"")
    }
}

fun getApiKey(): String {
    val properties = java.util.Properties()
    val file = rootProject.file("local.properties")
    if (file.exists()) {
        file.inputStream().use { properties.load(it) }
    }
    return properties.getProperty("GEMINI_API_KEY", "")
}
```

### 3. Build & Run

```bash
# Clone the project
git clone <repository-url>
cd Agricultural-App

# Build the project
./gradlew build

# Run on emulator or device
./gradlew installDebug
adb shell am start -n com.example.agriculturalapp/.presentation.main.MainActivity
```

## Key Features

### ✅ Dynamic Form Generation
- Forms are automatically generated based on analysis type
- Different field types: Text, TextArea, Number, Dropdown
- Full validation support

### ✅ AI-Powered Analysis
- Sends structured farm data to Gemini API
- Receives comprehensive analysis with:
  - Current Situation Assessment
  - Recommendations
  - Risk Analysis
  - Action Steps
  - Required Resources
  - Success Indicators

### ✅ Local Storage
- Save all analyses to Room Database
- Retrieve history with timestamps
- Track all previous analyses

### ✅ Multi-Language Support
- Full English and Arabic translation
- RTL (Right-to-Left) Layout Support
- Easy language switching in drawer menu

### ✅ Dark Mode
- Automatic dark/light theme
- User preference persistence
- Toggle in drawer menu

### ✅ Clean Architecture
- Proper separation of concerns
- Testable code structure
- Scalable and maintainable

## Use Cases Implementation

### GeneratePromptUseCase
```kotlin
// Generates AI prompts from farm data
Input: AnalysisInput(type, formData)
Output: Formatted prompt string
```

### SendPromptUseCase
```kotlin
// Sends prompt to Gemini API
Input: Prompt string
Output: AIResponse with parsed text
```

### SaveResultUseCase
```kotlin
// Persists analysis results
Input: AnalysisInput + Response
Output: Saved to Room Database
```

### GetResultsUseCase
```kotlin
// Retrieves all saved analyses
Input: None
Output: Flow<List<AIResponse>>
```

## State Management

### Using StateFlow + MVVM
Each ViewModel manages its own state:

```kotlin
// Example: FormViewModel
private val _formData = MutableStateFlow<Map<String, String>>(emptyMap())
val formData: StateFlow<Map<String, String>> = _formData

private val _isLoading = MutableStateFlow(false)
val isLoading: StateFlow<Boolean> = _isLoading

private val _response = MutableStateFlow<String>("")
val response: StateFlow<String> = _response
```

## Localization

### String Resources
- **values/strings.xml** - English (en)
- **values-ar/strings.xml** - Arabic (ar)

### RTL Support
- Compose automatically handles RTL for Arabic
- All layouts use `Arrangement` and proper alignment
- Text direction handled by `LocalLayoutDirection`

## Error Handling

### Network Errors
- Timeout handling with retry logic
- User-friendly error messages
- Fallback to local cache when available

### Validation
- Empty field validation
- Form field validation before submission
- API response validation

## Testing Strategy

### Unit Tests
- Test use cases with mocked repository
- Test viewmodels with test dispatchers
- Test data mappers

### Integration Tests
- Test API integration
- Test database operations
- Test end-to-end flows

## Navigation Flow

```
Splash Screen
    ↓
Home Screen
    ├→ Analysis Selection Screen
    │   ↓
    │  Dynamic Form Screen
    │   ↓
    │  Result Screen (save option)
    │   ↓
    └→ Home Screen (back)
    │
    └→ History Screen
        ↓
       (View saved analyses)
```

## Prompt Template Structure

Each analysis type has a specific prompt template:

```
1. Expert Role - Specialized in [analysis type]
2. Task Definition - Analysis goal and scope
3. Farm Data - User inputs structured
4. Output Requirements:
   - Analysis
   - Recommendations
   - Risks
   - Steps
   - Resources
   - Success Indicators
```

## Best Practices Implemented

✅ **SOLID Principles**
- Single Responsibility
- Open/Closed Principle
- Liskov Substitution
- Interface Segregation
- Dependency Inversion

✅ **DRY (Don't Repeat Yourself)**
- Reusable components
- Common utilities
- Shared resources

✅ **KISS (Keep It Simple, Stupid)**
- Clear, readable code
- Simple naming conventions
- Minimal complexity

✅ **Code Organization**
- Proper package structure
- Logical file placement
- Clear dependencies

## Performance Optimizations

- Lazy loading in lists
- Efficient state management with StateFlow
- Database queries optimized
- API calls debounced

## Security Considerations

- API key stored in BuildConfig (not hardcoded)
- User data stored locally in Room
- No sensitive data in logs
- HTTPS for all API calls

## Troubleshooting

### Common Issues

**Issue**: Build fails with "GEMINI_API_KEY not found"
**Solution**: Add API key to `local.properties`

**Issue**: Arabic text not displaying
**Solution**: Ensure device language is set to Arabic or check RTL layout

**Issue**: Screens not navigating
**Solution**: Check NavGraph routes match ScreensRoute objects

## Future Enhancements

- [ ] Offline mode with cached responses
- [ ] Image capture for disease diagnosis
- [ ] Weather API integration
- [ ] Market price tracking
- [ ] PDF report generation
- [ ] Cloud backup/sync
- [ ] Analytics dashboard
- [ ] Push notifications
- [ ] User authentication
- [ ] Multi-farm support

## Contributing

Follow the existing code style and architecture patterns when contributing.

## License

This project is licensed under the MIT License.

## Support

For issues or questions, please create an issue in the repository.

---

**Built with ❤️ using Kotlin & Jetpack Compose**

