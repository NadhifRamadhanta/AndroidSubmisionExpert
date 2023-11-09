package com.example.androidsubmision1.core.domain.repository

import com.example.androidsubmission1.core.domain.model.Favorite
import kotlinx.coroutines.flow.Flow

interface IFavoriteRepository {
    fun getFavorite(): Flow<List<Favorite>>

    fun getUsername(username: String): Flow<Favorite>

    fun delFavorite(favorite: Favorite)

    suspend fun setFavorite(favorite: Favorite, favoriteState: Boolean)
}