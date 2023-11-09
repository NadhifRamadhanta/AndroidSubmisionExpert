package com.example.androidsubmision1.core.Database

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class FavoriteEntity(
    @PrimaryKey(autoGenerate = false)
    var username: String = "",

    var avatarUrl: String? = null,

    var isFavorite: Boolean = false
): Parcelable