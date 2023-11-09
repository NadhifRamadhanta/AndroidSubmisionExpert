package com.example.androidsubmision1.ViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.androidsubmission1.core.domain.model.Favorite
import com.example.androidsubmision1.core.domain.usecase.FavoriteUseCase

class FavoriteViewModel(private val favoriteUseCase: FavoriteUseCase) : ViewModel() {
    fun getUsername(username: String) = favoriteUseCase.getUsername(username).asLiveData()

    suspend fun saveUserFavorite(favorite: Favorite) {
        favoriteUseCase.setFavorite(favorite = favorite, favoriteState = true)
    }

    fun delete(favorite: Favorite) {
        favoriteUseCase.delFavorite(favorite)
    }


}