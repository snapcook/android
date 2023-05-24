package com.bangkit.snapcook.di.viewmodel

import com.bangkit.snapcook.presentation.add_recipe.AddRecipeViewModel
import com.bangkit.snapcook.presentation.bookmark.BookmarkViewModel
import com.bangkit.snapcook.presentation.detail.DetailRecipeViewModel
import com.bangkit.snapcook.presentation.home.HomeViewModel
import com.bangkit.snapcook.presentation.login.LoginViewModel
import com.bangkit.snapcook.presentation.register.RegisterViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { RegisterViewModel(get()) }
    single { LoginViewModel(get()) }
    single { AddRecipeViewModel(get(), get()) }
    single { HomeViewModel(get()) }
    single { DetailRecipeViewModel(get()) }
}