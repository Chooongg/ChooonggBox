package com.chooongg.core.widget.shimmer

import android.animation.Animator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.ViewTreeObserver
import androidx.appcompat.widget.LinearLayoutCompat
import com.chooongg.core.R
import kotlin.math.abs
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.tan

@SuppressLint("CustomViewStyleable")
class ShimmerLinearLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : LinearLayoutCompat(context, attrs, defStyleAttr) {

    companion object {
        private const val DEFAULT_ANIMATION_DURATION = 1500

        private const val DEFAULT_ANGLE = 20

        private const val MIN_ANGLE_VALUE = -45
        private const val MAX_ANGLE_VALUE = 45
        private const val MIN_MASK_WIDTH_VALUE = 0f
        private const val MAX_MASK_WIDTH_VALUE = 1f

        private const val MIN_GRADIENT_CENTER_COLOR_WIDTH_VALUE = 0f
        private const val MAX_GRADIENT_CENTER_COLOR_WIDTH_VALUE = 1f
    }

    private var maskOffsetX = 0
    private var maskRect: Rect? = null
    private var gradientTexturePaint: Paint? = null
    private var maskAnimator: ValueAnimator? = null

    private var localMaskBitmap: Bitmap? = null
    private var maskBitmap: Bitmap? = null
    private var canvasForShimmerMask: Canvas? = null

    private var isAnimationStarted: Boolean = false
    private var isAnimationReversed: Boolean
    private var autoStart: Boolean
    private var shimmerAnimationDuration = 0
    private var shimmerColor: Int
    private var shimmerAngle: Int
    private var maskWidth: Float
    private var gradientCenterColorWidth: Float

    private var startAnimationPreDrawListener: ViewTreeObserver.OnPreDrawListener? = null

    init {
        setWillNotDraw(false)
        val a = context.obtainStyledAttributes(attrs, R.styleable.ShimmerLinearLayout, 0, 0)
        shimmerAngle = a.getInteger(R.styleable.ShimmerLinearLayout_shimmer_angle, DEFAULT_ANGLE)
        shimmerAnimationDuration = a.getInteger(
            R.styleable.ShimmerLinearLayout_shimmer_animation_duration,
            DEFAULT_ANIMATION_DURATION
        )
        shimmerColor = a.getColor(R.styleable.ShimmerLinearLayout_shimmer_color, Color.GRAY)
        autoStart = a.getBoolean(R.styleable.ShimmerLinearLayout_shimmer_auto_start, true)
        maskWidth = a.getFloat(R.styleable.ShimmerLinearLayout_shimmer_mask_width, 0.5f)
        gradientCenterColorWidth =
            a.getFloat(R.styleable.ShimmerLinearLayout_shimmer_gradient_center_color_width, 0.1f)
        isAnimationReversed =
            a.getBoolean(R.styleable.ShimmerLinearLayout_shimmer_reverse_animation, false)
        a.recycle()

        setMaskWidth(maskWidth)
        setGradientCenterColorWidth(gradientCenterColorWidth)
        setShimmerAngle(shimmerAngle)

        if (autoStart && visibility == VISIBLE) {
            startShimmerAnimation()
        }
    }

    override fun onDetachedFromWindow() {
        resetShimmering()
        super.onDetachedFromWindow()
    }

    override fun dispatchDraw(canvas: Canvas) {
        if (!isAnimationStarted || width <= 0 || height <= 0) {
            super.dispatchDraw(canvas)
        } else {
            dispatchDrawShimmer(canvas)
        }
    }

    override fun setVisibility(visibility: Int) {
        super.setVisibility(visibility)
        if (visibility == VISIBLE) {
            if (autoStart) {
                startShimmerAnimation()
            }
        } else {
            stopShimmerAnimation()
        }
    }

    fun startShimmerAnimation() {
        if (isAnimationStarted) return
        if (width == 0) {
            startAnimationPreDrawListener = object : ViewTreeObserver.OnPreDrawListener {
                override fun onPreDraw(): Boolean {
                    viewTreeObserver.removeOnPreDrawListener(this)
                    startShimmerAnimation()
                    return true
                }
            }
            viewTreeObserver.addOnPreDrawListener(startAnimationPreDrawListener)
            return
        }
        val animator: Animator = getShimmerAnimation()
        animator.start()
        isAnimationStarted = true
    }

    fun stopShimmerAnimation() {
        startAnimationPreDrawListener?.let { viewTreeObserver.removeOnPreDrawListener(it) }
        resetShimmering()
    }

    fun isAnimationStarted() = isAnimationStarted

    fun setShimmerColor(shimmerColor: Int) {
        this.shimmerColor = shimmerColor
        resetIfStarted()
    }

    fun setShimmerAnimationDuration(durationMillis: Int) {
        shimmerAnimationDuration = durationMillis
        resetIfStarted()
    }

    fun setAnimationReversed(animationReversed: Boolean) {
        isAnimationReversed = animationReversed
        resetIfStarted()
    }

    /**
     * 以度为单位设置顺时针方向的 Shimmer 角度
     * 角度必须介于 {@value #MIN_ANGLE_VALUE} 和 {@value #MAX_ANGLE_VALUE}
     */
    fun setShimmerAngle(angle: Int) {
        var angleTemp = angle
        if (angleTemp > MAX_ANGLE_VALUE) {
            angleTemp = MAX_ANGLE_VALUE
        } else if (angleTemp < MIN_ANGLE_VALUE) {
            angleTemp = MIN_ANGLE_VALUE
        }
        shimmerAngle = angleTemp
        resetIfStarted()
    }

    /**
     * 将 Shimmer 线的宽度设置为大于 0 到小于或等于 1 的值
     * 1表示 Shimmer 线的宽度等于 Shimmer 布局宽度的一半
     * 默认值为0.5
     */
    fun setMaskWidth(maskWidth: Float) {
        var maskWidthTemp = maskWidth
        if (maskWidthTemp > MAX_MASK_WIDTH_VALUE) {
            maskWidthTemp = MAX_MASK_WIDTH_VALUE
        } else if (maskWidthTemp < MIN_MASK_WIDTH_VALUE) {
            maskWidthTemp = MIN_MASK_WIDTH_VALUE
        }
        this.maskWidth = maskWidthTemp
        resetIfStarted()
    }

    /**
     * 将中心渐变颜色的宽度设置为大于 0 到小于 1 的值
     * 0.99 表示整个 Shimmer 线将具有此颜色 并带有一点透明边缘
     * 默认值为 0.1
     */
    fun setGradientCenterColorWidth(gradientCenterColorWidth: Float) {
        var gradientCenterColorWidthTemp = gradientCenterColorWidth
        if (gradientCenterColorWidthTemp > MAX_GRADIENT_CENTER_COLOR_WIDTH_VALUE) {
            gradientCenterColorWidthTemp = MAX_GRADIENT_CENTER_COLOR_WIDTH_VALUE
        } else if (gradientCenterColorWidthTemp < MIN_GRADIENT_CENTER_COLOR_WIDTH_VALUE) {
            gradientCenterColorWidthTemp = MIN_GRADIENT_CENTER_COLOR_WIDTH_VALUE
        }
        this.gradientCenterColorWidth = gradientCenterColorWidthTemp
        resetIfStarted()
    }

    private fun resetIfStarted() {
        if (isAnimationStarted) {
            resetShimmering()
            startShimmerAnimation()
        }
    }

    private fun dispatchDrawShimmer(canvas: Canvas) {
        super.dispatchDraw(canvas)
        localMaskBitmap = getMaskBitmap()
        if (localMaskBitmap == null) return
        if (canvasForShimmerMask == null) {
            canvasForShimmerMask = Canvas(localMaskBitmap!!)
        }
        canvasForShimmerMask!!.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR)
        canvasForShimmerMask!!.save()
        canvasForShimmerMask!!.translate(-maskOffsetX.toFloat(), 0f)
        super.dispatchDraw(canvasForShimmerMask)
        canvasForShimmerMask!!.restore()
        drawShimmer(canvas)
        localMaskBitmap = null
    }

    private fun drawShimmer(destinationCanvas: Canvas) {
        createShimmerPaint()
        destinationCanvas.save()
        destinationCanvas.translate(maskOffsetX.toFloat(), 0f)
        destinationCanvas.drawRect(
            maskRect!!.left.toFloat(),
            0f,
            maskRect!!.width().toFloat(),
            maskRect!!.height().toFloat(),
            gradientTexturePaint!!
        )
        destinationCanvas.restore()
    }

    private fun resetShimmering() {
        if (maskAnimator != null) {
            maskAnimator!!.end()
            maskAnimator!!.removeAllUpdateListeners()
        }
        maskAnimator = null
        gradientTexturePaint = null
        isAnimationStarted = false
        releaseBitMaps()
    }

    private fun releaseBitMaps() {
        canvasForShimmerMask = null
        if (maskBitmap != null) {
            maskBitmap!!.recycle()
            maskBitmap = null
        }
    }

    private fun getMaskBitmap(): Bitmap? {
        if (maskBitmap == null) {
            maskBitmap = createBitmap(maskRect!!.width(), height)
        }
        return maskBitmap
    }

    private fun createShimmerPaint() {
        if (gradientTexturePaint != null) {
            return
        }
        val edgeColor: Int = reduceColorAlphaValueToZero(shimmerColor)
        val shimmerLineWidth = width / 2 * maskWidth
        val yPosition = if (0 <= shimmerAngle) height.toFloat() else 0.toFloat()
        val gradient = LinearGradient(
            0f,
            yPosition,
            cos(Math.toRadians(shimmerAngle.toDouble())).toFloat() * shimmerLineWidth,
            yPosition + sin(Math.toRadians(shimmerAngle.toDouble()))
                .toFloat() * shimmerLineWidth,
            intArrayOf(edgeColor, shimmerColor, shimmerColor, edgeColor),
            getGradientColorDistribution(),
            Shader.TileMode.CLAMP
        )
        val maskBitmapShader =
            BitmapShader(localMaskBitmap!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
        val composeShader = ComposeShader(gradient, maskBitmapShader, PorterDuff.Mode.DST_IN)
        gradientTexturePaint = Paint()
        gradientTexturePaint!!.isAntiAlias = true
        gradientTexturePaint!!.isDither = true
        gradientTexturePaint!!.isFilterBitmap = true
        gradientTexturePaint!!.shader = composeShader
    }

    private fun getShimmerAnimation(): Animator {
        if (maskAnimator != null) {
            return maskAnimator!!
        }
        if (maskRect == null) {
            maskRect = calculateBitmapMaskRect()
        }
        val animationToX = width
        val animationFromX: Int =
            if (width > maskRect!!.width()) -animationToX else -maskRect!!.width()
        val shimmerBitmapWidth = maskRect!!.width()
        val shimmerAnimationFullLength = animationToX - animationFromX
        maskAnimator = if (isAnimationReversed) ValueAnimator.ofInt(shimmerAnimationFullLength, 0)
        else ValueAnimator.ofInt(0, shimmerAnimationFullLength)
        maskAnimator!!.duration = shimmerAnimationDuration.toLong()
        maskAnimator!!.repeatCount = ObjectAnimator.INFINITE
        maskAnimator!!.addUpdateListener {
            maskOffsetX = animationFromX + it.animatedValue as Int
            if (maskOffsetX + shimmerBitmapWidth >= 0) invalidate()
        }
        return maskAnimator!!
    }

    private fun createBitmap(width: Int, height: Int): Bitmap? {
        return try {
            Bitmap.createBitmap(width, height, Bitmap.Config.ALPHA_8)
        } catch (e: OutOfMemoryError) {
            System.gc()
            null
        }
    }

    private fun reduceColorAlphaValueToZero(actualColor: Int) =
        Color.argb(0, Color.red(actualColor), Color.green(actualColor), Color.blue(actualColor))

    private fun calculateBitmapMaskRect() = Rect(0, 0, calculateMaskWidth(), height)

    private fun calculateMaskWidth(): Int {
        val shimmerLineBottomWidth =
            width / 2 * maskWidth / cos(Math.toRadians(abs(shimmerAngle).toDouble()))
        val shimmerLineRemainingTopWidth =
            height * tan(Math.toRadians(abs(shimmerAngle).toDouble()))
        return (shimmerLineBottomWidth + shimmerLineRemainingTopWidth).toInt()
    }

    private fun getGradientColorDistribution(): FloatArray {
        val colorDistribution = FloatArray(4)
        colorDistribution[0] = 0f
        colorDistribution[3] = 1f
        colorDistribution[1] = 0.5f - gradientCenterColorWidth / 2f
        colorDistribution[2] = 0.5f + gradientCenterColorWidth / 2f
        return colorDistribution
    }
}