package com.kseniya.wallpapers.presentation.home

sealed class HomeScreenAction {

    data class OpenImageDetails(val id: Int, val isCurated: Boolean) : HomeScreenAction()
}