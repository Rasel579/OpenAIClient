package com.ai.onlineai.ui.screens

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.drawable.Drawable
import com.ai.onlineai.models.NetModelResponse


class OverPreviewDrawable(model: NetModelResponse) : Drawable() {

    private val contentTextPaint = Paint().apply {
        color = Color.DKGRAY
        alpha = 255
        textSize = 80F
    }
    private val contentRectPaint = Paint().apply {
        style = Paint.Style.STROKE
        color = Color.YELLOW
        strokeWidth = 10F
        alpha = 200
    }

    private val model = model

    override fun draw(canvas: Canvas) {
        canvas.drawText(model.name, model.topX.toFloat()*5,
                        model.topY.toFloat()*5,
                        contentTextPaint)

        canvas.drawRect(
            Rect(
                Math.abs(model.topX.toInt()) * 5,
                model.topY.toInt() * 5,
                model.bottomX.toInt() * 5,
                model.bottomY.toInt() * 5 ),
            contentRectPaint
        )
    }

    override fun setAlpha(alpha: Int) {
        contentTextPaint.alpha = alpha
    }

    override fun setColorFilter(colorFilter: ColorFilter?) {
        contentTextPaint.colorFilter = colorFilter
    }

    override fun getOpacity(): Int = PixelFormat.TRANSLUCENT

}