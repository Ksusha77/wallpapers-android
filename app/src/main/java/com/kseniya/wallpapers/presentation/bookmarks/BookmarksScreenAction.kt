package com.kseniya.wallpapers.presentation.bookmarks

sealed class BookmarksScreenAction {

    data class OpenImageDetails(val id: Int) : BookmarksScreenAction()

    object Explore : BookmarksScreenAction()
}