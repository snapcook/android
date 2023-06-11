package com.bangkit.snapcook.utils.helper

import com.bangkit.snapcook.data.model.Grocery
import com.bangkit.snapcook.data.model.Utensil

fun String.extractData(): Pair<String, String> {
    val pattern = "(\\d+\\s*[a-zA-Z]+)".toRegex()
    val match = pattern.find(this)
    val matchedValue = match?.value ?: ""
    val remainingText = this.replaceFirst(matchedValue, "").trim()
    return Pair(matchedValue, remainingText)
}

fun List<String>.convertToGrocery(groupId: String, type: String): List<Grocery> {
    val result = ArrayList<Grocery>()
    forEach { data ->
        result.add(
            Grocery(
                title = data,
                groupId = groupId,
                type = type
            ),
        )
    }
    return result
}

fun List<Utensil>.convertUtensilToGrocery(groupId: String, type: String): List<Grocery> {
    val result = ArrayList<Grocery>()
    forEach { data ->
        result.add(
            Grocery(
                title = data.name,
                groupId = groupId,
                type = type
            ),
        )
    }
    return result
}