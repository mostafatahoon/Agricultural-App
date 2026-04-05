/**
 * # AgriInsight AI - Setup & Configuration Guide
 *
 * This file contains all the necessary setup steps to get the data layer working.
 */

// ============================================================================
// STEP 1: ADD REQUIRED DEPENDENCIES
// ============================================================================

// File: app/build.gradle.kts
// Add to dependencies section:

/*
dependencies {
    // Retrofit
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    
    // Room
    implementation("androidx.room:room-runtime:2.5.2")
    ksp("androidx.room:room-compiler:2.5.2")
    
    // Hilt
    implementation("com.google.dagger:hilt-android:2.46")
    kapt("com.google.dagger:hilt-compiler:2.46")
    
    // Kotlin Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.1")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.1")
    
    // Lifecycle
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.6.1")
    
    // Testing (optional but recommended)
    testImplementation("junit:junit:4.13.2")
    testImplementation("org.mockito:mockito-core:5.2.0")
    testImplementation("org.mockito.kotlin:mockito-kotlin:5.0.0")
    testImplementation("androidx.room:room-testing:2.5.2")
}
*/

// ============================================================================
// STEP 2: CONFIGURE GRADLE BUILD
// ============================================================================

// File: app/build.gradle.kts
// Make sure plugins include:

/*
plugins {
    id("com.android.application")
    kotlin("android")
    kotlin("kapt")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}
*/

// ============================================================================
// STEP 3: SETUP API KEY
// ============================================================================

// File: local.properties (in root directory)
// Add your Gemini API key:

/*
GEMINI_API_KEY=your_actual_api_key_here
*/

// Get API Key:
// 1. Go to https://ai.google.dev/
// 2. Click "Get API Key"
// 3. Create a new project or use existing
// 4. Generate API key
// 5. Copy and paste to local.properties

// ============================================================================
// STEP 4: EXPOSE API KEY IN BUILD CONFIG
// ============================================================================

// File: app/build.gradle.kts
// Add to android block:

/*
android {
    // ... existing config ...
    
    buildFeatures {
        buildConfig = true
    }
    
    buildTypes {
        debug {
            buildConfigField("String", "AIzaSyAhT9eDlwKoyYVcAQpMifVBvB216B9_0Y", 
                "\"${project.properties["GEMINI_API_KEY"]}\"")
        }
        release {
            buildConfigField("String", "AIzaSyAhT9eDlwKoyYVcAQpMifVBvB216B9_0Y", 
                "\"${project.properties["GEMINI_API_KEY"]}\"")
        }
    }
}
*/

// ============================================================================
// STEP 5: UPDATE ANDROID MANIFEST
// ============================================================================

// File: app/src/main/AndroidManifest.xml

/*
<?xml version="1.0" encoding="utf-8"?>
<manifest xmlns:android="http://schemas.android.com/apk/res/android">

    <!-- Add Internet permission -->
    <uses-permission android:name="android.permission.INTERNET" />

    <application
        android:name=".MyApp"
        ... other attributes ...
        >
        
        <!-- Your activities here -->
        
    </application>

</manifest>
*/

// ============================================================================
// STEP 6: VERIFY HILT SETUP
// ============================================================================

// File: app/src/main/java/com/example/agriculturalapp/MyApp.kt

/*
package com.example.agriculturalapp

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApp : Application()
*/

// ============================================================================
// STEP 7: VERIFY DATA MODULES
// ============================================================================

// The following files should already exist after setup:

/*
✅ di/DataModule.kt
   - Provides: Retrofit, GeminiApi, RemoteDataSource
   - Provides: Database, DAO, LocalDataSource
   - Provides: AnalysisRepository

✅ di/UseCaseModule.kt
   - Provides all 5 use cases
*/

// ============================================================================
// STEP 8: TEST IN ACTIVITY/FRAGMENT
// ============================================================================

/*
package com.example.agriculturalapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import com.example.agriculturalapp.presentation.example.AnalysisExampleViewModel

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val viewModel: AnalysisExampleViewModel = hiltViewModel()
            // Use viewModel here
        }
    }
}
*/

// ============================================================================
// STEP 9: VERIFY DATA LAYER COMPILATION
// ============================================================================

/*
1. Open Terminal in Android Studio
2. Run: ./gradlew clean build
3. Check for errors related to:
   - Room annotations (@Entity, @Dao, @Database)
   - Retrofit annotations (@POST, @Query, @Body)
   - Hilt annotations (@HiltViewModel, @Inject)
   - Kotlin coroutines (suspend functions)

Common errors and fixes:
   - "Cannot find symbol: GeminiApi" → Check imports in DataModule
   - "Room cannot find a setter for property" → Ensure Entity has no-arg constructor
   - "Missing symbols for Hilt" → Rebuild or clear cache
*/

// ============================================================================
// STEP 10: FILE CHECKLIST
// ============================================================================

/*
DOMAIN LAYER:
✅ domain/entity/AnalysisResult.kt
✅ domain/repository/AnalysisRepository.kt
✅ domain/usecase/AnalysisUseCases.kt

DATA LAYER - REMOTE:
✅ data/dtomodel/GeminiRequest.kt
✅ data/dtomodel/GeminiResponse.kt
✅ data/remote/apiservice.kt
✅ data/remote/RemoteDataSource.kt

DATA LAYER - LOCAL:
✅ data/local/AnalysisResultEntity.kt
✅ data/local/AnalysisResultDao.kt
✅ data/local/AgriInsightDatabase.kt
✅ data/local/LocalDataSource.kt
✅ data/local/StaticDataProvider.kt

DATA LAYER - IMPLEMENTATION:
✅ data/repositoryimpl/AnalysisRepositoryImpl.kt
✅ data/utils/DataUtils.kt

DEPENDENCY INJECTION:
✅ di/DataModule.kt
✅ di/UseCaseModule.kt

EXAMPLES & TESTS:
✅ presentation/example/AnalysisExampleViewModel.kt
✅ data/test/DataLayerUnitTests.kt

DOCUMENTATION:
✅ QUICK_REFERENCE.kt (this file)
✅ README.kt (in data/)
✅ INTEGRATION_GUIDE.kt (in root)
*/

// ============================================================================
// STEP 11: ENVIRONMENT SETUP SCRIPT
// ============================================================================

/*
PowerShell Script to verify setup:

# 1. Check if local.properties exists
Test-Path "local.properties"

# 2. Verify key files exist
$files = @(
    "app/src/main/java/com/example/agriculturalapp/domain/entity/AnalysisResult.kt",
    "app/src/main/java/com/example/agriculturalapp/domain/repository/AnalysisRepository.kt",
    "app/src/main/java/com/example/agriculturalapp/data/remote/RemoteDataSource.kt",
    "app/src/main/java/com/example/agriculturalapp/data/local/AgriInsightDatabase.kt"
)

foreach ($file in $files) {
    if (Test-Path $file) {
        Write-Host "✓ $file exists"
    } else {
        Write-Host "✗ $file missing"
    }
}

# 3. Build project
./gradlew build --warning-mode all
*/

// ============================================================================
// STEP 12: COMMON SETUP ISSUES & SOLUTIONS
// ============================================================================

/*
ISSUE 1: "Cannot resolve symbol: GeminiApi"
SOLUTION:
  - Verify apiservice.kt has correct imports
  - Check @Inject constructor parameters in DataModule
  - Rebuild project: gradlew clean build

ISSUE 2: "Room cannot find a setter for property id"
SOLUTION:
  - Ensure AnalysisResultEntity is a data class
  - Primary key should be marked with @PrimaryKey
  - All properties must have getters/setters

ISSUE 3: "Hilt cannot inject into MyActivity"
SOLUTION:
  - Ensure MyApp.kt has @HiltAndroidApp
  - Activity must have @AndroidEntryPoint
  - Check AndroidManifest.xml references MyApp

ISSUE 4: "API returns empty candidates list"
SOLUTION:
  - Verify API key is valid in BuildConfig
  - Check prompt format (must have contents > parts > text)
  - Ensure API endpoint is correct

ISSUE 5: "Database locked"
SOLUTION:
  - Use Dispatchers.IO for database operations
  - Don't block main thread
  - Use Room suspend functions properly

ISSUE 6: "No Network Permission"
SOLUTION:
  - Add to AndroidManifest.xml:
    <uses-permission android:name="android.permission.INTERNET" />
*/

// ============================================================================
// STEP 13: PRODUCTION CHECKLIST
// ============================================================================

/*
Before deploying to production:

SECURITY:
□ API key in local.properties (NOT in source control)
□ No sensitive data in logs
□ HTTPS enforced for network calls
□ Input validation on all user data
□ Database encryption enabled (optional)

TESTING:
□ Unit tests written for use cases
□ Integration tests for repository
□ API response validation
□ Error handling tested
□ Network failure scenarios tested

PERFORMANCE:
□ Pagination implemented for large datasets
□ Database queries optimized with indexes
□ Network caching implemented
□ Memory leaks checked
□ Database size monitored

MAINTENANCE:
□ Error logging implemented
□ Analytics tracking added
□ Documentation updated
□ Version compatibility verified
□ Dependencies updated to latest stable
*/

// ============================================================================
// STEP 14: USEFUL GRADLE COMMANDS
// ============================================================================

/*
# Clean and build
./gradlew clean build

# Build debug version
./gradlew assembleDebug

# Build release version
./gradlew assembleRelease

# Run tests
./gradlew test

# Check dependencies
./gradlew dependencies

# Lint check
./gradlew lint

# Update dependencies
./gradlew dependencyUpdates

# Run specific test
./gradlew testDebugUnitTest --tests "*DataLayerUnitTests*"
*/

// ============================================================================
// STEP 15: IDE CONFIGURATION (Android Studio)
// ============================================================================

/*
1. Enable Annotation Processing:
   - Settings → Build, Execution, Deployment → Compiler → Annotation Processors
   - Enable annotation processing

2. Configure Room:
   - Settings → Languages & Frameworks → SQL Dialects
   - Select project → SQLite

3. Setup Gradle:
   - Settings → Build, Execution, Deployment → Gradle
   - Gradle JDK → Embedded JDK (or your preferred JDK 11+)

4. Enable Kotlin:
   - Install Kotlin plugin (usually pre-installed)
   - Settings → Languages & Frameworks → Kotlin
   - Verify Kotlin is available

5. Code Inspection:
   - Settings → Editor → Code Completion
   - Enable "Show suggestions while typing"
   - Enable "Complete statement on enter"
*/

