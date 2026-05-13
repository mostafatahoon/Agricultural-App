package com.example.agriculturalapp.presentation.userInput

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBackIos
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.agriculturalapp.R
import com.example.agriculturalapp.data.static.FieldType
import com.example.agriculturalapp.domain.entity.AnalysisType
import com.example.agriculturalapp.ui.components.AreaUnitFormField
import com.example.agriculturalapp.ui.components.DropdownFormField
import com.example.agriculturalapp.ui.components.FormField
import com.example.agriculturalapp.ui.components.LoadingOverlay
import com.example.agriculturalapp.ui.components.LocationFormField

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

    LoadingOverlay(isLoading = isLoading.value)

    Scaffold { innerPadding ->
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(innerPadding)
        ) {
            // Custom Header
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFF1F8E9)),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                androidx.compose.foundation.layout.Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(12.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.SpaceBetween
                ) {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBackIos,
                            contentDescription = "Back",
                            tint = Color(0xFF2E7D32),
                            modifier = Modifier.padding(start = 8.dp)
                        )
                    }
                    Text(
                        text = stringResource(analysisType.titleRes),
                        fontSize = 22.sp,
                        fontWeight = FontWeight.ExtraBold,
                        color = Color(0xFF1B5E20)
                    )
                    Spacer(modifier = Modifier.width(48.dp)) // Placeholder for balance
                }
            }

            LazyColumn(
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = androidx.compose.foundation.layout.Arrangement.spacedBy(4.dp)
            ) {
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
                    // Check dependency
                    val shouldShow = field.dependsOn == null || 
                        formData.value[field.dependsOn] == field.showIfValue

                    if (shouldShow) {
                        when (field.type) {
                            FieldType.LOCATION -> {
                                LocationFormField(
                                    label = if (isArabic) field.labelAr else field.labelEn,
                                    onLocationChanged = { viewModel.updateField(field.id, it) }
                                )
                            }
                            FieldType.DROPDOWN -> {
                                DropdownFormField(
                                    label = if (isArabic) field.labelAr else field.labelEn,
                                    selectedValue = formData.value[field.id] ?: "",
                                    options = if (isArabic) field.optionsAr else field.optionsEn,
                                    onOptionSelected = { viewModel.updateField(field.id, it) },
                                    placeholder = if (isArabic) field.placeHolderAr else field.placeHolderEn
                                )
                            }
                            FieldType.AREA_UNIT -> {
                                AreaUnitFormField(
                                    label = if (isArabic) field.labelAr else field.labelEn,
                                    value = formData.value[field.id] ?: "",
                                    onValueChange = { viewModel.updateField(field.id, it) },
                                    unit = formData.value["${field.id}_unit"] ?: (if (isArabic) "فدان" else "Feddan"),
                                    onUnitChange = { viewModel.updateField("${field.id}_unit", it) },
                                    units = if (isArabic) field.optionsAr else field.optionsEn,
                                    placeholder = if (isArabic) field.placeHolderAr else field.placeHolderEn
                                )
                            }
                            else -> {
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
                    }
                }
            }

            // Analysis Button at bottom
            Button(
                onClick = { viewModel.submitForm(analysisType) },
                enabled = !isLoading.value,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
                    .height(56.dp),
                shape = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF4CAF50),
                    contentColor = Color.White
                )
            ) {
                androidx.compose.foundation.layout.Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = androidx.compose.foundation.layout.Arrangement.Center
                ) {
                    Icon(Icons.Default.AutoAwesome, null)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        stringResource(R.string.btn_submit),
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}
