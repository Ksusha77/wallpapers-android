package com.kseniya.wallpapers.data.datasource.remote.dto


import com.google.gson.annotations.SerializedName

data class ImageDto(
    val alt: String,
    @SerializedName("avg_color")
    val avgColor: String,
    val height: Int,
    val id: Int,
    val liked: Boolean,
    val photographer: String,
    val src: Src,
    val url: String,
    val width: Int
)