package com.kseniya.wallpapers.data.downloader

interface Downloader {
    fun downloadFile(url: String): Long
}