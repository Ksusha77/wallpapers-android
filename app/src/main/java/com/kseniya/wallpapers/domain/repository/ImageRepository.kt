package com.kseniya.wallpapers.domain.repository

import androidx.paging.PagingData
import com.kseniya.wallpapers.core.util.Resource
import com.kseniya.wallpapers.domain.model.Collection
import com.kseniya.wallpapers.domain.model.Image
import kotlinx.coroutines.flow.Flow

interface ImageRepository {

    suspend fun getImages(query: String = ""): Flow<PagingData<Image>>

    suspend fun getFeaturedCollections(quantity: Int): List<Collection>

    suspend fun getImageDetails(
        id: Int,
        isImageCurated: Boolean = false,
        isImageBookmark: Boolean = false
    ): Resource<Image>

    suspend fun getBookmarks(): Flow<Resource<List<Image>>>

    suspend fun checkForBookmark(src: String): Boolean

    suspend fun addBookmark(image: Image)

    suspend fun deleteBookmark(src: String)

    suspend fun saveImage(src: String)
}