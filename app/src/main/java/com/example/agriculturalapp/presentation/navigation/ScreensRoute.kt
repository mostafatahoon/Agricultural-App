package com.example.agriculturalapp.presentation.navigation

sealed class ScreensRoute(val route: String) {
    object Splash : ScreensRoute("splash_screen")
    object Home : ScreensRoute("home_screen")
    object Analyze : ScreensRoute("analyze_screen")
    object History : ScreensRoute("history_screen")
}