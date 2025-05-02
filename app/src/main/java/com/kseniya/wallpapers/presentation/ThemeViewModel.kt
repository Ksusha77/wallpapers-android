package com.kseniya.wallpapers.presentation

import androidx.lifecycle.ViewModel
import com.kseniya.wallpapers.domain.repository.ThemeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class ThemeViewModel @Inject constructor(
    private val repository: ThemeRepository
) : ViewModel() {

    private val _isDark = MutableStateFlow(repository.isDarkTheme())
    val isDark: StateFlow<Boolean> = _isDark.asStateFlow()

    fun toggleTheme() {
        val newValue = !_isDark.value
        _isDark.value = newValue
        repository.saveIsDarkTheme(newValue)
    }
}