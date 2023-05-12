package com.bangkit.snapcook.di

import android.app.Application
import androidx.room.Room
import com.bangkit.snapcook.BuildConfig
import com.bangkit.snapcook.data.local.room.SnapcookDatabase
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

val localModule = module {

    ///Provide Dao
    factory { get<SnapcookDatabase>().getGroceryDao() }

    ///Provide Database
    fun provideDatabase(application: Application): SnapcookDatabase {
        return Room.databaseBuilder(application, SnapcookDatabase::class.java, BuildConfig.DB_NAME)
            .fallbackToDestructiveMigration()
            .build()
    }

    ///Provide context for database
    single { provideDatabase(androidApplication()) }
}