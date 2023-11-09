package com.example.androidsubmision1.core.domain.usecase

import kotlinx.coroutines.flow.Flow

interface SettingUseCase {
    fun getThemeSetting() : Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}