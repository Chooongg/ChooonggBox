package com.chooongg.core.widget.square

import android.content.Context
import android.util.AttributeSet
import android.widget.FrameLayout
import com.chooongg.core.R

class SquareFrameLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    defStyleRes: Int = 0
) : FrameLayout(context, attrs, defStyleAttr, defStyleRes) {

    private var basisAxis: Int = 0

    init {
        if (attrs != null) {
            val a = context.obtainStyledAttributes(
                attrs, R.styleable.SquareFrameLayout, defStyleAttr, defStyleRes
            )
            basisAxis = a.getInteger(R.styleable.SquareFrameLayout_basisAxis, 0)
            a.recycle()
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            getDefaultSize(0, widthMeasureSpec),
            getDefaultSize(0, heightMeasureSpec)
        )
        val size = MeasureSpec.makeMeasureSpec(
            if (basisAxis == 0) measuredWidth else measuredHeight, MeasureSpec.EXACTLY
        )
        super.onMeasure(size, size)
    }
}