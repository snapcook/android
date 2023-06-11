package com.bangkit.snapcook.utils.extension

fun Int.toHourAndMinutes(): String {
    val hours = this / 60
    val remainingMinutes = this % 60

    val formattedDuration = StringBuilder()
    if (hours > 0) {
        formattedDuration.append(hours).append(" hour")
        if (hours > 1) {
            formattedDuration.append("s")
        }
    }
    if (remainingMinutes > 0) {
        if (hours > 0) {
            formattedDuration.append(" ")
        }
        formattedDuration.append(remainingMinutes).append(" minute")
        if (remainingMinutes > 1) {
            formattedDuration.append("s")
        }
    }
    return formattedDuration.toString()
}

