package com.example.androidsubmision1.core.domain.repository

import kotlinx.coroutines.flow.Flow

interface ISettingRepository {
    fun getThemeSetting() : Flow<Boolean>
    suspend fun saveThemeSetting(isDarkModeActive: Boolean)
}