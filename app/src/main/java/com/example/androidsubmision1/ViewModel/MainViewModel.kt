package com.example.androidsubmision1.ViewModel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.androidsubmision1.core.API.ApiResponse
import com.example.androidsubmision1.core.domain.model.GithubResponseModel
import com.example.androidsubmision1.core.domain.model.ItemsItemModel
import com.example.androidsubmision1.core.domain.usecase.GithubUserUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class MainViewModel(private val githubUserUseCase: GithubUserUseCase) : ViewModel() {
    private val _userList = MutableLiveData<List<ItemsItemModel>>()
    val userList: LiveData<List<ItemsItemModel>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    var searchUser: String? = null

    init {
        viewModelScope.launch {
            getUserList(searchUser).collect { response ->
                if (response is ApiResponse.Success) {
                    _userList.postValue(response.data.items)
                }
            }
        }
    }

    fun refresh() {
        viewModelScope.launch {
            getUserList(searchUser).collect { response ->
                if (response is ApiResponse.Success) {
                    _userList.postValue(response.data.items)
                }
            }
        }
    }

    suspend fun getUserList(searchUser: String?): Flow<ApiResponse<GithubResponseModel>> =
        githubUserUseCase.getUserList(searchUser)
}
