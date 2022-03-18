package com.chooongg.core.widget

import android.content.Context
import android.graphics.*
import android.text.TextPaint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import com.chooongg.core.R

/**
 * 倾斜标签文本
 */
class SlantedTextView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : View(context, attrs, defStyleAttr, defStyleRes) {

    companion object {
        const val ROTATE_ANGLE = 45

        const val MODE_LEFT = 0
        const val MODE_RIGHT = 1
        const val MODE_LEFT_BOTTOM = 2
        const val MODE_RIGHT_BOTTOM = 3
        const val MODE_LEFT_TRIANGLE = 4
        const val MODE_RIGHT_TRIANGLE = 5
        const val MODE_LEFT_BOTTOM_TRIANGLE = 6
        const val MODE_RIGHT_BOTTOM_TRIANGLE = 7
    }

    private var paint: Paint
    private var textPaint: TextPaint
    private var slantedLength = 40f
    private var textSize = 16f
    private var slantedBackgroundColor: Int = Color.TRANSPARENT
    private var textColor: Int = Color.WHITE
    private var slantedText: String? = null
    private var mMode = MODE_LEFT

    init {
        val array = getContext().obtainStyledAttributes(attrs, R.styleable.SlantedTextView)
        textSize = array.getDimension(R.styleable.SlantedTextView_slantedTextSize, textSize)
        textColor = array.getColor(R.styleable.SlantedTextView_slantedTextColor, textColor)
        slantedLength =
            array.getDimension(R.styleable.SlantedTextView_slantedLength, slantedLength)
        slantedBackgroundColor =
            array.getColor(
                R.styleable.SlantedTextView_slantedBackgroundColor,
                slantedBackgroundColor
            )

        if (array.hasValue(R.styleable.SlantedTextView_slantedText)) {
            slantedText = array.getString(R.styleable.SlantedTextView_slantedText)!!
        }

        if (array.hasValue(R.styleable.SlantedTextView_slantedMode)) {
            mMode = array.getInt(R.styleable.SlantedTextView_slantedMode, 0)
        }
        array.recycle()

        paint = Paint()
        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_OVER)
        paint.color = slantedBackgroundColor

        textPaint = TextPaint(Paint.ANTI_ALIAS_FLAG)
        textPaint.isAntiAlias = true
        textPaint.textSize = textSize
        textPaint.color = textColor
    }

    override fun onDraw(canvas: Canvas) {
        drawBackground(canvas)
        drawText(canvas)
    }

    private fun drawBackground(canvas: Canvas) {
        var path = Path()
        val w = width
        val h = height
        check(w == h) { "SlantedTextView's width must equal to height" }
        when (mMode) {
            MODE_LEFT -> path = getModeLeftPath(path, w, h)
            MODE_RIGHT -> path = getModeRightPath(path, w, h)
            MODE_LEFT_BOTTOM -> path = getModeLeftBottomPath(path, w, h)
            MODE_RIGHT_BOTTOM -> path = getModeRightBottomPath(path, w, h)
            MODE_LEFT_TRIANGLE -> path = getModeLeftTrianglePath(path, w, h)
            MODE_RIGHT_TRIANGLE -> path = getModeRightTrianglePath(path, w, h)
            MODE_LEFT_BOTTOM_TRIANGLE -> path = getModeLeftBottomTrianglePath(path, w, h)
            MODE_RIGHT_BOTTOM_TRIANGLE -> path = getModeRightBottomTrianglePath(path, w, h)
        }
        path.close()
        canvas.drawPath(path, paint)
        canvas.save()
    }

    private fun getModeLeftPath(path: Path, w: Int, h: Int): Path {
        path.moveTo(w.toFloat(), 0f)
        path.lineTo(0f, h.toFloat())
        path.lineTo(0f, h - slantedLength)
        path.lineTo(w - slantedLength, 0f)
        return path
    }

    private fun getModeRightPath(path: Path, w: Int, h: Int): Path {
        path.lineTo(w.toFloat(), h.toFloat())
        path.lineTo(w.toFloat(), h - slantedLength)
        path.lineTo(slantedLength, 0f)
        return path
    }

    private fun getModeLeftBottomPath(path: Path, w: Int, h: Int): Path {
        path.lineTo(w.toFloat(), h.toFloat())
        path.lineTo(w - slantedLength, h.toFloat())
        path.lineTo(0f, slantedLength)
        return path
    }

    private fun getModeRightBottomPath(path: Path, w: Int, h: Int): Path {
        path.moveTo(0f, h.toFloat())
        path.lineTo(slantedLength, h.toFloat())
        path.lineTo(w.toFloat(), slantedLength)
        path.lineTo(w.toFloat(), 0f)
        return path
    }

    private fun getModeLeftTrianglePath(path: Path, w: Int, h: Int): Path {
        path.lineTo(0f, h.toFloat())
        path.lineTo(w.toFloat(), 0f)
        return path
    }

    private fun getModeRightTrianglePath(path: Path, w: Int, h: Int): Path {
        path.lineTo(w.toFloat(), 0f)
        path.lineTo(w.toFloat(), h.toFloat())
        return path
    }

    private fun getModeLeftBottomTrianglePath(path: Path, w: Int, h: Int): Path {
        path.lineTo(w.toFloat(), h.toFloat())
        path.lineTo(0f, h.toFloat())
        return path
    }

    private fun getModeRightBottomTrianglePath(path: Path, w: Int, h: Int): Path {
        path.moveTo(0f, h.toFloat())
        path.lineTo(w.toFloat(), h.toFloat())
        path.lineTo(w.toFloat(), 0f)
        return path
    }

    private fun drawText(canvas: Canvas) {
        if (slantedText == null) return
        val w = (canvas.width - slantedLength / 2).toInt()
        val h = (canvas.height - slantedLength / 2).toInt()
        val xy: FloatArray = calculateXY(w, h)
        val toX = xy[0]
        val toY = xy[1]
        val centerX = xy[2]
        val centerY = xy[3]
        val angle = xy[4]
        canvas.rotate(angle, centerX, centerY)
        canvas.drawText(slantedText!!, toX, toY, textPaint)
    }

    private fun calculateXY(w: Int, h: Int): FloatArray {
        val xy = FloatArray(5)
        val rect: Rect?
        val rectF: RectF?
        val offset = (slantedLength / 2).toInt()
        when (mMode) {
            MODE_LEFT_TRIANGLE, MODE_LEFT -> {
                rect = Rect(0, 0, w, h)
                rectF = RectF(rect)
                rectF.right = textPaint.measureText(slantedText, 0, slantedText!!.length)
                rectF.bottom = textPaint.descent() - textPaint.ascent()
                rectF.left += (rect.width() - rectF.right) / 2.0f
                rectF.top += (rect.height() - rectF.bottom) / 2.0f
                xy[0] = rectF.left
                xy[1] = rectF.top - textPaint.ascent()
                xy[2] = (w / 2).toFloat()
                xy[3] = (h / 2).toFloat()
                xy[4] = (-ROTATE_ANGLE).toFloat()
            }
            MODE_RIGHT_TRIANGLE, MODE_RIGHT -> {
                rect = Rect(offset, 0, w + offset, h)
                rectF = RectF(rect)
                rectF.right = textPaint.measureText(slantedText, 0, slantedText!!.length)
                rectF.bottom = textPaint.descent() - textPaint.ascent()
                rectF.left += (rect.width() - rectF.right) / 2.0f
                rectF.top += (rect.height() - rectF.bottom) / 2.0f
                xy[0] = rectF.left
                xy[1] = rectF.top - textPaint.ascent()
                xy[2] = (w / 2 + offset).toFloat()
                xy[3] = (h / 2).toFloat()
                xy[4] = ROTATE_ANGLE.toFloat()
            }
            MODE_LEFT_BOTTOM_TRIANGLE, MODE_LEFT_BOTTOM -> {
                rect = Rect(0, offset, w, h + offset)
                rectF = RectF(rect)
                rectF.right = textPaint.measureText(slantedText, 0, slantedText!!.length)
                rectF.bottom = textPaint.descent() - textPaint.ascent()
                rectF.left += (rect.width() - rectF.right) / 2.0f
                rectF.top += (rect.height() - rectF.bottom) / 2.0f
                xy[0] = rectF.left
                xy[1] = rectF.top - textPaint.ascent()
                xy[2] = (w / 2).toFloat()
                xy[3] = (h / 2 + offset).toFloat()
                xy[4] = ROTATE_ANGLE.toFloat()
            }
            MODE_RIGHT_BOTTOM_TRIANGLE, MODE_RIGHT_BOTTOM -> {
                rect = Rect(offset, offset, w + offset, h + offset)
                rectF = RectF(rect)
                rectF.right = textPaint.measureText(slantedText, 0, slantedText!!.length)
                rectF.bottom = textPaint.descent() - textPaint.ascent()
                rectF.left += (rect.width() - rectF.right) / 2.0f
                rectF.top += (rect.height() - rectF.bottom) / 2.0f
                xy[0] = rectF.left
                xy[1] = rectF.top - textPaint.ascent()
                xy[2] = (w / 2 + offset).toFloat()
                xy[3] = (h / 2 + offset).toFloat()
                xy[4] = (-ROTATE_ANGLE).toFloat()
            }
        }
        return xy
    }

    fun setText(str: String): SlantedTextView {
        slantedText = str
        postInvalidate()
        return this
    }

    fun setText(res: Int): SlantedTextView {
        val str = resources.getString(res)
        if (!TextUtils.isEmpty(str)) {
            setText(str)
        }
        return this
    }

    fun getText(): String? {
        return slantedText
    }

    fun setSlantedBackgroundColor(color: Int): SlantedTextView {
        slantedBackgroundColor = color
        paint.color = slantedBackgroundColor
        postInvalidate()
        return this
    }

    fun setTextColor(color: Int): SlantedTextView {
        textColor = color
        textPaint.color = textColor
        postInvalidate()
        return this
    }

    /**
     * @param mode :
     * SlantedTextView.MODE_LEFT : top left
     * SlantedTextView.MODE_RIGHT :top right
     * @return this
     */
    fun setMode(mode: Int): SlantedTextView {
        require(!(mMode > MODE_RIGHT_BOTTOM_TRIANGLE || mMode < 0)) { mode.toString() + "is illegal argument ,please use right value" }
        mMode = mode
        postInvalidate()
        return this
    }

    fun getMode(): Int {
        return mMode
    }

    fun setTextSize(size: Int): SlantedTextView {
        textSize = size.toFloat()
        textPaint.textSize = textSize
        postInvalidate()
        return this
    }

    /**
     * set slanted space length
     *
     * @param length
     * @return this
     */
    fun setSlantedLength(length: Int): SlantedTextView {
        slantedLength = length.toFloat()
        postInvalidate()
        return this
    }

}