package com.chooongg.core.popupMenu

import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.FrameLayout
import android.widget.PopupWindow
import androidx.core.widget.PopupWindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.ext.dp2px
import com.google.android.material.R
import kotlin.math.ceil

/**
 * A more Material version of [androidx.appcompat.widget.ListPopupWindow] based on [RecyclerView].
 *
 * Its width is a multiple of 56dp units with a minimum of 112dp and a maximum of 280dp
 * as stated in the <a href="https://material.io/guidelines/components/menus.html#menus-simple-menus">Material documentation</a>
 *
 * @see androidx.appcompat.widget.ListPopupWindow
 */
internal class BoxRecyclerViewPopupWindow(
    private val context: Context,
    private val anchorView: View
) {

    private val popupMaxWidth: Int = dp2px(280f)
    private val popupMinWidth: Int = dp2px(112f)
    private val popupWidthUnit: Int = dp2px(56f)

    private val popup: PopupWindow = PopupWindow(context, null, R.attr.popupMenuStyle)
    var overlapAnchor: Boolean? = null
    var width = ViewGroup.LayoutParams.WRAP_CONTENT
    var gravity: Int = Gravity.NO_GRAVITY
    var verticalOffset: Int = 0
    var horizontalOffset: Int = 0
    var paddingStart: Int = 0
    var paddingEnd: Int = 0
    var paddingTop: Int = 0
    var paddingBottom: Int = 0

    private val tempRect = Rect()

    internal var adapter: PopupMenuAdapter? = null
        set(value) {
            if (width == ViewGroup.LayoutParams.WRAP_CONTENT) {
                width = measureMenuSizeAndGetWidth(checkNotNull(value))
            } else value?.setupIndices()
            field = value
        }

    init {
        popup.inputMethodMode = PopupWindow.INPUT_METHOD_NEEDED
        popup.isFocusable = true
    }

    internal fun show() {
        if (overlapAnchor != null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            popup.overlapAnchor = overlapAnchor!!
        }
        val height = buildDropDown()
        PopupWindowCompat.setWindowLayoutType(
            popup,
            WindowManager.LayoutParams.TYPE_APPLICATION_SUB_PANEL
        )
        val widthSpec = width
        if (popup.isShowing) {
            popup.isOutsideTouchable = true
            popup.update(
                anchorView, horizontalOffset,
                verticalOffset, widthSpec,
                if (height < 0) -1 else height
            )
        } else {
            popup.width = widthSpec
            popup.height = height
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                popup.setIsClippedToScreen(true)
            }
            // use outside touchable to dismiss drop down when touching outside of it, so
            // only set this if the dropdown is not always visible
            popup.isOutsideTouchable = true
            PopupWindowCompat.showAsDropDown(
                popup, anchorView, horizontalOffset,
                verticalOffset, gravity
            )
        }
    }

    /**
     * Dismiss the popupMenu window.
     */
    fun dismiss() {
        popup.dismiss()
        popup.contentView = null
    }

    /**
     * Sets a listener that is called when this popup window is dismissed.
     *
     * @param listener Listener that is called when this popup window is dismissed.
     */
    internal fun setOnDismissListener(listener: (() -> Unit)?) {
        if (listener != null) {
            popup.setOnDismissListener { listener.invoke() }
        } else {
            popup.setOnDismissListener(null)
        }
    }

    /**
     *
     * Builds the popupMenu window's content and returns the height the popupMenu
     * should have.

     * @return the content's height
     */
    private fun buildDropDown(): Int {
        var otherHeights = 0

        val dropDownList = RecyclerView(context).apply {
            elevation = popup.elevation
            overScrollMode = View.OVER_SCROLL_NEVER
            adapter = this@BoxRecyclerViewPopupWindow.adapter
            layoutManager = LinearLayoutManager(context)
            isFocusable = true
            isFocusableInTouchMode = true
            setPaddingRelative(
                this@BoxRecyclerViewPopupWindow.paddingStart,
                this@BoxRecyclerViewPopupWindow.paddingTop,
                this@BoxRecyclerViewPopupWindow.paddingEnd,
                this@BoxRecyclerViewPopupWindow.paddingBottom
            )
        }

        dropDownList.clipToOutline = true

        popup.contentView = dropDownList

        // Max height available on the screen for a popupMenu.
        val ignoreBottomDecorations = popup.inputMethodMode == PopupWindow.INPUT_METHOD_NOT_NEEDED
        val maxHeight = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            popup.getMaxAvailableHeight(
                anchorView, verticalOffset, ignoreBottomDecorations
            )
        } else {
            popup.getMaxAvailableHeight(anchorView, verticalOffset)
        }

        if (popup.background!= null) {
            popup.background.getPadding(tempRect)
            otherHeights += tempRect.top + tempRect.bottom
        } else {
            tempRect.setEmpty()
        }

        val listContent = measureHeightOfChildrenCompat(maxHeight - otherHeights)
        if (listContent > 0) {
            val listPadding = dropDownList.paddingTop + dropDownList.paddingBottom
            otherHeights += listPadding
        }

        return listContent + otherHeights
    }

    /**
     * Measures the height of the given range of children (inclusive) and returns the height
     * with this ListView's padding and divider heights included. If maxHeight is provided, the
     * measuring will stop when the current height reaches maxHeight.

     * @param maxHeight                    The maximum height that will be returned (if all the
     * *                                     children don't fit in this value, this value will be
     * *                                     returned).
     * *
     * @return The height of this ListView with the given children.
     *
     * @see androidx.appcompat.widget.DropDownListView.measureHeightOfChildrenCompat
     */
    private fun measureHeightOfChildrenCompat(maxHeight: Int): Int {

        val parent = FrameLayout(context)
        val widthMeasureSpec =
            View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY)

        // Include the padding of the list
        var returnedHeight = 0

        val count = adapter?.itemCount ?: 0
        for (i in 0 until count) {
            val positionType = adapter!!.getItemViewType(i)

            val vh = adapter!!.createViewHolder(parent, positionType)
            adapter!!.bindViewHolder(vh, i)
            val itemView = vh.itemView

            // Compute child height spec
            val heightMeasureSpec: Int
            var childLp: ViewGroup.LayoutParams? = itemView.layoutParams

            if (childLp == null) {
                childLp = generateDefaultLayoutParams()
                itemView.layoutParams = childLp
            }

            heightMeasureSpec = if (childLp.height > 0) {
                View.MeasureSpec.makeMeasureSpec(
                    childLp.height,
                    View.MeasureSpec.EXACTLY
                )
            } else {
                View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
            }
            itemView.measure(widthMeasureSpec, heightMeasureSpec)
            // Since this view was measured directly against the parent measure
            // spec, we must measure it again before reuse.
            itemView.forceLayout()

            val marginLayoutParams = childLp as? ViewGroup.MarginLayoutParams
            val topMargin = marginLayoutParams?.topMargin ?: 0
            val bottomMargin = marginLayoutParams?.bottomMargin ?: 0
            val verticalMargin = topMargin + bottomMargin

            returnedHeight += itemView.measuredHeight + verticalMargin

            if (returnedHeight >= maxHeight) {
                // We went over, figure out which height to return.  If returnedHeight >
                // maxHeight, then the i'th position did not fit completely.
                return maxHeight
            }
        }

        // At this point, we went through the range of children, and they each
        // completely fit, so return the returnedHeight
        return returnedHeight
    }

    private fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    /**
     * @see androidx.appcompat.view.menu.MenuPopup.measureIndividualMenuWidth
     */
    private fun measureMenuSizeAndGetWidth(adapter: PopupMenuAdapter): Int {
        adapter.setupIndices()
        val parent = FrameLayout(context)
        var menuWidth = popupMinWidth

        val widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        val count = adapter.itemCount
        for (i in 0 until count) {
            val positionType = adapter.getItemViewType(i)

            val vh = adapter.createViewHolder(parent, positionType)
            adapter.bindViewHolder(vh, i)
            val itemView = vh.itemView
            itemView.measure(widthMeasureSpec, heightMeasureSpec)

            val itemWidth = itemView.measuredWidth
            if (itemWidth >= popupMaxWidth) {
                return popupMaxWidth
            } else if (itemWidth > menuWidth) {
                menuWidth = itemWidth
            }
        }
        menuWidth = ceil(menuWidth.toDouble() / popupWidthUnit).toInt() * popupWidthUnit
        return menuWidth
    }
}