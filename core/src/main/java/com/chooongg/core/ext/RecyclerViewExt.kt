package com.chooongg.core.ext

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.core.widget.tabLayoutAttache.ScrollMethod
import com.fondesa.recyclerviewdivider.DividerBuilder
import com.fondesa.recyclerviewdivider.StaggeredDividerBuilder
import com.fondesa.recyclerviewdivider.dividerBuilder
import com.fondesa.recyclerviewdivider.staggeredDividerBuilder

fun RecyclerView.divider(block: DividerBuilder.() -> Unit) {
    val dividerBuilder = context.dividerBuilder()
    block(dividerBuilder)
    dividerBuilder.build().addTo(this)
}

fun RecyclerView.dividerStaggered(block: StaggeredDividerBuilder.() -> Unit) {
    val dividerBuilder = context.staggeredDividerBuilder()
    block(dividerBuilder)
    dividerBuilder.build().addTo(this)
}

internal fun RecyclerView.scrollToPosition(position: Int, scrollMethod: ScrollMethod) {
    when (scrollMethod) {
        is ScrollMethod.Direct -> scrollToPosition(position)
        is ScrollMethod.Smooth -> smoothScrollToPosition(position)
        is ScrollMethod.LimitedSmooth -> smoothScrollToPosition(position, scrollMethod.limit)
    }
}

private fun RecyclerView.smoothScrollToPosition(position: Int, scrollLimit: Int) {
    layoutManager?.apply {
        when (this) {
            is LinearLayoutManager -> {
                val topItem = findFirstVisibleItemPosition()
                val distance = topItem - position
                val anchorItem = when {
                    distance > scrollLimit -> position + scrollLimit
                    distance < -scrollLimit -> position - scrollLimit
                    else -> topItem
                }
                if (anchorItem != topItem) scrollToPosition(anchorItem)
                post {
                    smoothScrollToPosition(position)
                }
            }
            else -> smoothScrollToPosition(position)
        }
    }
}