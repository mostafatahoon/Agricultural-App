package com.example.agriculturalapp.presentation.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.agriculturalapp.R
import com.example.agriculturalapp.presentation.navigation.ScreensRoute


@Composable
fun HomeScreen(navController: NavController, viewModel: HomeViewModel = viewModel()) {
    Scaffold(
        topBar = {
            HomeTopBar(stringResource(R.string.home_title))
        },
        containerColor = MaterialTheme.colorScheme.background
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = stringResource(R.string.home_headline),
                fontSize = 32.sp,
                fontWeight = FontWeight.ExtraBold,
                lineHeight = 38.sp,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = stringResource(R.string.home_description),
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { navController.navigate(ScreensRoute.Analyze.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                shape = RoundedCornerShape(28.dp)
            ) { Text(stringResource(R.string.start_analysis), fontWeight = FontWeight.Bold) }

            Spacer(modifier = Modifier.height(12.dp))

            OutlinedButton(
                onClick = { navController.navigate(ScreensRoute.History.route) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(28.dp),
                border = BorderStroke(1.5.dp, Color(0xFF388E3C))
            ) { Text(stringResource(R.string.view_saved_results), color = Color(0xFF388E3C)) }

            Spacer(modifier = Modifier.height(30.dp))

            Card(
                shape = RoundedCornerShape(32.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .height(250.dp)
                    .padding(bottom = 20.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.farming_landscape),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(title: String) {
    CenterAlignedTopAppBar(
        title = { Text(text = title, fontWeight = FontWeight.Bold, color = Color(0xFF2E7D32)) },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = Color.Transparent),
        modifier = Modifier.padding(top = 16.dp)
    )
}

@Composable
fun BottomNavigationBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    NavigationBar(
        containerColor = MaterialTheme.colorScheme.surface,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = currentRoute == ScreensRoute.Home.route,
            onClick = {
                if (currentRoute != ScreensRoute.Home.route) {
                    navController.navigate(ScreensRoute.Home.route) {
                        popUpTo(ScreensRoute.Home.route) { inclusive = true }
                    }
                }
            },
            icon = {
                if (currentRoute == ScreensRoute.Home.route) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Home,
                            contentDescription = stringResource(R.string.home_nav),
                            tint = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                } else {
                    Icon(Icons.Default.Home, contentDescription = stringResource(R.string.home_nav))
                }
            },
            label = { Text(stringResource(R.string.home_nav)) }
        )

        NavigationBarItem(
            selected = currentRoute == ScreensRoute.History.route,
            onClick = {
                if (currentRoute != ScreensRoute.History.route) {
                    navController.navigate(ScreensRoute.History.route)
                }
            },
            icon = {
                if (currentRoute == ScreensRoute.History.route) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF2E7D32),
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = stringResource(R.string.history_nav),
                            tint = Color.White,
                            modifier = Modifier.padding(8.dp)
                        )
                    }
                } else {
                    Icon(Icons.Default.Refresh, contentDescription = stringResource(R.string.history_nav))
                }
            },
            label = { Text(stringResource(R.string.history_nav)) }
        )
    }
}
