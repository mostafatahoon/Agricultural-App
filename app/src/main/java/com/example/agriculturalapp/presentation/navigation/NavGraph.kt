package com.example.agriculturalapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.agriculturalapp.presentation.analyze.AnalyzeScreen
import com.example.agriculturalapp.presentation.history.HistoryScreen
import com.example.agriculturalapp.presentation.home.HomeScreen
import com.example.agriculturalapp.presentation.splash.SplashScreen


@Composable
fun NavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = ScreensRoute.Splash.route
    ) {
        composable(ScreensRoute.Splash.route) {
            SplashScreen(
                onNavigate = {
                    navController.navigate(ScreensRoute.Home.route) {

                        popUpTo(ScreensRoute.Splash.route) { inclusive = true }
                    }
                }
            )
        }
        composable(ScreensRoute.Home.route) {
            HomeScreen(navController = navController)
        }

        composable(ScreensRoute.Analyze.route) {
            AnalyzeScreen()
        }

        composable(ScreensRoute.History.route) {
            HistoryScreen()
        }
    }
}