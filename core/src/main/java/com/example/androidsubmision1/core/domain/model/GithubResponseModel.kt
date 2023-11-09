package com.example.androidsubmision1.core.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class GithubResponseModel (
    @field:SerializedName("items")
    val items: List<ItemsItemModel>
):Parcelable


@Parcelize
data class ItemsItemModel(
    @field:SerializedName("following_url")
    val followingUrl: String = "",

    @field:SerializedName("login")
    val login: String = "",

    @field:SerializedName("followers_url")
    val followersUrl: String = "",

    @field:SerializedName("avatar_url")
    val avatarUrl: String = "",

    @field:SerializedName("id")
    val id: Int = 0,

):Parcelable