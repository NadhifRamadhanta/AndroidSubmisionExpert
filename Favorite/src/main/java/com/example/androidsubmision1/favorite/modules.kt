package com.example.androidsubmision1.favorite

import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val module = module{
    viewModel { FavoriteViewModelDynamicFeatures(get()) }
}