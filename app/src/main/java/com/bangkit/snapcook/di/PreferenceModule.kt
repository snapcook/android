package com.bangkit.snapcook.di

import com.bangkit.snapcook.utils.PreferenceManager
import org.koin.dsl.module

val preferenceModule = module {

    single { PreferenceManager(get()) }

}