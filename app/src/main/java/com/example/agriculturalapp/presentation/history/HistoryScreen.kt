package com.example.agriculturalapp.presentation.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun HistoryScreen(viewModel: HistoryViewModel = hiltViewModel()) {
    val items = viewModel.results

    if (items.isEmpty()) {
        Text(
            text = "History Screen - No saved items yet",
            modifier = Modifier.padding(16.dp)
        )
        return
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(items) { result ->
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(text = result.prompt, style = MaterialTheme.typography.titleMedium)
                Text(text = result.response, style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}