package com.example.androidsubmision1.core.domain.usecase

import com.example.androidsubmision1.core.domain.repository.IGithubUserRepository

class GithubUserInteractor(private val githubUserRepository: IGithubUserRepository) :GithubUserUseCase {

    override suspend fun getUserList(searchUser: String?) = githubUserRepository.getUserList(searchUser)

    override suspend fun getDetailUser(username : String) = githubUserRepository.getDetailUser(username)

    override suspend fun getFollowers(username : String) = githubUserRepository.getFollowers(username)

    override suspend fun getFollowing(username : String) = githubUserRepository.getFollowing(username)
}