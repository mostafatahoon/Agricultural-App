package com.example.agriculturalapp.presentation.analyze

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.agriculturalapp.R
import com.example.agriculturalapp.domain.entity.AnalysisType
import com.example.agriculturalapp.ui.components.AnalysisButton

@Composable
fun AnalysisSelectionScreen(
    onAnalysisSelected: (AnalysisType) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: AnalysisSelectionViewModel = hiltViewModel()
) {
    val selectedType by viewModel.selectedType

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 24.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        
        Text(
            text = stringResource(R.string.select_analysis_title),
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = stringResource(R.string.select_analysis_subtitle),
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            lineHeight = 20.sp
        )

        Spacer(modifier = Modifier.height(24.dp))

        LazyColumn(
            contentPadding = PaddingValues(bottom = 24.dp),
            modifier = Modifier.weight(1f)
        ) {
            items(AnalysisType.entries) { analysisType ->
                AnalysisButton(
                    title = stringResource(analysisType.titleRes),
                    description = stringResource(analysisType.descriptionRes),
                    icon = analysisType.icon,
                    isSelected = selectedType == analysisType,
                    onClick = { 
                        viewModel.selectType(analysisType)
                        onAnalysisSelected(analysisType)
                    }
                )
            }
        }
    }
}
