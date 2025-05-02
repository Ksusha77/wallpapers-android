package com.kseniya.wallpapers.domain.repository

interface ThemeRepository {
    fun isDarkTheme(): Boolean
    fun saveIsDarkTheme(isDark: Boolean)
}