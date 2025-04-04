package com.kseniya.wallpapers.presentation.bookmarks

import com.kseniya.wallpapers.domain.model.Image

data class BookmarksState(

    val isLoading: Boolean = true,

    val images: List<Image> = emptyList(),

    val isNoBookmarks: Boolean = false
)