package com.chooongg.core.widget.gravitySnap

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import com.chooongg.core.R
import com.chooongg.core.widget.gravitySnap.GravitySnapHelper.SnapListener


/**
 * An [OrientationAwareRecyclerView] that uses a default [GravitySnapHelper]
 */
class GravitySnapRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : OrientationAwareRecyclerView(context, attrs, defStyleAttr) {

    private val snapHelper: GravitySnapHelper
    var isSnappingEnabled = false
        private set

    init {
        val typedArray: TypedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.GravitySnapRecyclerView, defStyleAttr, 0
        )
        val snapGravity = typedArray.getInt(
            R.styleable.GravitySnapRecyclerView_snapGravity, 0
        )
        snapHelper = when (snapGravity) {
            0 -> GravitySnapHelper(Gravity.START)
            1 -> GravitySnapHelper(Gravity.TOP)
            2 -> GravitySnapHelper(Gravity.END)
            3 -> GravitySnapHelper(Gravity.BOTTOM)
            4 -> GravitySnapHelper(Gravity.CENTER)
            else -> throw IllegalArgumentException(
                "Invalid gravity value. Use START | END | BOTTOM | TOP | CENTER constants"
            )
        }
        snapHelper.setSnapToPadding(
            typedArray.getBoolean(R.styleable.GravitySnapRecyclerView_snapToPadding, false)
        )
        snapHelper.setSnapLastItem(
            typedArray.getBoolean(R.styleable.GravitySnapRecyclerView_snapLastItem, false)
        )
        snapHelper.setMaxFlingSizeFraction(
            typedArray.getFloat(
                R.styleable.GravitySnapRecyclerView_snapMaxFlingSizeFraction,
                GravitySnapHelper.FLING_SIZE_FRACTION_DISABLE
            )
        )
        snapHelper.setScrollMsPerInch(
            typedArray.getFloat(R.styleable.GravitySnapRecyclerView_snapScrollMsPerInch, 100f)
        )
        enableSnapping(
            typedArray.getBoolean(R.styleable.GravitySnapRecyclerView_snapEnabled, true)
        )
        typedArray.recycle()
    }

    override fun smoothScrollToPosition(position: Int) {
        if (!isSnappingEnabled || !snapHelper.smoothScrollToPosition(position)) {
            super.smoothScrollToPosition(position)
        }
    }

    override fun scrollToPosition(position: Int) {
        if (!isSnappingEnabled || !snapHelper.scrollToPosition(position)) {
            super.scrollToPosition(position)
        }
    }

    fun enableSnapping(enable: Boolean) {
        if (enable) {
            snapHelper.attachToRecyclerView(this)
        } else {
            snapHelper.attachToRecyclerView(null)
        }
        isSnappingEnabled = enable
    }

    val currentSnappedPosition: Int
        get() = snapHelper.getCurrentSnappedPosition()

    fun snapToNextPosition(smooth: Boolean) {
        snapTo(true, smooth)
    }

    fun snapToPreviousPosition(smooth: Boolean) {
        snapTo(false, smooth)
    }

    fun setSnapListener(listener: SnapListener?) {
        snapHelper.setSnapListener(listener)
    }

    private fun snapTo(next: Boolean, smooth: Boolean) {
        val lm = layoutManager
        if (lm != null) {
            val snapView: View? = snapHelper.findSnapView(lm, false)
            if (snapView != null) {
                val pos: Int = getChildAdapterPosition(snapView)
                if (next) {
                    if (smooth) smoothScrollToPosition(pos + 1)
                    else scrollToPosition(pos + 1)
                } else if (pos > 0) {
                    if (smooth) smoothScrollToPosition(pos - 1)
                    else scrollToPosition(pos - 1)
                }
            }
        }
    }
}