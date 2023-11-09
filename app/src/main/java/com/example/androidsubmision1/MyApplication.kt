package com.example.androidsubmision1

import android.app.Application
import com.example.androidsubmission1.core.Database.databaseModule
import com.example.androidsubmission1.core.Database.networkModule
import com.example.androidsubmission1.core.Database.repositoryModule
import com.example.androidsubmision1.di.useCaseModule
import com.example.androidsubmision1.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule,

                )
            )
        }
    }
}