package com.example.androidsubmision1.core.utils

import com.example.androidsubmision1.core.RoboPojoResponse.GithubResponse
import com.example.androidsubmision1.core.RoboPojoResponse.ItemsItem
import com.example.androidsubmision1.core.RoboPojoResponse.ProfileResponse
import com.example.androidsubmision1.core.Database.FavoriteEntity
import com.example.androidsubmision1.core.domain.model.GithubResponseModel
import com.example.androidsubmision1.core.domain.model.ItemsItemModel
import com.example.androidsubmision1.core.domain.model.ProfileResponseModel
import com.example.androidsubmission1.core.domain.model.Favorite

object DataMapper {

    fun mapEntitiesToDomain(input: List<FavoriteEntity>): List<Favorite> =
        input.map()
        {
            Favorite(
                username = it.username,
                avatarUrl = it.avatarUrl,
                isFavorite = it.isFavorite
            )
        }

    fun mapEntitiesToDomainNonList(input: FavoriteEntity): Favorite =
        Favorite(
            username = input.username,
            avatarUrl = input.avatarUrl,
            isFavorite = input.isFavorite
        )


    fun mapDomainToEntitiesNonList(input: Favorite): FavoriteEntity =
        FavoriteEntity(
            username = input.username,
            avatarUrl = input.avatarUrl,
            isFavorite = input.isFavorite
        )

    fun mapItemsItemToItemsItemModel(input: ItemsItem): ItemsItemModel = ItemsItemModel(
        followersUrl = input.followersUrl,
        followingUrl = input.followingUrl,
        login = input.login,
        avatarUrl = input.avatarUrl,
        id = input.id
    )

    fun mapGithubToGithubModel(input: GithubResponse): GithubResponseModel =
        GithubResponseModel(
            items = input.items.map {
                mapItemsItemToItemsItemModel(it)
            }
        )

    fun mapProfileResponseToProfileResponseModel(input: ProfileResponse): ProfileResponseModel =
        ProfileResponseModel(
            id = input.id,
            avatarUrl = input.avatarUrl,
            login = input.login,
            followingUrl = input.followingUrl,
            followersUrl = input.followersUrl,
            name = input.name,
            followers = input.followers,
            following = input.following,
            location = input.location,
            publicGists = input.publicGists,
            publicRepos = input.publicRepos
        )

}