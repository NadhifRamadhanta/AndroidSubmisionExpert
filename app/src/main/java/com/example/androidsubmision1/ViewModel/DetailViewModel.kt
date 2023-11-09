package com.example.androidsubmision1.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsubmision1.core.API.ApiResponse
import com.example.androidsubmision1.core.domain.model.ItemsItemModel
import com.example.androidsubmision1.core.domain.model.ProfileResponseModel
import com.example.androidsubmision1.core.domain.usecase.GithubUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class DetailViewModel(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {

    private val _detailUser = MutableLiveData<ProfileResponseModel>()
    val detailUser: LiveData<ProfileResponseModel> = _detailUser

    private val _followersUser = MutableLiveData<List<ItemsItemModel>>()
    val followersUser: LiveData<List<ItemsItemModel>> = _followersUser

    private val _followingUser = MutableLiveData<List<ItemsItemModel>>()
    val followingUser: LiveData<List<ItemsItemModel>> = _followingUser

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var username: String? = null

    suspend fun getUser(username: String) {
        if (!username.isNullOrBlank()) {
            viewModelScope.launch {
                getDetailUser(username).collect { response ->
                    _isLoading.postValue(true)
                    if (response is ApiResponse.Success) {
                        _isLoading.postValue(false)
                        _detailUser.postValue(response.data!!)
                    }
                }
            }
        }
    }

    suspend fun getFollow(username: String) {
        viewModelScope.launch {
            getFollowers(username).collect { response ->
                if (response is ApiResponse.Success) {
                    _followersUser.postValue(response.data!!)
                }
            }
            getFollowing(username).collect { response ->
                if (response is ApiResponse.Success) {
                    _followingUser.postValue(response.data!!)
                }
            }
        }
    }

    suspend fun getDetailUser(username: String): Flow<ApiResponse<ProfileResponseModel>> =
        githubUserUseCase.getDetailUser(username)

    suspend fun getFollowers(username: String): Flow<ApiResponse<List<ItemsItemModel>>> =
        githubUserUseCase.getFollowers(username)

    suspend fun getFollowing(username: String): Flow<ApiResponse<List<ItemsItemModel>>> =
        githubUserUseCase.getFollowing(username)
}
