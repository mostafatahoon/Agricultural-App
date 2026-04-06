package com.example.agriculturalapp.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import com.example.agriculturalapp.presentation.navigation.AppNavHost
import com.example.agriculturalapp.ui.theme.AgriculturalAPPTheme
import dagger.hilt.android.AndroidEntryPoint
import java.util.Locale

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val isDarkMode by viewModel.isDarkMode.collectAsState()
            val language by viewModel.language.collectAsState()

            // Set Locale
            val locale = Locale(language)
            Locale.setDefault(locale)
            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)

            val layoutDirection = if (language == "ar") LayoutDirection.Rtl else LayoutDirection.Ltr

            AgriculturalAPPTheme(darkTheme = isDarkMode ?: isSystemInDarkTheme()) {
                CompositionLocalProvider(LocalLayoutDirection provides layoutDirection) {
                    AppNavHost(viewModel)
                }
            }
        }
    }
}