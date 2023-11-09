package com.example.androidsubmision1.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.androidsubmision1.core.domain.usecase.FavoriteUseCase

class FavoriteViewModelDynamicFeatures(private val favoriteUseCase: FavoriteUseCase) : ViewModel() {
    fun getFavorite() = favoriteUseCase.getFavorite().asLiveData()
}