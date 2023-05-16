package com.bangkit.snapcook.di

import com.bangkit.snapcook.BuildConfig
import com.bangkit.snapcook.data.network.HeaderInterceptor
import com.bangkit.snapcook.data.network.services.BookmarkService
import com.bangkit.snapcook.data.network.services.RecipeService
import com.bangkit.snapcook.data.network.services.UserService
import com.bangkit.snapcook.utils.PreferenceManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    /// Provide OKHttp Client for debugging
    single {
        return@single OkHttpClient.Builder()
            .addInterceptor(getHeaderInterceptor(get()))
            .addInterceptor(loggingInterceptor)
            .connectTimeout(120, TimeUnit.SECONDS)
            .readTimeout(120, TimeUnit.SECONDS)
            .build()
    }

    /// Provide Retrofit
    single {
        Retrofit.Builder()
            .baseUrl("")
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    /// Provide Services
    single { provideRecipeService(get()) }
    single { provideUserService(get()) }
    single { provideBookmarkService(get()) }
}

private val loggingInterceptor = if (BuildConfig.DEBUG) {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
} else {
    HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
}

private fun getHeaderInterceptor(preferenceManager: PreferenceManager): Interceptor {
    val headers = HashMap<String, String>()
    headers["Content-Type"] = "application/json"
    return HeaderInterceptor(headers, preferenceManager)
}

fun provideRecipeService(retrofit: Retrofit): RecipeService =
    retrofit.create(RecipeService::class.java)

fun provideUserService(retrofit: Retrofit): UserService =
    retrofit.create(UserService::class.java)
fun provideBookmarkService(retrofit: Retrofit): BookmarkService =
    retrofit.create(BookmarkService::class.java)