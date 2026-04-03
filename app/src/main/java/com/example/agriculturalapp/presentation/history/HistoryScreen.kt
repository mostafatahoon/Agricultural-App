package com.example.agriculturalapp.presentation.history

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel


@Composable
fun HistoryScreen(viewModel: HistoryViewModel = viewModel()) {
    Text("History Screen - Items: ${viewModel.results.size}")
}