package com.bangkit.snapcook.data.source

import com.bangkit.snapcook.data.model.User
import com.bangkit.snapcook.data.network.ApiResponse
import com.bangkit.snapcook.data.network.services.UserService
import com.bangkit.snapcook.utils.PreferenceManager
import com.bangkit.snapcook.utils.helper.createResponse
import com.bangkit.snapcook.utils.helper.toMultipart
import com.bangkit.snapcook.utils.helper.toRequestBody
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import timber.log.Timber
import java.io.File

class UserDataSource(
    private val service: UserService,
    private val pref: PreferenceManager,
) {

    suspend fun fetchProfile(): Flow<ApiResponse<User>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val userId = pref.getUserId
                val response = service.fetchProfile(userId)

                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                Timber.e(e.message)
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }

    suspend fun editProfile(name: String?, photo: File?): Flow<ApiResponse<User>> {
        return flow {
            try {
                emit(ApiResponse.Loading)
                val userId = pref.getUserId
                val response =
                    service.editProfile(userId, photo?.toMultipart(), name?.toRequestBody())
                emit(ApiResponse.Success(response))
            } catch (e: Exception) {
                Timber.e(e.message)
                emit(ApiResponse.Error(e.createResponse()?.message ?: ""))
            }
        }
    }
}