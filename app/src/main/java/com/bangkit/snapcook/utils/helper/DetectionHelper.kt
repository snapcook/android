package com.bangkit.snapcook.utils.helper

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.Typeface
import com.bangkit.snapcook.ml.SnapcookV5
import org.tensorflow.lite.support.image.TensorImage

fun Bitmap.detectIngredient(context: Context, onSuccessDetect: (List<String>) -> Unit): Bitmap{
    val model = SnapcookV5.newInstance(context)

    // Creates inputs for reference.
    val image = TensorImage.fromBitmap(this)

    // Runs model inference and gets result.
    val outputs = model.process(image)
    val detectionResults = outputs.detectionResultList
    val mutableBitmap = this.copy(Bitmap.Config.ARGB_8888, true)
    val result = ArrayList<String>()

    for (detectionResult in detectionResults){
        val score = detectionResult.scoreAsFloat
        val location = detectionResult.locationAsRectF
        val category = detectionResult.categoryAsString

        if (score > 0.3f){
            val canvas = mutableBitmap?.let { Canvas(it) }
            val paint = Paint()

            if (!result.contains(category)){
                result.add(category)
            }

            paint.color = setColorBasedOnCategory(category)
            paint.style = Paint.Style.STROKE
            paint.strokeWidth = 2.0f
            paint.textSize = 32.0f
            paint.typeface = Typeface.SANS_SERIF
            paint.isAntiAlias = true

//            canvas?.drawRect(location, paint)
            val textBounds = Rect()
            paint.getTextBounds(category, 0, category.length, textBounds)
            val x = location.left
            val y = location.top - textBounds.height()
            canvas?.drawText(category, x, y, paint)
        }
    }

    // Releases model resources if no longer used.
    model.close()
    onSuccessDetect(result)

    return mutableBitmap
}

private fun setColorBasedOnCategory(category: String): Int{
    val fixedCategory = category.lowercase()
    if (fixedCategory == "banana") return Color.RED
    if (fixedCategory == "tempe") return Color.CYAN
    if (fixedCategory == "daging sapi") return Color.YELLOW
    if (fixedCategory == "carrot") return Color.GREEN
    if (fixedCategory == "apple") return Color.CYAN
    if (fixedCategory == "orange") return Color.GREEN
    if (fixedCategory == "egg") return Color.MAGENTA
    if (fixedCategory == "potato") return Color.RED
    if (fixedCategory == "chicken") return Color.MAGENTA
    if (fixedCategory == "tomato") return Color.YELLOW
    if (fixedCategory == "cabbage") return Color.YELLOW
    return Color.RED
}