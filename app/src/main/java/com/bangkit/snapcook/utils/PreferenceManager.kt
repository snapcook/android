package com.bangkit.snapcook.utils

import android.content.Context
import android.content.SharedPreferences
import com.bangkit.snapcook.utils.constant.AppConstants.KEY_ON_BOARD
import com.bangkit.snapcook.utils.constant.AppConstants.KEY_TOKEN
import com.bangkit.snapcook.utils.constant.AppConstants.KEY_USER_ID
import com.bangkit.snapcook.utils.constant.AppConstants.PREFS_NAME

class PreferenceManager(context: Context) {

    private var prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    fun storeLoginData(token: String, userId: String) {
        editor.putString(KEY_TOKEN, token)
        editor.putString(KEY_USER_ID, userId)
        editor.apply()
    }

    fun markUserDoneOnboard() {
        editor.putString(KEY_ON_BOARD, "YES")
        editor.apply()
    }

    fun clearAllPreferences() {
        editor.remove(KEY_TOKEN)
        editor.remove(KEY_USER_ID)
        editor.apply()
    }

    val getToken = prefs.getString(KEY_TOKEN, "") ?: ""
    private val getOnBoardStatus = prefs.getString(KEY_ON_BOARD, "") ?: ""
    val getUserId = prefs.getString(KEY_USER_ID, "") ?: ""
    val isLogin = getToken.isNotEmpty()
    val isAlreadyOnBoard = getOnBoardStatus.isNotEmpty()
}