package com.kseniya.wallpapers.core.navigation

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.kseniya.wallpapers.core.navigation.components.AppBottomBar
import com.kseniya.wallpapers.core.navigation.components.AppNavigationRail
import com.kseniya.wallpapers.core.navigation.graphs.NavigationBarNavGraph
import com.kseniya.wallpapers.presentation.ThemeViewModel

@Composable
fun NavigationScreen(
    navController: NavHostController = rememberNavController(),
    themeViewModel: ThemeViewModel,
    rootNavController: NavHostController,
    windowSize: WindowSizeClass
) {
    val screens = listOf(
        NavigationBarScreen.Home,
        NavigationBarScreen.Favorites,
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    when (windowSize.widthSizeClass) {
        WindowWidthSizeClass.Compact -> {
            AppNavigationPortrait(
                themeViewModel,
                navController,
                rootNavController,
                currentDestination,
                screens,
                elementOnClick = { route ->
                    elementOnClick(route = route, navController = navController)
                },
            )
        }

        else -> {
            AppNavigationLandscape(
                themeViewModel,
                navController,
                rootNavController,
                currentDestination,
                screens,
                elementOnClick = { route ->
                    elementOnClick(route = route, navController = navController)
                }
            )
        }
    }
}

fun elementOnClick(route: String, navController: NavHostController) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id)
        launchSingleTop = true
    }
}

@Composable
fun AppNavigationLandscape(
    themeViewModel: ThemeViewModel,
    navController: NavHostController,
    rootNavController: NavHostController,
    currentDestination: NavDestination?,
    screens: List<NavigationBarScreen>,
    elementOnClick: (String) -> Unit
) {
    Row {
        AppNavigationRail(
            currentDestination = currentDestination,
            screens = screens,
            elementOnClick = elementOnClick
        )
        NavigationBarNavGraph(
            navController = navController,
            rootNavController = rootNavController,
            themeViewModel = themeViewModel,
        )
    }
}

@Composable
fun AppNavigationPortrait(
    themeViewModel: ThemeViewModel,
    navController: NavHostController,
    rootNavController: NavHostController,
    currentDestination: NavDestination?,
    screens: List<NavigationBarScreen>,
    elementOnClick: (String) -> Unit
) {
    Scaffold(
        bottomBar = {
            AppBottomBar(
                currentDestination = currentDestination,
                screens = screens,
                elementOnClick = elementOnClick
            )
        }
    ) {
        NavigationBarNavGraph(
            navController = navController,
            rootNavController = rootNavController,
            modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
            themeViewModel = themeViewModel,
        )
    }
}

