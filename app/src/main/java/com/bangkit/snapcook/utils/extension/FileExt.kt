package com.bangkit.snapcook.utils.extension

import android.content.ContentResolver
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.text.TextUtils
import timber.log.Timber
import java.io.*

const val BUFFER_SIZE: Int = 1024 * 2

fun Context.getImageUri(img: Bitmap): Uri? {
    val bytes = ByteArrayOutputStream()
    img.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
    val path = MediaStore.Images.Media.
    insertImage(this.contentResolver, img, "Title", null)
    return Uri.parse(path)
}

fun Uri.uriToBitmap(context: Context): Bitmap? {
    var bitmap: Bitmap? = null
    try {
        val contentResolver: ContentResolver = context.contentResolver
        val inputStream = contentResolver.openInputStream(this)
        bitmap = BitmapFactory.decodeStream(inputStream)
        inputStream?.close()
    } catch (e: Exception) {
        e.printStackTrace()
    }
    return bitmap
}

fun Context.checkIfImageTooSmall(contentUri: Uri): Boolean{
    val inputStream = contentResolver.openInputStream(contentUri)
    val options = BitmapFactory.Options().apply {
        inJustDecodeBounds = true
    }
    BitmapFactory.decodeStream(inputStream, null, options)
    inputStream?.close()

    val imageWidth = options.outWidth
    val imageHeight = options.outHeight

    val minimumWidth = 600
    val minimumHeight = 600

    return imageWidth < minimumWidth || imageHeight < minimumHeight

}
fun Context.getFileFromUri(contentUri: Uri?): File? {
    val fileName: String = getFileName(contentUri) ?: ""
    Timber.d("FILE NAME FROM URI $fileName")
    Timber.d("FILE NAME FROM URI ${contentUri?.path}")

    val dir = File(
        this.externalCacheDir.toString()
    )
    if (!dir.exists()) {
        dir.mkdirs()
    }
    if (!TextUtils.isEmpty(fileName)) {
        val copyFile = File(dir.toString() + File.separator + fileName)
        copy(this, contentUri, copyFile)
        return copyFile
    }
    return null
}

private fun getFileName(uri: Uri?): String? {
    if (uri == null) return null
    var fileName: String? = null
    val path = uri.path
    val cut = path!!.lastIndexOf('/')
    if (cut != -1) {
        fileName = path.substring(cut + 1)
    }
    return "$fileName.jpg"
}


private fun copy(context: Context, srcUri: Uri?, dstFile: File?) {
    try {
        val inputStream = context.contentResolver.openInputStream(srcUri!!) ?: return
        val outputStream: OutputStream = FileOutputStream(dstFile)
        copyStream(inputStream, outputStream)
        inputStream.close()
        outputStream.close()
    } catch (e: IOException) {
        e.printStackTrace()
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

@Throws(java.lang.Exception::class, IOException::class)
private fun copyStream(input: InputStream?, output: OutputStream?): Int {
    val buffer = ByteArray(BUFFER_SIZE)
    val `in` = BufferedInputStream(input, BUFFER_SIZE)
    val out = BufferedOutputStream(output, BUFFER_SIZE)
    var count = 0
    var n: Int
    try {
        while (`in`.read(buffer, 0, BUFFER_SIZE).also { n = it } != -1) {
            out.write(buffer, 0, n)
            count += n
        }
        out.flush()
    } finally {
        try {
            out.close()
        } catch (e: IOException) {
            Timber.e(e.toString())
        }
        try {
            `in`.close()
        } catch (e: IOException) {
            Timber.e(e.toString())
        }
    }
    return count
}

