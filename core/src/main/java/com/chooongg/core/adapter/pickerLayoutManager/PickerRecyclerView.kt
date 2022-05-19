package com.chooongg.core.adapter.pickerLayoutManager

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.core.R
import com.chooongg.ext.attrColor

/**
 * 包装PickerLayoutManager的PickerRecyclerView
 */
open class PickerRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    companion object {
        const val DIVIDER_VISIBLE = true
        const val DIVIDER_SIZE = 1.0f
        const val DIVIDER_MARGIN = 0f
    }

    var orientation = PickerLayoutManager.VERTICAL
    var visibleCount = PickerLayoutManager.VISIBLE_COUNT
    var isLoop = PickerLayoutManager.IS_LOOP
    var pickerScaleX = PickerLayoutManager.SCALE_X
    var pickerScaleY = PickerLayoutManager.SCALE_Y
    var pickerAlpha = PickerLayoutManager.ALPHA

    var dividerVisible = DIVIDER_VISIBLE
    var dividerSize = DIVIDER_SIZE
    var dividerColor = attrColor(com.google.android.material.R.attr.colorOutline)
    var dividerMargin = DIVIDER_MARGIN

    private var decor: PickerItemDecoration? = null

    init {
        val typeA = context.obtainStyledAttributes(
            attrs, R.styleable.PickerRecyclerView, defStyleAttr, 0
        )

        orientation = typeA.getInt(R.styleable.PickerRecyclerView_orientation, orientation)
        visibleCount = typeA.getInt(R.styleable.PickerRecyclerView_visibleCount, visibleCount)
        isLoop = typeA.getBoolean(R.styleable.PickerRecyclerView_isLoop, isLoop)
        pickerScaleX = typeA.getFloat(R.styleable.PickerRecyclerView_scaleX, pickerScaleX)
        pickerScaleY = typeA.getFloat(R.styleable.PickerRecyclerView_scaleY, pickerScaleY)
        pickerAlpha = typeA.getFloat(R.styleable.PickerRecyclerView_alpha, pickerAlpha)

        dividerVisible =
            typeA.getBoolean(R.styleable.PickerRecyclerView_dividerVisible, dividerVisible)
        dividerSize =
            typeA.getDimension(R.styleable.PickerRecyclerView_dividerSize, dividerSize)
        dividerColor =
            typeA.getColor(R.styleable.PickerRecyclerView_dividerColor, dividerColor)
        dividerMargin =
            typeA.getDimension(R.styleable.PickerRecyclerView_dividerMargin, dividerMargin)

        typeA.recycle()
        resetLayoutManager(orientation, visibleCount, isLoop, pickerScaleX, pickerScaleY, pickerAlpha)
    }

    /**
     * 重新设置LayoutManager
     */
    fun resetLayoutManager(
        orientation: Int = this.orientation,
        visibleCount: Int = this.visibleCount,
        isLoop: Boolean = this.isLoop,
        scaleX: Float = pickerScaleX,
        scaleY: Float = pickerScaleY,
        alpha: Float = pickerAlpha
    ) {
        val lm = PickerLayoutManager(
            orientation,
            visibleCount,
            isLoop,
            scaleX,
            scaleY,
            alpha
        )
        resetLayoutManager(lm)
    }

    open fun resetLayoutManager(lm: PickerLayoutManager) {
        this.layoutManager = lm
    }

    override fun setLayoutManager(layout: LayoutManager?) {
        super.setLayoutManager(layout)
        initDivider()
        if (layout !is PickerLayoutManager) {
            throw IllegalArgumentException("LayoutManager only can use PickerLayoutManager")
        }
    }

    /**
     * 获取选中的那个item的position
     */
    open fun getSelectedPosition() = layoutManager.getSelectedPosition()

    /**
     *
     */
    override fun getLayoutManager(): PickerLayoutManager {
        return super.getLayoutManager() as PickerLayoutManager
    }

    //设置分割线
    open fun initDivider() {
        removeDivider()

        if (!dividerVisible) return
        decor = PickerItemDecoration(dividerColor, dividerSize, dividerMargin)
        this.addItemDecoration(decor!!)
    }

    //删除分割线
    open fun removeDivider() {
        decor?.let { removeItemDecoration(it) }
    }

    fun addOnSelectedItemListener(listener: OnItemSelectedListener) {
        layoutManager.addOnItemSelectedListener(listener)
    }

    fun removeOnItemSelectedListener(listener: OnItemSelectedListener) {
        layoutManager.removeOnItemSelectedListener(listener)
    }

    /**
     * 删除所有的监听器
     */
    fun removeAllOnItemSelectedListener() {
        layoutManager.removeAllOnItemSelectedListener()
    }

    fun addOnItemFillListener(listener: PickerLayoutManager.OnItemFillListener) {
        layoutManager.addOnItemFillListener(listener)
    }

    fun removeOnItemFillListener(listener: PickerLayoutManager.OnItemFillListener) {
        layoutManager.removeOnItemFillListener(listener)
    }

    fun removeAllItemFillListener() {
        layoutManager.removeAllItemFillListener()
    }

    /**
     * 滚动到最后一个item
     */
    fun scrollToEnd() {
        if (adapter == null) return
        this.post {
            this.scrollToPosition(adapter!!.itemCount - 1)
        }
    }

    /**
     * 平滑的滚动到最后一个item
     */
    fun smoothScrollToEnd() {
        if (adapter == null) return
        this.post {
            this.scrollToPosition(adapter!!.itemCount - 1)
        }
    }
}