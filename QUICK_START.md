/**
 * # ⚡ QUICK START - Get Up & Running in 30 Minutes
 */

// ============================================================================
// STEP 1: ENVIRONMENT SETUP (5 minutes)
// ============================================================================

/*
1.1 Add Dependencies
    File: app/build.gradle.kts
    
    Add to dependencies {
        // Retrofit
        implementation("com.squareup.retrofit2:retrofit:2.9.0")
        implementation("com.squareup.retrofit2:converter-gson:2.9.0")
        
        // Room
        implementation("androidx.room:room-runtime:2.5.2")
        ksp("androidx.room:room-compiler:2.5.2")
        
        // Hilt
        implementation("com.google.dagger:hilt-android:2.46")
        kapt("com.google.dagger:hilt-compiler:2.46")
        
        // Coroutines
        implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    }

1.2 Add Plugins
    File: app/build.gradle.kts
    
    Ensure plugins section has:
    - alias(libs.plugins.kotlin.kapt)  (or kapt plugin)
    - alias(libs.plugins.ksp)          (for Room)
    - alias(libs.plugins.hilt)         (for Hilt)

1.3 Add API Key
    File: local.properties (in root directory)
    
    Add line:
    GEMINI_API_KEY=your_actual_api_key_here
    
    Get API key:
    - Visit https://ai.google.dev/
    - Click "Get API Key"
    - Copy and paste

1.4 Add Permissions
    File: app/src/main/AndroidManifest.xml
    
    Add above <application> tag:
    <uses-permission android:name="android.permission.INTERNET" />

1.5 Build Project
    Terminal:
    ./gradlew clean build
    
    Wait for build to complete...
*/

// ============================================================================
// STEP 2: VERIFY SETUP (5 minutes)
// ============================================================================

/*
2.1 Check Files Exist
    ✓ domain/entity/AnalysisResult.kt
    ✓ domain/repository/AnalysisRepository.kt
    ✓ domain/usecase/AnalysisUseCases.kt
    ✓ data/remote/RemoteDataSource.kt
    ✓ data/local/AgriInsightDatabase.kt
    ✓ data/repositoryimpl/AnalysisRepositoryImpl.kt
    ✓ di/DataModule.kt
    ✓ di/UseCaseModule.kt

2.2 Verify MyApp.kt
    Should have:
    @HiltAndroidApp
    class MyApp : Application()

2.3 Verify No Build Errors
    Check: Build Output
    Should see: "BUILD SUCCESSFUL"
    If errors: Check SETUP_GUIDE.kt troubleshooting section
*/

// ============================================================================
// STEP 3: CREATE YOUR FIRST VIEWMODEL (10 minutes)
// ============================================================================

/*
3.1 Create HomeViewModel

File: app/src/main/java/com/example/agriculturalapp/presentation/home/HomeViewModel.kt

Copy this code:
─────────────────────────────────────────────────────────────────

package com.example.agriculturalapp.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.agriculturalapp.domain.entity.AnalysisResult
import com.example.agriculturalapp.domain.usecase.GetSavedResultsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getSavedResultsUseCase: GetSavedResultsUseCase
) : ViewModel() {

    private val _results = MutableStateFlow<List<AnalysisResult>>(emptyList())
    val results: StateFlow<List<AnalysisResult>> = _results

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadResults()
    }

    fun loadResults() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val allResults = getSavedResultsUseCase()
                _results.value = allResults
            } catch (e: Exception) {
                _error.value = e.message ?: "Unknown error"
            } finally {
                _isLoading.value = false
            }
        }
    }
}

─────────────────────────────────────────────────────────────────

Done! ✓
*/

// ============================================================================
// STEP 4: CREATE YOUR FIRST SCREEN (10 minutes)
// ============================================================================

/*
4.1 Create HomeScreen

File: app/src/main/java/com/example/agriculturalapp/presentation/home/HomeScreen.kt

Copy this code:
─────────────────────────────────────────────────────────────────

package com.example.agriculturalapp.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.agriculturalapp.data.local.StaticDataProvider

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onAnalysisTypeSelected: (String) -> Unit = {}
) {
    val results = viewModel.results.collectAsState().value
    val isLoading = viewModel.isLoading.collectAsState().value
    val error = viewModel.error.collectAsState().value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text("AgriInsight AI", style = MaterialTheme.typography.headlineLarge)

        Spacer(modifier = Modifier.height(16.dp))

        // Analysis Types
        Text("Select Analysis Type", style = MaterialTheme.typography.titleMedium)
        
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(StaticDataProvider.analysisTypes.size) { index ->
                val type = StaticDataProvider.analysisTypes[index]
                Button(
                    onClick = { onAnalysisTypeSelected(type) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(type)
                }
            }
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Recent Results
        Text("Recent Analyses", style = MaterialTheme.typography.titleMedium)
        
        when {
            isLoading -> CircularProgressIndicator()
            error != null -> Text("Error: $error", color = MaterialTheme.colorScheme.error)
            results.isEmpty() -> Text("No analyses yet")
            else -> {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(results.size) { index ->
                        val result = results[index]
                        Card(modifier = Modifier.fillMaxWidth()) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(result.analysisType.name, style = MaterialTheme.typography.labelSmall)
                                Text(result.aiResponse.take(100) + "...", maxLines = 2)
                            }
                        }
                    }
                }
            }
        }
    }
}

─────────────────────────────────────────────────────────────────

Done! ✓
*/

// ============================================================================
// STEP 5: TEST YOUR SETUP (5 minutes)
// ============================================================================

/*
5.1 Run Your App
    - Press Shift+F10 (or click Run)
    - Wait for app to install
    - App should launch without crashes

5.2 Check Console for Errors
    - Look for: "No crashes" = success ✓
    - If crashes: Check the error message in Logcat
    - Common error: "Cannot find GeminiApi" → Rebuild
    - Common error: "API key not found" → Check local.properties

5.3 Test ViewModel Injection
    - HomeScreen should display
    - Analysis type buttons should be visible
    - If empty: Database is empty (first time), that's normal

5.4 Add Test Analysis Data (Optional)
    - Use Logcat to verify database operations
    - Check: "All results loaded successfully"
*/

// ============================================================================
// NEXT STEPS
// ============================================================================

/*
Now that basic setup is done:

1. Create remaining screens:
   - SelectAnalyzeTypScreen
   - AnalysisInputScreen
   - AiResponseScreen
   - HistoryScreen

2. For each screen:
   - Create ViewModel (inject use cases)
   - Create Composable (using ViewModel)
   - Add navigation route
   - Test thoroughly

3. Reference files:
   - IMPLEMENTATION_CHECKLIST.kt (detailed roadmap)
   - AnalysisExampleViewModel.kt (code examples)
   - QUICK_REFERENCE.kt (common operations)
   - DataLayerUnitTests.kt (testing examples)

4. Follow this pattern for each screen:
   a. Inject needed use cases in ViewModel
   b. Create StateFlow for UI state
   c. Create methods that call use cases
   d. Create Composable that observes state
   e. Build UI using state values
   f. Connect to navigation
*/

// ============================================================================
// COMMON ISSUES & SOLUTIONS
// ============================================================================

/*
ISSUE: "Cannot resolve symbol: GeminiApi"
FIX: Run gradlew clean build

ISSUE: "API key not found in BuildConfig"
FIX: Check local.properties has GEMINI_API_KEY=...

ISSUE: "No internet permission"
FIX: Add <uses-permission> to AndroidManifest.xml

ISSUE: "@HiltViewModel not recognized"
FIX: Ensure MyApp.kt has @HiltAndroidApp

ISSUE: "Room cannot find a setter"
FIX: Verify AnalysisResultEntity is a data class

ISSUE: "Build fails with Hilt errors"
FIX: Clean cache: gradlew clean build --rerun-tasks

ISSUE: App crashes on launch
FIX: Check Logcat for exact error, compare with SETUP_GUIDE.kt
*/

// ============================================================================
// VERIFICATION CHECKLIST
// ============================================================================

/*
Before proceeding to UI development:

□ Project builds without errors
□ App launches without crashing
□ HomeScreen displays correctly
□ Analysis type buttons are visible
□ No errors in Logcat (warnings are OK)
□ ViewModel is injected successfully
□ Database exists (can be inspected in Android Studio)

If all ✓, you're ready to build more screens!
*/

// ============================================================================
// KEY FILES TO REFERENCE
// ============================================================================

/*
For HomeViewModel pattern:
→ presentation/example/AnalysisExampleViewModel.kt

For using StaticDataProvider:
→ QUICK_REFERENCE.kt (section 2)

For building Compose UI:
→ Your existing screens (HomeScreen.kt already partially implemented)

For navigation setup:
→ IMPLEMENTATION_CHECKLIST.kt (UI section)

For error handling:
→ INTEGRATION_GUIDE.kt (error handling section)
*/

// ============================================================================
// YOU'RE READY! 🚀
// ============================================================================

/*
Congratulations! You've:
✅ Set up the environment
✅ Verified the data layer works
✅ Created your first ViewModel
✅ Created your first Compose screen
✅ Learned how to use the data layer

Next: Build the remaining screens following IMPLEMENTATION_CHECKLIST.kt

Questions? Check:
→ QUICK_REFERENCE.kt
→ INTEGRATION_GUIDE.kt
→ AnalysisExampleViewModel.kt
→ DataLayerUnitTests.kt

Happy coding! 🌾
*/

