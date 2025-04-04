package com.kseniya.wallpapers.presentation.bookmarks

sealed class BookmarksScreenEvent {

    object OnExploreClicked : BookmarksScreenEvent()

    data class OnImageClicked(val id: Int) : BookmarksScreenEvent()

}