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
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import okhttp3.CertificatePinner
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
        val passphrase: ByteArray = SQLiteDatabase.getBytes("user-github".toCharArray())
        val factory = SupportFactory(passphrase)
        Room.databaseBuilder(
            androidContext(),
            FavoriteRoomDatabase::class.java, "favoriteUser"
        ).fallbackToDestructiveMigration().openHelperFactory(factory).build()
    }
}

val networkModule = module {
    val loggingInterceptor = if (BuildConfig.DEBUG) {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    } else {
        HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
    }

    single {
        val hostname = "api.github.com"
        val certificatePinner = CertificatePinner.Builder()
            .add(hostname, "sha256/ jFaeVpA8UQuidlJkkpIdq3MPwD0m8XbuCRbJlezysBE=")
            .add(hostname, "sha256/ Jg78dOE+fydIGk19swWwiypUSR6HWZybfnJG/8G7pyM=")
            .build()

        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS).readTimeout(120, TimeUnit.SECONDS).certificatePinner(certificatePinner).build()
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