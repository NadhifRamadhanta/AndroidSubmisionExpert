package com.example.androidsubmision1.core.domain.usecase

import com.example.androidsubmision1.core.API.ApiResponse
import com.example.androidsubmision1.core.domain.model.GithubResponseModel
import com.example.androidsubmision1.core.domain.model.ItemsItemModel
import com.example.androidsubmision1.core.domain.model.ProfileResponseModel
import kotlinx.coroutines.flow.Flow

interface GithubUserUseCase {

    suspend fun getUserList(searchUser: String?): Flow<ApiResponse<GithubResponseModel>>

    suspend fun getDetailUser(username : String): Flow<ApiResponse<ProfileResponseModel>>

    suspend fun getFollowers(username : String): Flow<ApiResponse<List<ItemsItemModel>>>

    suspend fun getFollowing(username : String): Flow<ApiResponse<List<ItemsItemModel>>>
}