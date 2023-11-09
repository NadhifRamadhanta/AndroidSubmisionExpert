package com.example.androidsubmision1.core.Database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(favorite: FavoriteEntity)

    @Delete
    fun delete(favorite: FavoriteEntity)

    @Query("SELECT * FROM FavoriteEntity WHERE username = :username")
    fun getUsername(username: String): Flow<FavoriteEntity>

    @Query("SELECT * FROM FavoriteEntity where isFavorite = 1")
    fun getFavorited(): Flow<List<FavoriteEntity>>

}