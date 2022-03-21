package com.chooongg.core.adapter.loop

import android.view.View
import kotlin.math.abs

/**
 * The default view picker used when one is not provided.
 * @return A view with the given adapter index.
 */
fun defaultPicker(
    targetAdapterIndex: Int,
    layoutManager: LoopingLayoutManager
): View? {
    return childClosestToMiddle(targetAdapterIndex, layoutManager)
}

/**
 * Returns a view with the given [targetAdapterIndex]. If there are multiple views associated with the
 * given index, this returns the view closest to the anchor edge.
 *
 * The anchor edge is the edge the view associated with index 0 would be initially laid out
 * against. For example: In a RTL horizontal layout, the anchor edge would be the right edge.
 */
fun childClosestToAnchorEdge(
    targetAdapterIndex: Int,
    layoutManager: LoopingLayoutManager
): View? {
    val movementDir = layoutManager.convertAdapterDirToMovementDir(
        LoopingLayoutManager.TOWARDS_HIGHER_INDICES
    )
    val range = if (movementDir == LoopingLayoutManager.TOWARDS_HIGHER_INDICES) {
        0 until layoutManager.childCount
    } else {
        layoutManager.childCount - 1 downTo 0
    }

    for (i in range) {
        val view = layoutManager.getChildAt(i) ?: break
        if (layoutManager.getPosition(view) == targetAdapterIndex) {
            return view
        }
    }
    return null
}

/**
 * Returns a view with the given [targetAdapterIndex]. If there are multiple views associated with the
 * given index, this returns the view whose middle is closest to the middle of the layout.
 */
fun childClosestToMiddle(
    targetAdapterIndex: Int,
    layoutManager: LoopingLayoutManager
): View? {
    var minDistance = Int.MAX_VALUE
    var closestView: View? = null
    val layoutMiddle = if (layoutManager.orientation == LoopingLayoutManager.HORIZONTAL) {
        layoutManager.paddingLeft + (layoutManager.layoutWidth / 2)
    } else {
        layoutManager.paddingTop + (layoutManager.layoutHeight / 2)
    }
    for (i in 0 until layoutManager.childCount) {
        val view = layoutManager.getChildAt(i) ?: return null
        if (layoutManager.getPosition(view) != targetAdapterIndex) {
            continue
        }
        val childMiddle = if (layoutManager.orientation == LoopingLayoutManager.HORIZONTAL) {
            layoutManager.getDecoratedLeft(view) +
                    (layoutManager.getDecoratedMeasuredWidth(view) / 2)
        } else {
            layoutManager.getDecoratedTop(view) +
                    (layoutManager.getDecoratedMeasuredHeight(view) / 2)
        }
        val distance = abs(childMiddle - layoutMiddle)
        if (distance < minDistance) {
            minDistance = distance
            closestView = view
        }
    }
    return closestView
}
