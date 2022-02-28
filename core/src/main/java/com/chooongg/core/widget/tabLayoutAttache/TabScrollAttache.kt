package com.chooongg.core.widget.tabLayoutAttache

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chooongg.core.ext.scrollToPosition
import com.google.android.material.tabs.TabLayout

class TabScrollAttache(
    private val tabLayout: TabLayout,
    private val recyclerView: RecyclerView,
    private val tabStartIndexOffsets: List<Int>,
    configuration: Configuration.() -> Unit = {}
) {

    private lateinit var layoutManager: LinearLayoutManager

    private lateinit var config: Configuration

    private var attacheState = AttacheState.IDLE

    private var isAttached = false

    private var scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            if (attacheState == AttacheState.TAB_SELECTED) {
                return
            }

            val isScrolling = dx != 0 || dy != 0
            if (attacheState == AttacheState.IDLE && isScrolling) {
                attacheState = AttacheState.RECYCLERVIEW_SCROLLING
            }

            val calculatedRecyclerViewItemPosition = when {
                layoutManager.findLastVisibleItemPosition() == layoutManager.itemCount - 1 -> layoutManager.findLastVisibleItemPosition()
                layoutManager.findFirstVisibleItemPosition() == 0 -> layoutManager.findFirstVisibleItemPosition()
                else -> findMidVisibleRecyclerItemPosition()
            }

            val tabIndex = getTabIndex(calculatedRecyclerViewItemPosition)
            if (tabIndex != tabLayout.selectedTabPosition) {
                tabLayout.getTabAt(tabIndex)?.select()
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                attacheState = AttacheState.IDLE
            }
        }
    }

    private var tabSelectedListener = object : TabLayout.OnTabSelectedListener {
        override fun onTabSelected(p0: TabLayout.Tab?) {
            if (attacheState != AttacheState.RECYCLERVIEW_SCROLLING) {
                attacheState = AttacheState.TAB_SELECTED
                val recyclerViewPosition = tabStartIndexOffsets[tabLayout.selectedTabPosition]
                recyclerView.scrollToPosition(recyclerViewPosition, config.scrollMethod)
            }
        }

        override fun onTabUnselected(tab: TabLayout.Tab?) {
        }

        override fun onTabReselected(tab: TabLayout.Tab?) {
        }
    }

    init {
        require(recyclerView.layoutManager is LinearLayoutManager) { "Only LinearLayoutManager is supported." }
        layoutManager = recyclerView.layoutManager as LinearLayoutManager
        config = Configuration().apply(configuration)
    }

    fun detach() {
        if (isAttached) {
            recyclerView.removeOnScrollListener(scrollListener)
            tabLayout.removeOnTabSelectedListener(tabSelectedListener)
            isAttached = false
        }
    }

    fun attach() {
        if (isAttached.not()) {
            recyclerView.addOnScrollListener(scrollListener)
            tabLayout.addOnTabSelectedListener(tabSelectedListener)
            isAttached = true
        }
    }

    private fun findMidVisibleRecyclerItemPosition(): Int {
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
        return (firstVisibleItemPosition + lastVisibleItemPosition) / 2
    }

    private fun getTabIndex(recyclerItemPosition: Int): Int {
        var calculatedTabIndex = -1
        tabStartIndexOffsets.forEachIndexed { tabIndex, startOffset ->
            if (recyclerItemPosition >= startOffset) {
                calculatedTabIndex = tabIndex
            }
        }
        return calculatedTabIndex
    }

    class Configuration {
        internal var scrollMethod: ScrollMethod = ScrollMethod.Smooth
            private set

        fun scrollDirectly() {
            scrollMethod = ScrollMethod.Direct
        }

        fun scrollSmoothly(limit: Int? = null) {
            scrollMethod = when (limit) {
                null -> ScrollMethod.Smooth
                else -> ScrollMethod.LimitedSmooth(limit)
            }
        }
    }

}