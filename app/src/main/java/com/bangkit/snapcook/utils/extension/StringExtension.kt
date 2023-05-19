package com.bangkit.snapcook.utils.extension

fun String.isEmailCorrect(): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.extractToMinutes(): Int {
    val components = this.split(" ")
    var minutes = 0
    for (i in components.indices) {
        when {
            components[i] == "jam" || components[i] == "hour" || components[i] == "hours" -> {
                val hours = components[i - 1].toIntOrNull()
                if (hours != null) {
                    minutes += hours * 60
                }
            }

            components[i] == "menit" || components[i] == "minute" || components[i] == "minutes" -> {
                val currentMinutes = components[i - 1].toIntOrNull()
                if (currentMinutes != null) {
                    minutes += currentMinutes
                }
            }
        }
    }
    return minutes
}