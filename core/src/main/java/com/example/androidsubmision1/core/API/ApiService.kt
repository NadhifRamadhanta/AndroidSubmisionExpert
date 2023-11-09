package com.example.androidsubmision1.core.API

import com.example.androidsubmision1.core.RoboPojoResponse.GithubResponse
import com.example.androidsubmision1.core.RoboPojoResponse.ItemsItem
import com.example.androidsubmision1.core.RoboPojoResponse.ProfileResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getUsers(
        @Query("q") username: String
    ): Response<GithubResponse>

    @GET("users/{username}")
    suspend fun getProfile(
        @Path("username") username: String
    ): Response<ProfileResponse>

    @GET("users/{username}/followers")
    suspend fun getFollowers(
        @Path("username") username: String
    ): Response<List<ItemsItem>>

    @GET("users/{username}/following")
    suspend fun getFollowing(
        @Path("username") username: String
    ): Response<List<ItemsItem>>

}