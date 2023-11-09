package com.example.androidsubmision1.core.remote

import android.util.Log
import com.example.androidsubmision1.core.API.ApiConfig
import com.example.androidsubmision1.core.API.ApiResponse
import com.example.androidsubmision1.core.domain.model.GithubResponseModel
import com.example.androidsubmision1.core.domain.model.ItemsItemModel
import com.example.androidsubmision1.core.domain.model.ProfileResponseModel
import com.example.androidsubmision1.core.domain.repository.IGithubUserRepository
import com.example.androidsubmision1.core.utils.DataMapper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GithubUserRepository : IGithubUserRepository {

    override suspend fun getUserList(searchUser: String?): Flow<ApiResponse<GithubResponseModel>> {
        return flow {
//            _isLoading.postValue(true)
            try {
                val response = ApiConfig.getApiService().getUsers(searchUser ?: USER_Q)
                if (response.isSuccessful && response.body() != null) {
                    val model = DataMapper.mapGithubToGithubModel(response.body()!!)
                    emit(ApiResponse.Success(model))
                    Log.d(TAG, "tes sukses: ${response.message()}")
                } else {
                    emit(ApiResponse.Error("onFailureResponse: ${response.message()}"))
                    Log.e(TAG, "onFailureResponse: ${response.message()}")

                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
//            finally {
//                _isLoading.postValue(false)
//            }
        }.flowOn(Dispatchers.IO)
    }


    override suspend fun getDetailUser(username: String): Flow<ApiResponse<ProfileResponseModel>> {
        return flow {
//            _isLoading.postValue(true)
            try {
                val response = ApiConfig.getApiService().getProfile(username)
                if (response.isSuccessful && response.body() != null) {
                    val model =
                        DataMapper.mapProfileResponseToProfileResponseModel(response.body()!!)
                    emit(ApiResponse.Success(model))
                } else {
                    emit(ApiResponse.Error("Failed to fetch profile"))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
//            finally {
//                _isLoading.postValue(false)
//            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getFollowers(username: String): Flow<ApiResponse<List<ItemsItemModel>>> {
        return flow {
//            _isLoading.postValue(true)
            try {
                val response = ApiConfig.getApiService().getFollowers(username)
                if (response.isSuccessful) {
                    val model =
                        response.body()!!.map {
                            DataMapper.mapItemsItemToItemsItemModel(it)
                        }
                    emit(ApiResponse.Success<List<ItemsItemModel>>(model))
                } else {
                    emit(ApiResponse.Error("Failed to fetch followers"))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
//            finally {
//                _isLoading.postValue(false)
//            }
        }.flowOn(Dispatchers.IO)
    }

    override suspend fun getFollowing(username: String): Flow<ApiResponse<List<ItemsItemModel>>> {
        return flow {
//            _isLoading.postValue(true)
            try {
                val response = ApiConfig.getApiService().getFollowing(username)
                if (response.isSuccessful) {
                    val model =
                        response.body()!!.map {
                            DataMapper.mapItemsItemToItemsItemModel(it)
                        }
                    emit(ApiResponse.Success<List<ItemsItemModel>>(model))
                } else {
                    emit(ApiResponse.Error("Failed to fetch followers"))
                }
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.toString()))
            }
//            finally {
//                _isLoading.postValue(false)
//            }
        }.flowOn(Dispatchers.IO)
    }


    companion object {
        private const val TAG = "MainViewModel"
        private const val USER_Q = "a"
    }

}