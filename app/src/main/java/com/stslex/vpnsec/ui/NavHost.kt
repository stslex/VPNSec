package com.stslex.vpnsec.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.stslex.core_navigation.AppDestination
import com.stslex.home_feature.navigation.featureHomeGraph

@Composable
fun NavigationHost(
    modifier: Modifier = Modifier,
    navHostController: NavHostController,
    startDestination: AppDestination = AppDestination.HOME
) {
    NavHost(
        navController = navHostController,
        startDestination = startDestination.navigationRoute,
        builder = {
            featureHomeGraph(modifier = modifier)
        }
    )
}