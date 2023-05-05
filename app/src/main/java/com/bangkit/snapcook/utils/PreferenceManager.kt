package com.bangkit.snapcook.utils

import android.content.Context
import android.content.SharedPreferences
import com.bangkit.snapcook.utils.constant.AppConstants.KEY_TOKEN
import com.bangkit.snapcook.utils.constant.AppConstants.PREFS_NAME

class PreferenceManager(context: Context) {

    private var prefs: SharedPreferences =
        context.applicationContext.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    private val editor = prefs.edit()

    fun storeLoginData(token: String) {
        editor.putString(KEY_TOKEN, token)
        editor.apply()
    }

    fun clearAllPreferences() {
        editor.remove(KEY_TOKEN)
        editor.apply()
    }

    val getToken = prefs.getString(KEY_TOKEN, "") ?: ""
    val isLogin = getToken.isNotEmpty()

}