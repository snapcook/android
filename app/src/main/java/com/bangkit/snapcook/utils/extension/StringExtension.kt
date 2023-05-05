package com.bangkit.snapcook.utils.extension

fun String.isEmailCorrect(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}
