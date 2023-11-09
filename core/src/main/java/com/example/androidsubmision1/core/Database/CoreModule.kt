package com.example.androidsubmission1.core.Database

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.room.Room
import com.example.androidsubmision1.core.API.ApiService
import com.example.androidsubmision1.core.domain.repository.IGithubUserRepository
import com.example.androidsubmision1.core.domain.repository.ISettingRepository
import com.example.androidsubmision1.core.preference.SettingPreference
import com.example.androidsubmision1.core.remote.GithubUserRepository
import com.example.androidsubmission1.core.BuildConfig

import com.example.androidsubmision1.core.domain.repository.IFavoriteRepository
import com.example.androidsubmission1.core.utils.AppExecutors
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val databaseModule = module {
    factory(override = true) { get<FavoriteRoomDatabase>().favoriteDao() }
    single {
        Room.databaseBuilder(
            androidContext(),
            FavoriteRoomDatabase::class.java, "favoriteUser"
        ).fallbackToDestructiveMigration().build()
    }
}

val networkModule = module {
    val loggingInterceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS).build()
    }

    single {
        val retrofit = Retrofit.Builder().baseUrl(BuildConfig.URL)
            .addConverterFactory(GsonConverterFactory.create()).client(get()).build()
        retrofit.create(ApiService::class.java)
    }
}

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

val repositoryModule = module {
    single { GithubUserRepository() }
    single { FavoriteRepository(get(), get()) }
    single {
        val context: Context = androidContext()
        context.dataStore
    }
    factory(override = true) { AppExecutors() }
    single<IGithubUserRepository> { GithubUserRepository() }
    single<IFavoriteRepository> { FavoriteRepository(get(), get()) }
    single<ISettingRepository> { SettingPreference.getInstance(get()) }
}