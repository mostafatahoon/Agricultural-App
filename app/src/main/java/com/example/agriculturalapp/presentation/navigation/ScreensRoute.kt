package com.example.agriculturalapp.presentation.navigation

sealed class ScreensRoute(val route: String) {
    object Splash : ScreensRoute("splash_screen")
    object Home : ScreensRoute("home_screen")
    object Analyze : ScreensRoute("analyze_screen")
    object Form : ScreensRoute("form_screen/{analysisType}") {
        fun createRoute(analysisType: String) = "form_screen/$analysisType"
    }
    object Loading : ScreensRoute("loading_screen")
    object Result : ScreensRoute("result_screen")
    object History : ScreensRoute("history_screen")
}