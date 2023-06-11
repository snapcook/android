package com.bangkit.snapcook.data.source

import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.network.request.LoginRequest
import com.bangkit.snapcook.data.network.request.RegisterRequest
import com.bangkit.snapcook.data.network.services.UserService
import com.bangkit.snapcook.di.networkModule
import com.bangkit.snapcook.di.preferenceModule
import com.bangkit.snapcook.utils.PreferenceManager
import com.bangkit.snapcook.utils.helper.createResponse
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.context.loadKoinModules
import org.koin.core.context.unloadKoinModules
import timber.log.Timber

class AuthDataSource(
    private val service: UserService,
    private val pref: PreferenceManager
) {
    suspend fun register(
        request: RegisterRequest
    ): Flow<ApiResponse<String>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                service.register(request)
                emit(ApiResponse.Success("Success"))
            } catch (e: Exception) {
//                emit(ApiResponse.Error(e.message.toString()))
                Timber.d("Error is ${e.message}")
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }

    suspend fun login(
        request: LoginRequest
    ): Flow<ApiResponse<String>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val response = service.login(request)
//                if (response.error) {
//                    emit(ApiResponse.Error(response.message))
//                    return@flow
//                }
                pref.storeLoginData(response.token, response.user.id)
                reloadModule()
                emit(ApiResponse.Success("SUCCESS"))
            } catch (e: Exception) {
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }

    /**
     * reloadModule() is a required to remove previous preference and network module and
     * replace it with newer one. It is only required in login to insert or renew token.
     * */
    private fun reloadModule() {
        unloadKoinModules(preferenceModule)
        loadKoinModules(preferenceModule)

        unloadKoinModules(networkModule)
        loadKoinModules(networkModule)
    }
}