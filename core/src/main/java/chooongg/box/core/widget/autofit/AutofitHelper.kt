package chooongg.box.core.widget.autofit

import android.content.res.Resources
import android.text.*
import android.util.AttributeSet
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.View
import android.widget.TextView
import chooongg.box.core.R
import java.util.*

/**
 * A helper class to enable automatically resizing [TextView]`s `textSize` to fit
 * within its bounds.
 *
 * @attr ref R.styleable.AutofitTextView_sizeToFit
 * @attr ref R.styleable.AutofitTextView_minTextSize
 * @attr ref R.styleable.AutofitTextView_precision
 */
class AutofitHelper private constructor(// Attributes
    private val mTextView: TextView
) {
    private val mPaint: TextPaint

    /**
     * Original textSize of the TextView.
     */
    private var mTextSize: Float = 0f

    private var mMaxLines: Int = 0
    private var mMinTextSize: Float = 0f
    private var mMaxTextSize: Float = 0f
    private var mPrecision: Float = 0f

    private var mEnabled: Boolean = false
    private var mIsAutofitting: Boolean = false

    private var mListeners: ArrayList<OnTextSizeChangeListener>? = null

    private val mTextWatcher = AutofitTextWatcher()

    private val mOnLayoutChangeListener = AutofitOnLayoutChangeListener()

    /**
     * Returns the original text size of the View.
     *
     * @see TextView.getTextSize
     */
    /**
     * Set the original text size of the View.
     *
     * @see TextView.setTextSize
     */
    var textSize: Float
        get() = mTextSize
        set(size) = setTextSize(TypedValue.COMPLEX_UNIT_SP, size)

    init {
        val context = mTextView.context
        val scaledDensity = context.resources.displayMetrics.scaledDensity
        mPaint = TextPaint()
        setRawTextSize(mTextView.textSize)
        mMaxLines =
            getMaxLines(
                mTextView
            )
        mMinTextSize = scaledDensity * DEFAULT_MIN_TEXT_SIZE
        mMaxTextSize = mTextSize
        mPrecision =
            DEFAULT_PRECISION
    }

    /**
     * Adds an [OnTextSizeChangeListener] to the list of those whose methods are called
     * whenever the [TextView]'s `textSize` changes.
     */
    fun addOnTextSizeChangeListener(listener: OnTextSizeChangeListener): AutofitHelper {
        if (mListeners == null) {
            mListeners = ArrayList()
        }
        mListeners!!.add(listener)
        return this
    }

    /**
     * Removes the specified [OnTextSizeChangeListener] from the list of those whose methods
     * are called whenever the [TextView]'s `textSize` changes.
     */
    fun removeOnTextSizeChangeListener(listener: OnTextSizeChangeListener): AutofitHelper {
        if (mListeners != null) {
            mListeners!!.remove(listener)
        }
        return this
    }

    /**
     * Returns the amount of precision used to calculate the correct text size to fit within its
     * bounds.
     */
    fun getPrecision(): Float = mPrecision

    /**
     * Set the amount of precision used to calculate the correct text size to fit within its
     * bounds. Lower precision is more precise and takes more time.
     *
     * @param precision The amount of precision.
     */
    fun setPrecision(precision: Float): AutofitHelper {
        if (mPrecision != precision) {
            mPrecision = precision

            autofit()
        }
        return this
    }

    /**
     * Returns the minimum size (in pixels) of the text.
     */
    fun getMinTextSize(): Float = mMinTextSize

    /**
     * Set the minimum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * @param size The scaled pixel size.
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    fun setMinTextSize(size: Float): AutofitHelper =
        setMinTextSize(TypedValue.COMPLEX_UNIT_SP, size)

    /**
     * Set the minimum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     * @attr ref me.grantland.R.styleable#AutofitTextView_minTextSize
     */
    fun setMinTextSize(unit: Int, size: Float): AutofitHelper {
        val context = mTextView.context
        var r = Resources.getSystem()
        if (context != null) {
            r = context.resources
        }
        setRawMinTextSize(TypedValue.applyDimension(unit, size, r.displayMetrics))
        return this
    }

    private fun setRawMinTextSize(size: Float) {
        if (size != mMinTextSize) {
            mMinTextSize = size
            autofit()
        }
    }

    /**
     * Returns the maximum size (in pixels) of the text.
     */
    fun getMaxTextSize(): Float {
        return mMaxTextSize
    }

    /**
     * Set the maximum text size to the given value, interpreted as "scaled pixel" units. This size
     * is adjusted based on the current density and user font size preference.
     *
     * @param size The scaled pixel size.
     * @attr ref android.R.styleable#TextView_textSize
     */
    fun setMaxTextSize(size: Float): AutofitHelper {
        return setMaxTextSize(TypedValue.COMPLEX_UNIT_SP, size)
    }

    /**
     * Set the maximum text size to a given unit and value. See TypedValue for the possible
     * dimension units.
     *
     * @param unit The desired dimension unit.
     * @param size The desired size in the given units.
     * @attr ref android.R.styleable#TextView_textSize
     */
    fun setMaxTextSize(unit: Int, size: Float): AutofitHelper {
        val context = mTextView.context
        var r = Resources.getSystem()
        if (context != null) {
            r = context.resources
        }
        setRawMaxTextSize(TypedValue.applyDimension(unit, size, r.displayMetrics))
        return this
    }

    private fun setRawMaxTextSize(size: Float) {
        if (size != mMaxTextSize) {
            mMaxTextSize = size
            autofit()
        }
    }

    /**
     * @see TextView.getMaxLines
     */
    fun getMaxLines(): Int {
        return mMaxLines
    }

    /**
     * @see TextView.setMaxLines
     */
    fun setMaxLines(lines: Int): AutofitHelper {
        if (mMaxLines != lines) {
            mMaxLines = lines
            autofit()
        }
        return this
    }

    /**
     * Returns whether or not automatically resizing text is enabled.
     */
    fun isEnabled(): Boolean {
        return mEnabled
    }

    /**
     * Set the enabled state of automatically resizing text.
     */
    fun setEnabled(enabled: Boolean): AutofitHelper {
        if (mEnabled != enabled) {
            mEnabled = enabled
            if (enabled) {
                mTextView.addTextChangedListener(mTextWatcher)
                mTextView.addOnLayoutChangeListener(mOnLayoutChangeListener)
                autofit()
            } else {
                mTextView.removeTextChangedListener(mTextWatcher)
                mTextView.removeOnLayoutChangeListener(mOnLayoutChangeListener)
                mTextView.setTextSize(TypedValue.COMPLEX_UNIT_PX, mTextSize)
            }
        }
        return this
    }

    /**
     * Set the original text size of the View.
     *
     * @see TextView.setTextSize
     */
    fun setTextSize(unit: Int, size: Float) {
        if (mIsAutofitting) {
            // We don't want to update the TextView's actual textSize while we're autofitting
            // since it'd get set to the autofitTextSize
            return
        }
        val context = mTextView.context
        var r = Resources.getSystem()

        if (context != null) {
            r = context.resources
        }
        setRawTextSize(TypedValue.applyDimension(unit, size, r.displayMetrics))
    }

    private fun setRawTextSize(size: Float) {
        if (mTextSize != size) {
            mTextSize = size
        }
    }

    private fun autofit() {
        val oldTextSize = mTextView.textSize
        val textSize: Float

        mIsAutofitting = true
        autofit(
            mTextView,
            mPaint,
            mMinTextSize,
            mMaxTextSize,
            mMaxLines,
            mPrecision
        )
        mIsAutofitting = false

        textSize = mTextView.textSize
        if (textSize != oldTextSize) {
            sendTextSizeChange(textSize, oldTextSize)
        }
    }

    private fun sendTextSizeChange(textSize: Float, oldTextSize: Float) {
        if (mListeners == null) {
            return
        }

        for (listener in mListeners!!) {
            listener.onTextSizeChange(textSize, oldTextSize)
        }
    }

    private inner class AutofitTextWatcher : TextWatcher {
        override fun beforeTextChanged(
            charSequence: CharSequence,
            start: Int,
            count: Int,
            after: Int
        ) {
            // do nothing
        }

        override fun onTextChanged(
            charSequence: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            autofit()
        }

        override fun afterTextChanged(editable: Editable) {
            // do nothing
        }
    }

    private inner class AutofitOnLayoutChangeListener : View.OnLayoutChangeListener {
        override fun onLayoutChange(
            view: View, left: Int, top: Int, right: Int, bottom: Int,
            oldLeft: Int, oldTop: Int, oldRight: Int, oldBottom: Int
        ) {
            autofit()
        }
    }

    /**
     * When an object of a type is attached to an `AutofitHelper`, its methods will be called
     * when the `textSize` is changed.
     */
    interface OnTextSizeChangeListener {
        /**
         * This method is called to notify you that the size of the text has changed to
         * `textSize` from `oldTextSize`.
         */
        fun onTextSizeChange(textSize: Float, oldTextSize: Float)
    }

    companion object {
        // Minimum size of the text in pixels
        private const val DEFAULT_MIN_TEXT_SIZE = 8 //sp

        // How precise we want to be when reaching the target textWidth size
        private const val DEFAULT_PRECISION = 0.5f

        /**
         * Creates a new instance of `AutofitHelper` that wraps a [TextView] and enables
         * automatically sizing the text to fit.
         */
        @JvmOverloads
        fun create(view: TextView, attrs: AttributeSet? = null, defStyle: Int = 0): AutofitHelper {
            val helper = AutofitHelper(view)
            var sizeToFit = true
            if (attrs != null) {
                val context = view.context
                var minTextSize = helper.getMinTextSize().toInt()
                var precision = helper.getPrecision()

                val ta = context.obtainStyledAttributes(
                    attrs,
                    R.styleable.AutofitTextView,
                    defStyle,
                    0
                )
                sizeToFit = ta.getBoolean(R.styleable.AutofitTextView_autofit_sizeToFit, sizeToFit)
                minTextSize = ta.getDimensionPixelSize(
                    R.styleable.AutofitTextView_autofit_minTextSize,
                    minTextSize
                )
                precision = ta.getFloat(R.styleable.AutofitTextView_autofit_precision, precision)
                ta.recycle()

                helper.setMinTextSize(TypedValue.COMPLEX_UNIT_PX, minTextSize.toFloat())
                    .setPrecision(precision)
            }
            helper.setEnabled(sizeToFit)
            return helper
        }

        /**
         * Re-sizes the textSize of the TextView so that the text fits within the bounds of the View.
         */
        private fun autofit(
            view: TextView, paint: TextPaint, minTextSize: Float, maxTextSize: Float,
            maxLines: Int, precision: Float
        ) {
            if (maxLines <= 0 || maxLines == Integer.MAX_VALUE) {
                // Don't auto-size since there's no limit on lines.
                return
            }
            val targetWidth = view.width - view.paddingLeft - view.paddingRight
            if (targetWidth <= 0) {
                return
            }
            var text = view.text
            val method = view.transformationMethod
            if (method != null) {
                text = method.getTransformation(text, view)
            }
            val context = view.context
            var r = Resources.getSystem()
            val displayMetrics: DisplayMetrics
            var size = maxTextSize
            val high = size
            val low = 0f
            if (context != null) {
                r = context.resources
            }
            displayMetrics = r.displayMetrics
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
                    getAutofitTextSize(
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
        private fun getAutofitTextSize(
            text: CharSequence, paint: TextPaint,
            targetWidth: Float, maxLines: Int, low: Float, high: Float, precision: Float,
            displayMetrics: DisplayMetrics
        ): Float {
            val mid = (low + high) / 2.0f
            var lineCount = 1
            var layout: StaticLayout? = null

            paint.textSize = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_PX, mid,
                displayMetrics
            )

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
                } else getAutofitTextSize(
                    text, paint, targetWidth, maxLines, low, mid, precision,
                    displayMetrics
                )
                lineCount < maxLines -> return getAutofitTextSize(
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
                        maxLineWidth > targetWidth -> getAutofitTextSize(
                            text, paint, targetWidth, maxLines, low, mid, precision,
                            displayMetrics
                        )
                        maxLineWidth < targetWidth -> getAutofitTextSize(
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

        private fun getMaxLines(view: TextView): Int = view.maxLines
    }
}