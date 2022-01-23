package com.habibi.find

import android.app.Application
import com.habibi.core.di.databaseModule
import com.habibi.core.di.networkModule
import com.habibi.core.di.repositoryModule
import com.habibi.find.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class FindApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(org.koin.core.logger.Level.NONE)
            androidContext(this@FindApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    viewModelModule
                )
            )
        }
    }
}