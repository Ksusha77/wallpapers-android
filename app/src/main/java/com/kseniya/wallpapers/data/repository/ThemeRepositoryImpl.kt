package com.kseniya.wallpapers.data.repository

import android.content.Context
import com.kseniya.wallpapers.domain.repository.ThemeRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ThemeRepository {

    private val prefs = context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)
    private val key = "is_dark"

    override fun isDarkTheme(): Boolean {
        return prefs.getBoolean(key, false)
    }

    override fun saveIsDarkTheme(isDark: Boolean) {
        prefs.edit().putBoolean(key, isDark).apply()
    }
}