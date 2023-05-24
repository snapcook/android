package com.bangkit.snapcook.di

import com.bangkit.snapcook.data.repository.AuthRepository
import com.bangkit.snapcook.data.repository.CategoryRepository
import com.bangkit.snapcook.data.repository.RecipeRepository
import com.bangkit.snapcook.data.source.AuthDataSource
import com.bangkit.snapcook.data.source.CategoryDataSource
import com.bangkit.snapcook.data.source.RecipeDataSource
import org.koin.dsl.module

val sourceModule = module {
    factory { AuthRepository(get()) }
    single { AuthDataSource(get(), get()) }

    factory { RecipeRepository(get()) }
    single { RecipeDataSource(get(), get()) }

    factory { CategoryRepository(get()) }
    single { CategoryDataSource(get()) }
}
