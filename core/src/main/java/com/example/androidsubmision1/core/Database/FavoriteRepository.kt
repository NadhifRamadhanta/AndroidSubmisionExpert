package com.example.androidsubmission1.core.Database

import android.util.Log
import com.example.androidsubmision1.core.Database.FavoriteDao
import com.example.androidsubmision1.core.utils.DataMapper
import com.example.androidsubmission1.core.domain.model.Favorite
import com.example.androidsubmision1.core.domain.repository.IFavoriteRepository
import com.example.androidsubmission1.core.utils.AppExecutors
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoriteRepository(
    private val favoriteDao: FavoriteDao,
    private val appExecutors: AppExecutors
) : IFavoriteRepository {

    override fun getFavorite(): Flow<List<Favorite>> {
        return favoriteDao.getFavorited().map {
            Log.d("FavoriteRepo","$it")
            DataMapper.mapEntitiesToDomain(it)
        }
    }

    override fun getUsername(username: String): Flow<Favorite> {
        return favoriteDao.getUsername(username).map {
                entity ->
            if (entity != null) {
                DataMapper.mapEntitiesToDomainNonList(entity)
            } else {
                Favorite()
            }
        }
    }

    override fun delFavorite(favorite: Favorite) {
        val dataFavorite = DataMapper.mapDomainToEntitiesNonList(favorite)
        appExecutors.diskIO.execute {
            favoriteDao.delete(dataFavorite)
        }
    }

    override suspend fun setFavorite(favorite: Favorite, favoriteState: Boolean) {
        val dataFavorite = DataMapper.mapDomainToEntitiesNonList(favorite)
        dataFavorite.isFavorite = true
        favorite.isFavorite = favoriteState
        favoriteDao.insert(dataFavorite)
    }
}