package com.stslex.home_feature.navigation

import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.stslex.core_navigation.AppDestination
import com.stslex.home_feature.ui.FeatureHomeRoute

fun NavGraphBuilder.featureHomeGraph(modifier: Modifier = Modifier) {
    composable(AppDestination.HOME.navigationRoute) {
        FeatureHomeRoute(modifier = modifier)
    }
}