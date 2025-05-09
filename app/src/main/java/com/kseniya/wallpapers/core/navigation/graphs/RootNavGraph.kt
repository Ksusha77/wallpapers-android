package com.kseniya.wallpapers.core.navigation.graphs

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.kseniya.wallpapers.core.navigation.NavigationScreen
import com.kseniya.wallpapers.core.navigation.graphs.Graph.IMAGE_ID_PARAM
import com.kseniya.wallpapers.core.navigation.graphs.Graph.IS_IMAGE_BOOKMARK
import com.kseniya.wallpapers.core.navigation.graphs.Graph.IS_IMAGE_CURATED_PARAM
import com.kseniya.wallpapers.core.navigation.navanimations.scaleInAnimation
import com.kseniya.wallpapers.core.navigation.navanimations.scaleOutAnimation
import com.kseniya.wallpapers.presentation.ThemeViewModel
import com.kseniya.wallpapers.presentation.details.DetailsScreen


@Composable
fun RootNavigationGraph(
    navController: NavHostController,
    windowSize: WindowSizeClass,
    themeViewModel: ThemeViewModel
) {
    NavHost(
        navController = navController,
        route = Graph.ROOT,
        startDestination = Graph.NAVIGATION_BAR_ROUTE
    ) {
        composable(route = Graph.NAVIGATION_BAR_ROUTE) {
            NavigationScreen(
                windowSize = windowSize,
                rootNavController = navController,
                themeViewModel = themeViewModel
            )
        }
        composable(
            route = Graph.DETAILS_ROUTE + "?${IMAGE_ID_PARAM}={$IMAGE_ID_PARAM}&${IS_IMAGE_CURATED_PARAM}={$IS_IMAGE_CURATED_PARAM}&${IS_IMAGE_BOOKMARK}={$IS_IMAGE_BOOKMARK}",
            arguments = listOf(
                navArgument(
                    name = IMAGE_ID_PARAM
                ) {
                    type = NavType.IntType
                },
                navArgument(
                    name = IS_IMAGE_CURATED_PARAM
                ) {
                    type = NavType.BoolType
                    defaultValue = false
                },
                navArgument(
                    name = IS_IMAGE_BOOKMARK
                ) {
                    type = NavType.BoolType
                    defaultValue = false
                }
            ),
            enterTransition = {
                scaleInAnimation()
            },
            popEnterTransition = {
                scaleInAnimation()
            },
            exitTransition = {
                scaleOutAnimation()
            },
            popExitTransition = {
                scaleOutAnimation()
            }
        ) {
            DetailsScreen(navController = navController)
        }
    }
}

object Graph {

    const val ROOT = "root"
    const val NAVIGATION_BAR_ROUTE = "navigation_route"

    const val DETAILS_ROUTE = "details_route"
    const val IMAGE_ID_PARAM = "movie_id"
    const val IS_IMAGE_CURATED_PARAM = "image_curated"
    const val IS_IMAGE_BOOKMARK = "image_bookmark"
}