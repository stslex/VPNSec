package com.stslex.vpnsec.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.systemuicontroller.SystemUiController
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.stslex.vpnsec.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppInit(
    modifier: Modifier = Modifier,
    systemUiController: SystemUiController = rememberSystemUiController(),
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    navHostController: NavHostController = rememberNavController()
) {
    DisposableEffect(systemUiController, isDarkTheme) {
        systemUiController.setStatusBarColor(
            color = Color.Transparent,
            darkIcons = isDarkTheme.not()
        )
        onDispose {}
    }
    val topAppBarScrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior()
    val scrollConnection = remember {
        topAppBarScrollBehavior.nestedScrollConnection
    }
    Scaffold(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(scrollConnection),
        topBar = {
            AppTopAppbar(scrollBehavior = topAppBarScrollBehavior)
        }
    ) { paddingValues ->
        NavigationHost(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues),
            navHostController = navHostController,
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopAppbar(
    modifier: Modifier = Modifier,
    scrollBehavior: TopAppBarScrollBehavior
) {
    LargeTopAppBar(
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        title = {
            Text(
                modifier = modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                text = LocalContext.current.getString(R.string.app_title),
                style = MaterialTheme.typography.displayMedium,
                textAlign = TextAlign.Center
            )
        }
    )
}