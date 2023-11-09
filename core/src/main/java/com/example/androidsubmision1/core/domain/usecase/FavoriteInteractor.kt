package com.example.androidsubmision1.core.domain.usecase

import android.util.Log
import com.example.androidsubmission1.core.domain.model.Favorite
import com.example.androidsubmision1.core.domain.repository.IFavoriteRepository

class FavoriteInteractor(private val favoriteRepository: IFavoriteRepository) : FavoriteUseCase {
    override fun getFavorite() = favoriteRepository.getFavorite()

    override fun getUsername(username: String) = favoriteRepository.getUsername(username)

    override fun delFavorite(favorite: Favorite) = favoriteRepository.delFavorite(favorite)

    override suspend fun setFavorite(favorite: Favorite, favoriteState: Boolean) {
        favoriteRepository.setFavorite(favorite, favoriteState)
        Log.d("FavInter","$favoriteState")
    }
}