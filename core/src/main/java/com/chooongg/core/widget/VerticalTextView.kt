package com.chooongg.core.widget

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Path
import android.graphics.Rect
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.chooongg.core.R
import kotlin.math.min

class VerticalTextView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatTextView(context, attrs, defStyleAttr) {

    companion object {
        const val ORIENTATION_UP_TO_DOWN = 0
        const val ORIENTATION_DOWN_TO_UP = 1
        const val ORIENTATION_LEFT_TO_RIGHT = 2
        const val ORIENTATION_RIGHT_TO_LEFT = 3
    }

    private var textBounds = Rect()
    private var direction = 0

    private val path = Path()

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.VerticalTextView, defStyleAttr, 0)
        direction = a.getInt(R.styleable.VerticalTextView_direction, 0)
        a.recycle()
        requestLayout()
        invalidate()
    }

    fun setDirection(direction: Int) {
        this.direction = direction
        requestLayout()
        invalidate()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        paint.getTextBounds(
            text.toString(), 0, text.length,
            textBounds
        )
        if (direction == ORIENTATION_LEFT_TO_RIGHT
            || direction == ORIENTATION_RIGHT_TO_LEFT
        ) {
            setMeasuredDimension(
                measureHeight(widthMeasureSpec),
                measureWidth(heightMeasureSpec)
            )
        } else if (direction == ORIENTATION_UP_TO_DOWN
            || direction == ORIENTATION_DOWN_TO_UP
        ) {
            setMeasuredDimension(
                measureWidth(widthMeasureSpec),
                measureHeight(heightMeasureSpec)
            )
        }
    }

    private fun measureWidth(measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = textBounds.height() + paddingTop + paddingBottom
            if (specMode == MeasureSpec.AT_MOST) {
                result = min(result, specSize)
            }
        }
        return result
    }

    private fun measureHeight(measureSpec: Int): Int {
        var result: Int
        val specMode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize
        } else {
            result = textBounds.width() + paddingLeft + paddingRight
            if (specMode == MeasureSpec.AT_MOST) {
                result = min(result, specSize)
            }
        }
        return result
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        canvas.save()
        val startX: Int
        val startY: Int
        val stopX: Int
        val stopY: Int
        path.reset()
        when (direction) {
            ORIENTATION_UP_TO_DOWN -> {
                startX = width - textBounds.height() shr 1
                startY = height - textBounds.width() shr 1
                stopX = width - textBounds.height() shr 1
                stopY = height + textBounds.width() shr 1
                path.moveTo(startX.toFloat(), startY.toFloat())
                path.lineTo(stopX.toFloat(), stopY.toFloat())
            }
            ORIENTATION_DOWN_TO_UP -> {
                startX = width + textBounds.height() shr 1
                startY = height + textBounds.width() shr 1
                stopX = width + textBounds.height() shr 1
                stopY = height - textBounds.width() shr 1
                path.moveTo(startX.toFloat(), startY.toFloat())
                path.lineTo(stopX.toFloat(), stopY.toFloat())
            }
            ORIENTATION_LEFT_TO_RIGHT -> {
                startX = width - textBounds.width() shr 1
                startY = height + textBounds.height() shr 1
                stopX = width + textBounds.width() shr 1
                stopY = height + textBounds.height() shr 1
                path.moveTo(startX.toFloat(), startY.toFloat())
                path.lineTo(stopX.toFloat(), stopY.toFloat())
            }
            ORIENTATION_RIGHT_TO_LEFT -> {
                startX = width + textBounds.width() shr 1
                startY = height - textBounds.height() shr 1
                stopX = width - textBounds.width() shr 1
                stopY = height - textBounds.height() shr 1
                path.moveTo(startX.toFloat(), startY.toFloat())
                path.lineTo(stopX.toFloat(), stopY.toFloat())
            }
        }
        this.paint.color = this.currentTextColor
        canvas.drawTextOnPath(text.toString(), path, 0f, 0f, this.paint)
        canvas.restore()
    }
}