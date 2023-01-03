package com.stslex.home_feature.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
fun FeatureHomeRoute(
    modifier: Modifier = Modifier,
    viewModel: FeatureHomeViewModel = koinViewModel()
) {
    FeatureHomeScreen(
        modifier = modifier,
        viewModel = viewModel
    )
}