package com.kseniya.wallpapers.core.navigation.graphs

import android.annotation.SuppressLint
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.kseniya.wallpapers.core.navigation.NavigationBarScreen
import com.kseniya.wallpapers.core.navigation.navanimations.intoLeftAnimation
import com.kseniya.wallpapers.core.navigation.navanimations.intoRightAnimation
import com.kseniya.wallpapers.core.navigation.navanimations.outLeftAnimation
import com.kseniya.wallpapers.core.navigation.navanimations.outRightAnimation
import com.kseniya.wallpapers.presentation.ThemeViewModel
import com.kseniya.wallpapers.presentation.bookmarks.BookmarksScreen
import com.kseniya.wallpapers.presentation.home.HomeScreen

@SuppressLint("RestrictedApi")
@Composable
fun NavigationBarNavGraph(
    modifier: Modifier = Modifier,
    themeViewModel: ThemeViewModel,
    navController: NavHostController,
    rootNavController: NavHostController
) {
    NavHost(
        navController = navController,
        route = Graph.NAVIGATION_BAR_ROUTE,
        startDestination = NavigationBarScreen.Home.route
    ) {
        composable(
            route = NavigationBarScreen.Home.route,
            enterTransition = {
                when (navController.previousBackStackEntry?.destination?.route) {
                    NavigationBarScreen.Favorites.route -> {
                        intoLeftAnimation()
                    }

                    else -> {
                        null
                    }
                }
            },
            exitTransition = {
                when (navController.currentDestination?.route) {
                    NavigationBarScreen.Favorites.route -> {
                        outRightAnimation()
                    }

                    else -> {
                        null
                    }
                }
            }
        ) {
            HomeScreen(
                modifier = modifier,
                navController = rootNavController
            )
        }
        composable(
            route = NavigationBarScreen.Favorites.route,
            enterTransition = {
                when (navController.previousBackStackEntry?.destination?.route) {
                    NavigationBarScreen.Home.route -> {
                        intoRightAnimation()
                    }

                    else -> {
                        null
                    }
                }
            },
            exitTransition = {
                when (navController.currentDestination?.route) {
                    NavigationBarScreen.Home.route -> {
                        outLeftAnimation()
                    }

                    else -> {
                        null
                    }
                }
            }
        ) {
            BookmarksScreen(
                modifier = modifier,
                navController = rootNavController,
                themeViewModel = themeViewModel,
            )
        }
    }
}