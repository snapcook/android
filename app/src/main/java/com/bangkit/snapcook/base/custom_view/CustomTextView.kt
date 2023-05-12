package com.bangkit.snapcook.base.custom_view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import com.bangkit.snapcook.R

class IngredientTextView(context: Context, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatTextView(context, attrs) {

    private var prefixText = ""
    private val prefixPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        attrs?.let {
            val typedArray = context.obtainStyledAttributes(it, R.styleable.IngredientTextView)
            prefixText = typedArray.getString(R.styleable.IngredientTextView_ingredientText) ?: ""
            typedArray.recycle()
        }
        prefixPaint.typeface = typeface
        prefixPaint.textSize = textSize
        prefixPaint.color = currentTextColor
        prefixPaint.textAlign = Paint.Align.LEFT
    }

    fun setPrefixText(prefix: String) {
        prefixText = prefix
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            val prefixWidth = prefixPaint.measureText(prefixText)
            it.drawText(prefixText, 0f, baseline.toFloat(), prefixPaint)
            it.drawText(text.toString(), prefixWidth, baseline.toFloat(), paint)
        }
    }

    override fun setText(text: CharSequence?, type: BufferType?) {
        super.setText(text, type)
        invalidate()
    }
}