package com.bangkit.snapcook

import android.app.Application
import com.bangkit.snapcook.di.localModule
import com.bangkit.snapcook.di.networkModule
import com.bangkit.snapcook.di.preferenceModule
import com.bangkit.snapcook.di.sourceModule
import com.bangkit.snapcook.di.viewmodel.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber
class BaseApp : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@BaseApp)
            modules(
                listOf(
                    networkModule,
                    localModule,
                    preferenceModule,
                    viewModelModule,
                    sourceModule
                )
            )
        }
    }
}