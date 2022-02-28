package com.chooongg.utils

import android.content.Context
import android.graphics.BlurMaskFilter
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.text.style.*
import androidx.annotation.IntDef
import androidx.annotation.StringDef
import androidx.annotation.StyleRes

class SpanUtils(val content: CharSequence) {

    val styles = mutableListOf(mutableListOf<CharacterStyle>())
    val textConstructor = mutableListOf(content)

    /**
     * 追加内容对象
     */
    fun append(nextVal: SpanUtils): SpanUtils {
        styles.addAll(nextVal.styles)
        textConstructor.addAll(nextVal.textConstructor)
        return this
    }

    /**
     * 追加内容文本
     */
    fun append(content: CharSequence): SpanUtils {
        return SpanUtils(content)
    }

    operator fun plus(nextVal: SpanUtils) = append(nextVal)

    operator fun plus(content: CharSequence) = append(content)

    /**
     * 设置背景色
     */
    fun setBackgroundColor(colorVal: Int) {
        styles[0].add(BackgroundColorSpan(colorVal))
    }

    /**
     * 设置前景色
     */
    fun setForegroundColor(colorVal: Int) {
        styles[0].add(ForegroundColorSpan(colorVal))
    }

    /**
     * 设置下划线
     */
    fun setUnderline() {
        styles[0].add(UnderlineSpan())
    }

    /**
     * 设置删除线
     */
    fun setStrikethrough() {
        styles[0].add(StrikethroughSpan())
    }

    /**
     * 设置模糊模糊
     */
    @JvmOverloads
    fun setBlurMask(radius: Float, style: BlurMaskFilter.Blur = BlurMaskFilter.Blur.NORMAL) {
        styles[0].add(MaskFilterSpan(BlurMaskFilter(radius, style)))
    }

    /**
     * 设置点击事件
     */
    fun setClickable(clickableSpan: ClickableSpan) {
        styles[0].add(clickableSpan)
    }

    /**
     * 设置下标
     */
    fun setSubscript() {
        styles[0].add(SubscriptSpan())
    }

    /**
     * 设置上标
     */
    fun setSuperscript() {
        styles[0].add(SuperscriptSpan())
    }

    /**
     * 设置字体绝对大小
     */
    @JvmOverloads
    fun setTextSizeAbsolute(size: Int, dip: Boolean = true) {
        styles[0].add(AbsoluteSizeSpan(size, dip))
    }

    /**
     * 设置字体相对大小
     */
    fun setTextSizeRelative(proportion: Float) {
        styles[0].add(RelativeSizeSpan(proportion))
    }

    /**
     * 设置字符水平方向缩放比例
     */
    fun setScaleX(proportion: Float) {
        styles[0].add(ScaleXSpan(proportion))
    }

    @IntDef(Typeface.NORMAL, Typeface.BOLD, Typeface.ITALIC, Typeface.BOLD_ITALIC)
    annotation class TextStyle

    /**
     * 设置字体风格
     */
    fun setTextStyle(@TextStyle style: Int) {
        styles[0].add(StyleSpan(style))
    }

    @StringDef("monospace", "serif", "sans-serif")
    annotation class TypeFace

    /**
     * 设置字体
     */
    fun setTypeface(@TypeFace typeface: String) {
        styles[0].add(TypefaceSpan(typeface))
    }

    /**
     * 设置字体外观(styleRes)
     */
    fun setTextAppearance(context: Context, @StyleRes appearance: Int) {
        styles[0].add(TextAppearanceSpan(context, appearance))
    }

    /**
     * 设置边框
     */
    fun setFrame(colorVal: Int) {
        styles[0].add(FrameSpan(colorVal))
    }

    /**
     * 设置图片
     */
    fun setImage(drawable: Drawable) {
        styles[0].add(VerticalImageSpan(drawable))
    }

    /**
     * 设置URL,可点击
     */
    fun setURL() {
        styles[0].add(URLSpan(content.toString()))
    }

    /**
     * 边框效果实现
     */
    class FrameSpan(colorVal: Int) : ReplacementSpan() {

        private val mPaint: Paint = Paint()
        private var mWidth: Int = 0

        init {
            mPaint.style = Paint.Style.STROKE
            mPaint.color = colorVal
            mPaint.isAntiAlias = true
        }

        override fun getSize(
            paint: Paint,
            text: CharSequence,
            start: Int,
            end: Int,
            fm: Paint.FontMetricsInt?
        ): Int {
            mWidth = paint.measureText(text, start, end).toInt()
            return mWidth
        }

        override fun draw(
            canvas: Canvas,
            text: CharSequence,
            start: Int,
            end: Int,
            x: Float,
            top: Int,
            y: Int,
            bottom: Int,
            paint: Paint
        ) {
            canvas.drawRect(x, top.toFloat(), x + mWidth, bottom.toFloat(), mPaint)
            canvas.drawText(text, start, end, x, y.toFloat(), paint)
        }
    }

    /**
     * 居中对齐的图片
     */
    class VerticalImageSpan(drawable: Drawable) : ImageSpan(drawable) {

        override fun getSize(
            paint: Paint,
            text: CharSequence,
            start: Int,
            end: Int,
            fontMetricsInt: Paint.FontMetricsInt?
        ): Int {
            val rect = drawable.bounds
            if (fontMetricsInt != null) {
                val fmPaint = paint.fontMetricsInt
                val fontHeight = fmPaint.bottom - fmPaint.top
                val drHeight = rect.bottom - rect.top
                val top = drHeight / 2 - fontHeight / 4
                val bottom = drHeight / 2 + fontHeight / 4
                fontMetricsInt.ascent = -bottom
                fontMetricsInt.top = -bottom
                fontMetricsInt.bottom = top
                fontMetricsInt.descent = top
            }
            return rect.right
        }

        override fun draw(
            canvas: Canvas,
            text: CharSequence,
            start: Int,
            end: Int,
            x: Float,
            top: Int,
            y: Int,
            bottom: Int,
            paint: Paint
        ) {
            canvas.save()
            val transY = (bottom - top - drawable.bounds.bottom) / 2 + top
            canvas.translate(x, transY.toFloat())
            drawable.draw(canvas)
            canvas.restore()
        }
    }
}