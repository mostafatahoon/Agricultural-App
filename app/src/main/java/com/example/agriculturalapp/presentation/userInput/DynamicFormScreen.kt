package com.example.agriculturalapp.presentation.userInput

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.agriculturalapp.R
import com.example.agriculturalapp.data.static.FieldType
import com.example.agriculturalapp.domain.entity.AnalysisType
import com.example.agriculturalapp.ui.components.FormField
import com.example.agriculturalapp.ui.components.DropdownFormField
import com.example.agriculturalapp.ui.components.LoadingOverlay
import androidx.compose.ui.platform.LocalContext

@Composable
fun DynamicFormScreen(
    analysisType: AnalysisType,
    onFormSubmit: (response: String) -> Unit,
    onBack: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: FormViewModel = hiltViewModel()
) {
    val formData = viewModel.formData.collectAsState()
    val isLoading = viewModel.isLoading.collectAsState()
    val response = viewModel.response.collectAsState()
    val error = viewModel.error.collectAsState()
    val fields = viewModel.getFieldsForAnalysis(analysisType)
    val context = LocalContext.current
    val isArabic = context.resources.configuration.locales[0].language == "ar"

    LaunchedEffect(response.value) {
        if (response.value.isNotBlank()) {
            onFormSubmit(response.value)
        }
    }

    // Modern Loading Overlay
    LoadingOverlay(isLoading = isLoading.value)

    Scaffold { innerPadding ->
        LazyColumn(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentPadding = PaddingValues(vertical = 16.dp),
            verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(8.dp)
        ) {
            item {
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    shape = androidx.compose.foundation.shape.RoundedCornerShape(24.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = stringResource(analysisType.titleRes),
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(20.dp),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            item {
                if (!error.value.isNullOrBlank()) {
                    Text(
                        text = error.value.orEmpty(),
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                    )
                }
            }

            items(fields) { field ->
                if (field.type == FieldType.DROPDOWN) {
                    DropdownFormField(
                        label = if (isArabic) field.labelAr else field.labelEn,
                        selectedValue = formData.value[field.id] ?: "",
                        options = if (isArabic) field.optionsAr else field.optionsEn,
                        onOptionSelected = { viewModel.updateField(field.id, it) },
                        placeholder = if (isArabic) field.placeHolderAr else field.placeHolderEn
                    )
                } else {
                    FormField(
                        label = if (isArabic) field.labelAr else field.labelEn,
                        value = formData.value[field.id] ?: "",
                        onValueChange = { viewModel.updateField(field.id, it) },
                        placeholder = if (isArabic) field.placeHolderAr else field.placeHolderEn,
                        singleLine = field.type != FieldType.TEXTAREA,
                        lines = if (field.type == FieldType.TEXTAREA) 4 else 1
                    )
                }
            }

            item {
                Column(modifier = Modifier.padding(16.dp)) {
                    Button(
                        onClick = { viewModel.submitForm(analysisType) },
                        enabled = !isLoading.value,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary,
                            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                            disabledContentColor = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    ) {
                        Text(stringResource(R.string.btn_submit))
                    }

                    OutlinedButton(
                        onClick = onBack,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp),
                    ) {
                        Text(stringResource(R.string.btn_back))
                    }
                }
            }
        }
    }
}
