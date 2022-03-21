package com.chooongg.core.widget.autoSize

import android.text.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import com.chooongg.core.R
import com.chooongg.ext.getScreenDisplayMetrics
import com.chooongg.ext.getScreenScaledDensity

class AutoSizeTextHelper private constructor(private val textView: TextView) {

    interface OnTextSizeChangeListener {
        fun onTextSizeChange(textSize: Float, oldTextSize: Float)
    }

    private val paint = TextPaint()

    var rawTextSize = textView.textSize
        set(value) {
            if (isInAutoSize) return
            field = value
            autoSize()
        }

    var maxLines = textView.maxLines
        set(value) {
            field = value
            autoSize()
        }

    var minTextSize = getScreenScaledDensity() * DEFAULT_MIN_TEXT_SIZE
        set(value) {
            field = value
            autoSize()
        }

    var maxTextSize = rawTextSize
        set(value) {
            field = value
            autoSize()
        }

    var precision = DEFAULT_PRECISION
        set(value) {
            field = value
            autoSize()
        }

    var isEnabled: Boolean = false
        set(value) {
            if (field != value) {
                field = value
                if (value) {
                    textView.addTextChangedListener(textWatcher)
                    textView.addOnLayoutChangeListener(mOnLayoutChangeListener)
                    autoSize()
                } else {
                    textView.removeTextChangedListener(textWatcher)
                    textView.removeOnLayoutChangeListener(mOnLayoutChangeListener)
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_PX, rawTextSize)
                }
            }
        }

    private var isInAutoSize: Boolean = false

    private var listeners = ArrayList<OnTextSizeChangeListener>()

    private val textWatcher = AutoSizeTextWatcher()

    private val mOnLayoutChangeListener = AutoSizeOnLayoutChangeListener()

    fun addOnTextSizeChangeListener(listener: OnTextSizeChangeListener) {
        listeners.add(listener)
    }

    fun removeOnTextSizeChangeListener(listener: OnTextSizeChangeListener) {
        listeners.remove(listener)
    }

    fun setMinTextSize(unit: Int, size: Float) {
        minTextSize = TypedValue.applyDimension(unit, size, getScreenDisplayMetrics())
    }

    fun setMaxTextSize(unit: Int, size: Float) {
        maxTextSize = TypedValue.applyDimension(unit, size, getScreenDisplayMetrics())
    }

    fun setRawTextSize(unit: Int, size: Float) {
        if (isInAutoSize) return
        rawTextSize = TypedValue.applyDimension(unit, size, getScreenDisplayMetrics())
    }

    private fun autoSize() {
        val oldTextSize = textView.textSize

        isInAutoSize = true
        autoSize(
            textView,
            paint,
            minTextSize,
            maxTextSize,
            maxLines,
            precision
        )
        isInAutoSize = false

        val textSize = textView.textSize
        if (textSize != oldTextSize) {
            listeners.forEach { it.onTextSizeChange(textSize, oldTextSize) }
        }
    }

    private inner class AutoSizeTextWatcher : TextWatcher {
        override fun afterTextChanged(editable: Editable) = Unit
        override fun beforeTextChanged(
            charSequence: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) = Unit

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            autoSize()
        }
    }

    private inner class AutoSizeOnLayoutChangeListener : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            view: View, left: Int, top: Int, right: Int, bottom: Int,
            oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
        ) {
            autoSize()
        }
    }

    companion object {
        private const val DEFAULT_MIN_TEXT_SIZE = 8
        private const val DEFAULT_PRECISION = 0.5f

        @JvmOverloads
        fun create(
            view: TextView,
            attrs: AttributeSet? = null,
            defStyle: Int = 0
        ): AutoSizeTextHelper {
            val helper = AutoSizeTextHelper(view)
            if (attrs != null) {
                val context = view.context
                val a =
                    context.obtainStyledAttributes(attrs, R.styleable.AutoSizeTextView, defStyle, 0)
                helper.isEnabled =
                    a.getBoolean(R.styleable.AutoSizeTextView_ast_autoSizeEnable, false)
                helper.minTextSize =
                    a.getDimension(R.styleable.AutoSizeTextView_ast_minTextSize, helper.minTextSize)
                helper.precision =
                    a.getFloat(R.styleable.AutoSizeTextView_ast_precision, helper.precision)
                a.recycle()
            }
            return helper
        }

        private fun autoSize(
            view: TextView, paint: TextPaint, minTextSize: Float, maxTextSize: Float,
            maxLines: Int, precision: Float
        ) {
            if (maxLines <= 0 || maxLines == Integer.MAX_VALUE) return
            val targetWidth = view.width - view.paddingLeft - view.paddingRight
            if (targetWidth <= 0) return
            var text = view.text
            val method = view.transformationMethod
            if (method != null) text = method.getTransformation(text, view)
            var size = maxTextSize
            val high = size
            val low = 0f
            val displayMetrics = getScreenDisplayMetrics()
            paint.set(view.paint)
            paint.textSize = size
            if (maxLines == 1 && paint.measureText(
                    text,
                    0,
                    text.length
                ) > targetWidth || getLineCount(
                    text,
                    paint,
                    size,
                    targetWidth.toFloat(),
                    displayMetrics
                ) > maxLines
            ) {
                size =
                    getAutoSizeTextSize(
                        text, paint, targetWidth.toFloat(), maxLines, low, high, precision,
                        displayMetrics
                    )
            }
            if (size < minTextSize) {
                size = minTextSize
            }
            view.setTextSize(TypedValue.COMPLEX_UNIT_PX, size)
        }

        /**
         * Recursive binary search to find the best size for the text.
         */
        @Suppress("DEPRECATION")
        private fun getAutoSizeTextSize(
            text: CharSequence, paint: TextPaint,
            targetWidth: Float, maxLines: Int, low: Float, high: Float, precision: Float,
            displayMetrics: DisplayMetrics
        ): Float {
            val mid = (low + high) / 2.0f
            var lineCount = 1
            var layout: StaticLayout? = null

            paint.textSize =
                TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_PX, mid, displayMetrics)
            if (maxLines != 1) {
                layout = StaticLayout(
                    text, paint, targetWidth.toInt(), Layout.Alignment.ALIGN_NORMAL,
                    1.0f, 0.0f, true
                )
                lineCount = layout.lineCount
            }

            when {
                lineCount > maxLines -> return if (high - low < precision) {
                    low
                } else getAutoSizeTextSize(
                    text, paint, targetWidth, maxLines, low, mid, precision,
                    displayMetrics
                )
                lineCount < maxLines -> return getAutoSizeTextSize(
                    text, paint, targetWidth, maxLines, mid, high, precision,
                    displayMetrics
                )
                else -> {
                    var maxLineWidth = 0f
                    if (maxLines == 1) {
                        maxLineWidth = paint.measureText(text, 0, text.length)
                    } else {
                        for (i in 0 until lineCount) {
                            if (layout!!.getLineWidth(i) > maxLineWidth) {
                                maxLineWidth = layout.getLineWidth(i)
                            }
                        }
                    }
                    return when {
                        high - low < precision -> low
                        maxLineWidth > targetWidth -> getAutoSizeTextSize(
                            text, paint, targetWidth, maxLines, low, mid, precision,
                            displayMetrics
                        )
                        maxLineWidth < targetWidth -> getAutoSizeTextSize(
                            text, paint, targetWidth, maxLines, mid, high, precision,
                            displayMetrics
                        )
                        else -> mid
                    }
                }
            }
        }

        @Suppress("DEPRECATION")
        private fun getLineCount(
            text: CharSequence, paint: TextPaint, size: Float, width: Float,
            displayMetrics: DisplayMetrics
        ): Int {
            paint.textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, size,
                displayMetrics
            )
            val layout = StaticLayout(
                text, paint, width.toInt(),
                Layout.Alignment.ALIGN_NORMAL, 1.0f, 0.0f, true
            )
            return layout.lineCount
        }
    }
}