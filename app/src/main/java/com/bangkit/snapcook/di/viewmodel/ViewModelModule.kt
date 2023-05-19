package com.bangkit.snapcook.di.viewmodel

import com.bangkit.snapcook.presentation.add_recipe.AddRecipeViewModel
import com.bangkit.snapcook.presentation.login.LoginViewModel
import com.bangkit.snapcook.presentation.register.RegisterViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { RegisterViewModel(get()) }
    single { LoginViewModel(get()) }
    single { AddRecipeViewModel(get()) }

}