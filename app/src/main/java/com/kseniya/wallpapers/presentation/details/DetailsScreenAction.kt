package com.kseniya.wallpapers.presentation.details


sealed class DetailsScreenAction {

    object GoBack : DetailsScreenAction()

    object Explore : DetailsScreenAction()
}