package com.bangkit.snapcook.utils.helper

import com.bangkit.snapcook.utils.constant.AppConstants.MULTIPART_FILE_NAME
import com.bangkit.snapcook.utils.constant.AppConstants.MULTIPART_FORM_DATA
import com.bangkit.snapcook.utils.extension.toRequestBody
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File


fun File.toMultipart(): MultipartBody.Part {
    return MultipartBody.Part
        .createFormData(
            name = MULTIPART_FILE_NAME,
            filename = this.name,
            body = this.asRequestBody()
        )
}

fun String.toRequestBody(): RequestBody {
    return this.toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull())
}

fun List<String>.toRequestBody(): List<RequestBody> {
   val result = this.map {
       it.toRequestBody()
   }

    return result
}

private fun createPartFromArray(skills: Array<String>): Map<String, RequestBody>? {
    val skill: MutableMap<String, RequestBody> = HashMap()
    var requestFile: RequestBody
    for (i in skills.indices) {
        requestFile = RequestBody.create(MultipartBody.FORM, skills[i])
        skill["skill[$i]"] = requestFile
    }
    return skill
}

fun Float?.toRequestBody(): RequestBody? {
    if (this == null) return null
    return this.toString().toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull())
}

fun Int?.toRequestBody(): RequestBody? {
    if (this == null) return null
    return this.toString().toRequestBody(MULTIPART_FORM_DATA.toMediaTypeOrNull())
}