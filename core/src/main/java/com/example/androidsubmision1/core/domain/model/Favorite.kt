package com.example.androidsubmission1.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Favorite(
    var username: String = "",

    var avatarUrl: String? = null,

    var isFavorite: Boolean = false
):Parcelable
