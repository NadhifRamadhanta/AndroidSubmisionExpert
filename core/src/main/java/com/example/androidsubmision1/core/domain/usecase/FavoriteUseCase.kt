package com.example.androidsubmision1.core.domain.usecase

import androidx.lifecycle.LiveData
import com.example.androidsubmission1.core.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface FavoriteUseCase {
    fun getFavorite(): Flow<List<Favorite>>

    fun getUsername(username: String): Flow<Favorite>

    fun delFavorite(favorite: Favorite)

    suspend fun setFavorite(favorite: Favorite, favoriteState: Boolean)
}