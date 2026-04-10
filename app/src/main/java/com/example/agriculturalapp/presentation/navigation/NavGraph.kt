package com.example.agriculturalapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.agriculturalapp.domain.entity.AIResponse
import com.example.agriculturalapp.domain.entity.AnalysisInput
import com.example.agriculturalapp.domain.entity.AnalysisType
import com.example.agriculturalapp.presentation.aiResponse.ResultScreen
import com.example.agriculturalapp.presentation.analyze.AnalysisSelectionScreen
import com.example.agriculturalapp.presentation.history.HistoryDetailScreen
import com.example.agriculturalapp.presentation.history.HistoryScreen
import com.example.agriculturalapp.presentation.home.HomeScreen
import com.example.agriculturalapp.presentation.splash.SplashScreen
import com.example.agriculturalapp.presentation.userInput.DynamicFormScreen

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
            HomeScreen(navController)
        }

        composable(ScreensRoute.Analyze.route) {
            AnalysisSelectionScreen(
                onAnalysisSelected = { analysisType ->
                    navController.navigate(ScreensRoute.Form.createRoute(analysisType.id))
                }
            )
        }

        composable(
            route = ScreensRoute.Form.route,
            arguments = listOf(navArgument("analysisType") { type = NavType.StringType })
        ) { backStackEntry ->
            val analysisTypeId = backStackEntry.arguments?.getString("analysisType") ?: return@composable
            val analysisType = AnalysisType.fromId(analysisTypeId) ?: return@composable
            
            DynamicFormScreen(
                analysisType = analysisType,
                onFormSubmit = { response ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("response", response)
                    navController.currentBackStackEntry?.savedStateHandle?.set("analysisType", analysisType)
                    navController.navigate(ScreensRoute.Result.route)
                },
                onBack = { navController.popBackStack() }
            )
        }

        composable(ScreensRoute.Result.route) {
            val response = navController.previousBackStackEntry?.savedStateHandle?.get<String>("response") ?: ""
            val analysisType = navController.previousBackStackEntry?.savedStateHandle?.get<AnalysisType>("analysisType")
            
            if (analysisType != null) {
                ResultScreen(
                    response = response,
                    input = AnalysisInput(analysisType, emptyMap()),
                    onBack = {
                        navController.navigate(ScreensRoute.Home.route) {
                            popUpTo(ScreensRoute.Home.route) { inclusive = false }
                        }
                    }
                )
            }
        }

        composable(ScreensRoute.History.route) {
            HistoryScreen(
                onResultClick = { result ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("history_item", result)
                    navController.navigate(ScreensRoute.HistoryDetail.route)
                }
            )
        }

        composable(ScreensRoute.HistoryDetail.route) {
            val result = navController.previousBackStackEntry?.savedStateHandle?.get<AIResponse>("history_item")
            if (result != null) {
                HistoryDetailScreen(
                    result = result,
                    onBack = { navController.popBackStack() }
                )
            }
        }
    }
}
