package com.example.androidsubmision1.core.domain.usecase

import com.example.androidsubmision1.core.domain.repository.ISettingRepository

class SettingInteractor(private val settingRepository: ISettingRepository):SettingUseCase {
    override fun getThemeSetting() = settingRepository.getThemeSetting()
    override suspend fun saveThemeSetting(isDarkModeActive: Boolean) = settingRepository.saveThemeSetting(isDarkModeActive)


}