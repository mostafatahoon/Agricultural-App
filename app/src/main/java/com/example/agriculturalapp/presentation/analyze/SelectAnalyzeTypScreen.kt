package com.example.agriculturalapp.presentation.analyze


import androidx.compose.runtime.Composable
import androidx.compose.material3.Text
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun AnalyzeScreen(viewModel: SelectAnalyzeTypeViewModel = hiltViewModel()) {
    val isAnalyzing by viewModel.isAnalyzing
    val result by viewModel.analysisResult

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        if (isAnalyzing) {
            CircularProgressIndicator(color = Color(0xFF388E3C))
            Spacer(modifier = Modifier.height(16.dp))
            Text("AI is analyzing your field data...")
        } else {
            result?.let {
                Text(
                    text = it,
                    style = MaterialTheme.typography.headlineSmall,
                    color = Color(0xFF2E7D32)
                )
            } ?: Text("Ready to start new analysis")

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { viewModel.startAnalysis() },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C))
            ) {
                Text("Run AI Analysis")
            }
        }
    }
}