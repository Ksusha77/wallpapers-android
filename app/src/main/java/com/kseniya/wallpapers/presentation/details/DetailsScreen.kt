package com.kseniya.wallpapers.presentation.details

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.kseniya.wallpapers.R
import com.kseniya.wallpapers.core.util.Container
import com.kseniya.wallpapers.presentation.details.components.DetailsBottomBar
import com.kseniya.wallpapers.presentation.details.components.DetailsTopBar
import com.kseniya.wallpapers.presentation.details.components.NotFoundStub

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun DetailsScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: DetailsViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    val permissionState =
        rememberPermissionState(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
    if (!permissionState.status.isGranted) {
        LaunchedEffect(permissionState) {
            permissionState.launchPermissionRequest()
        }
    }

    Container(viewModel.actionFlow) { action ->
        when (action) {
            DetailsScreenAction.Explore -> {
                navController.popBackStack(
                    navController.graph.findStartDestination().id,
                    inclusive = false
                )
            }

            DetailsScreenAction.GoBack -> {
                navController.navigateUp()
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            DetailsTopBar(
                name = state.photographer,
                backButtonAction = {
                    viewModel.onEvent(DetailsScreenEvent.OnBackClicked)
                }
            )
        },
        bottomBar = {
            AnimatedVisibility(state is DetailsState.ImageDetails) {
                DetailsBottomBar(
                    isBookmark = state.isBookmark,
                    onDownloadClicked = {
                        viewModel.onEvent(DetailsScreenEvent.OnDownloadClicked)
                    },
                    onBookmarkClicked = {
                        viewModel.onEvent(DetailsScreenEvent.OnBookmarkClicked)
                    },
                    onActionClicked = {
                        shareImageLink(context = context, imageUrl = state.src)
                    }
                )
            }
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            when (state) {
                is DetailsState.ImageDetails -> {
                    Box(
                        modifier = Modifier
                            .padding(24.dp)
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        AsyncImage(
                            model = state.src,
                            contentDescription = null,
                            contentScale = ContentScale.Fit,
                            modifier = Modifier
                                .clip(RoundedCornerShape(16.dp))

                        )
                    }
                }

                DetailsState.IsLoading -> {
                    LinearProgressIndicator(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                    )
                }

                DetailsState.IsNotFound -> {
                    NotFoundStub {
                        viewModel.onEvent(DetailsScreenEvent.OnExploreClicked)
                    }
                }
            }
        }
    }
}

fun shareImageLink(context: Context, imageUrl: String) {
    val intent = Intent(Intent.ACTION_SEND).apply {
        this.type = "text/plain"
        putExtra(Intent.EXTRA_TEXT, imageUrl)
    }

    context.startActivity(Intent.createChooser(intent, context.getString(R.string.share_image_via)))
}