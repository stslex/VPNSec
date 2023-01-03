package com.stslex.home_feature.ui

import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun FeatureHomeScreen(
    modifier: Modifier = Modifier,
    viewModel: FeatureHomeViewModel
) {
    Surface(modifier = modifier) {
        Text(text = "home")
    }
}