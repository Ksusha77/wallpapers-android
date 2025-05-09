package com.kseniya.wallpapers.presentation.bookmarks

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import com.kseniya.wallpapers.core.navigation.graphs.Graph
import com.kseniya.wallpapers.core.util.Container
import com.kseniya.wallpapers.presentation.ThemeViewModel
import com.kseniya.wallpapers.presentation.bookmarks.components.BookmarksGrid
import com.kseniya.wallpapers.presentation.bookmarks.components.BookmarksTopBar
import com.kseniya.wallpapers.presentation.bookmarks.components.NoBookmarksStub

@Composable
fun BookmarksScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    themeViewModel: ThemeViewModel,
    viewModel: BookmarksViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val isDark by themeViewModel.isDark.collectAsStateWithLifecycle()

    Container(viewModel.actionFlow) { action ->
        when (action) {
            is BookmarksScreenAction.OpenImageDetails -> {
                val routeWithParams =
                    "${Graph.DETAILS_ROUTE}?${Graph.IMAGE_ID_PARAM}=${action.id}&${Graph.IS_IMAGE_BOOKMARK}=${true}"
                navController.navigate(route = routeWithParams) {
                    launchSingleTop = true
                }
            }

            BookmarksScreenAction.Explore -> {
                navController.navigate(navController.graph.findStartDestination().id)
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            BookmarksTopBar(
                isDark = isDark,
                toggleTheme = themeViewModel::toggleTheme,
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(top = padding.calculateTopPadding())) {
            if (state.isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 12.dp)
                )
            }
            if (state.isNoBookmarks) {
                NoBookmarksStub {
                    viewModel.onEvent(BookmarksScreenEvent.OnExploreClicked)
                }
            } else {
                BookmarksGrid(
                    images = state.images,
                    imageOnClicked = { id ->
                        viewModel.onEvent(BookmarksScreenEvent.OnImageClicked(id))
                    },
                    modifier = Modifier.padding(start = 24.dp, end = 24.dp, top = 38.dp)
                )
            }
        }
    }
}