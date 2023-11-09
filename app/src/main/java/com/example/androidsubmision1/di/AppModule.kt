package com.example.androidsubmision1.di

import com.example.androidsubmision1.ViewModel.DetailViewModel
import com.example.androidsubmision1.ViewModel.FavoriteViewModel
import com.example.androidsubmision1.ViewModel.MainViewModel
import com.example.androidsubmision1.ViewModel.SettingViewModel
import com.example.androidsubmision1.core.domain.usecase.GithubUserInteractor
import com.example.androidsubmision1.core.domain.usecase.GithubUserUseCase
import com.example.androidsubmision1.core.domain.usecase.SettingInteractor
import com.example.androidsubmision1.core.domain.usecase.SettingUseCase
import com.example.androidsubmision1.core.domain.usecase.FavoriteInteractor
import com.example.androidsubmision1.core.domain.usecase.FavoriteUseCase
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<GithubUserUseCase> { GithubUserInteractor(get()) }
    factory<FavoriteUseCase> { FavoriteInteractor(get()) }
    factory<SettingUseCase> { SettingInteractor(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteViewModel(get()) }
    viewModel { SettingViewModel(get()) }
}