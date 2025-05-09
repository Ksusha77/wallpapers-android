package com.kseniya.wallpapers.data.datasource.remote

import com.kseniya.wallpapers.data.datasource.remote.dto.CollectionsResponse
import com.kseniya.wallpapers.data.datasource.remote.dto.ImageDto
import com.kseniya.wallpapers.data.datasource.remote.dto.ImagesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface PexelsApiService {

    @GET("search")
    suspend fun getImages(
        @Header("Authorization") apiKey: String = API_KEY,
        @Query("query") query: String,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): Response<ImagesResponse>

    @GET("curated")
    suspend fun getCurated(
        @Header("Authorization") apiKey: String = API_KEY,
        @Query("page") page: Int,
        @Query("per_page") perPage: Int
    ): ImagesResponse

    @GET("collections/featured")
    suspend fun getFeaturedCollections(
        @Header("Authorization") apiKey: String = API_KEY,
        @Query("per_page") perPage: Int
    ): Response<CollectionsResponse>

    @GET("photos/{photoId}")
    suspend fun getImageDetails(
        @Header("Authorization") apiKey: String = API_KEY,
        @Path("photoId") id: Int,
    ): Response<ImageDto>

    companion object {

        const val PAGE_SIZE = 30
        const val COLLECTIONS_QUANTITY = 7
        const val MAX_PAGE_SIZE = 80
        const val INITIAL_LOAD = 10
        const val PREFETCH_DISTANCE = 5
        const val BASE_URL = "https://api.pexels.com/v1/"
        const val API_KEY = "3ssDG9Cgmm3dLoqsrWEzy2PCxmOlhETvZUObLQ8Xon0nIxN9zfl4riu1"
    }
}