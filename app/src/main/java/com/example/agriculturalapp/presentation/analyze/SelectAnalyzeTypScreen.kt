package com.example.agriculturalapp.presentation.analyze

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun AnalyzeScreen(viewModel: SelectAnalyzeTypeViewModel = viewModel()) {
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
