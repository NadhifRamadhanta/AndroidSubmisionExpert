package com.example.androidsubmision1.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.androidsubmision1.core.domain.usecase.SettingUseCase
import kotlinx.coroutines.launch

class SettingViewModel(private val settingUseCase: SettingUseCase) : ViewModel() {
    fun getThemeSettings(): LiveData<Boolean> {
        return settingUseCase.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            settingUseCase.saveThemeSetting(isDarkModeActive)
        }
    }
}