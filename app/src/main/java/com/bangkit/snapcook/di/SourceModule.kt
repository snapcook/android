package com.bangkit.snapcook.di

import com.bangkit.snapcook.data.repository.AuthRepository
import com.bangkit.snapcook.data.repository.CategoryRepository
import com.bangkit.snapcook.data.repository.GroceryRepository
import com.bangkit.snapcook.data.repository.RecipeRepository
import com.bangkit.snapcook.data.repository.UserRepository
import com.bangkit.snapcook.data.source.AuthDataSource
import com.bangkit.snapcook.data.source.CategoryDataSource
import com.bangkit.snapcook.data.source.GroceryDataSource
import com.bangkit.snapcook.data.source.RecipeDataSource
import com.bangkit.snapcook.data.source.UserDataSource
import org.koin.dsl.module

val sourceModule = module {
    factory { AuthRepository(get()) }
    single { AuthDataSource(get(), get()) }

    factory { RecipeRepository(get()) }
    single { RecipeDataSource(get(), get()) }
    factory { UserRepository(get()) }
    single { UserDataSource(get(), get()) }

    factory { CategoryRepository(get()) }
    single { CategoryDataSource(get()) }

    factory { GroceryRepository(get()) }
    single { GroceryDataSource(get()) }
}
