package com.bangkit.snapcook.di.viewmodel

import com.bangkit.snapcook.presentation.add_recipe.AddRecipeViewModel
import com.bangkit.snapcook.presentation.add_to_grocery.AddToGroceryViewModel
import com.bangkit.snapcook.presentation.bookmark.BookmarkViewModel
import com.bangkit.snapcook.presentation.category.CategoryViewModel
import com.bangkit.snapcook.presentation.detail.DetailRecipeViewModel
import com.bangkit.snapcook.presentation.edit_profile.EditProfileViewModel
import com.bangkit.snapcook.presentation.home.HomeViewModel
import com.bangkit.snapcook.presentation.login.LoginViewModel
import com.bangkit.snapcook.presentation.my_recipe.MyRecipeViewModel
import com.bangkit.snapcook.presentation.note.NoteViewModel
import com.bangkit.snapcook.presentation.note_detail.NoteDetailViewModel
import com.bangkit.snapcook.presentation.profile.ProfileViewModel
import com.bangkit.snapcook.presentation.recommended.RecommendedViewModel
import com.bangkit.snapcook.presentation.register.RegisterViewModel
import com.bangkit.snapcook.presentation.search.SearchRecipeViewModel
import org.koin.dsl.module

val viewModelModule = module {
    single { RegisterViewModel(get()) }
    single { LoginViewModel(get()) }
    single { AddRecipeViewModel(get(), get()) }
    single { HomeViewModel(get(), get(), get()) }
    single { DetailRecipeViewModel(get(), get(), get()) }
    single { BookmarkViewModel(get()) }
    single { SearchRecipeViewModel(get(), get()) }
    single { RecommendedViewModel(get(), get()) }
    single { NoteViewModel(get()) }
    single { NoteDetailViewModel(get()) }
    single { ProfileViewModel(get()) }
    single { MyRecipeViewModel(get()) }
    single { EditProfileViewModel(get()) }
    single { AddToGroceryViewModel(get()) }
    single { CategoryViewModel(get(), get())}
}