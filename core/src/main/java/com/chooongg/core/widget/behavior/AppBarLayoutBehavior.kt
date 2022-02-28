package com.chooongg.core.widget.behavior

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.OverScroller
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.google.android.material.appbar.AppBarLayout
import java.lang.reflect.Field

class AppBarLayoutBehavior(context: Context, attrs: AttributeSet?) :
    AppBarLayout.Behavior(context, attrs) {

    companion object {
        const val TYPE_FLING = 1
    }

    private var isFlinging = false
    private var shouldBlockNestedScroll = false

    /**
     * 反射获取私有的flingRunnable 属性，考虑support 28以后变量名修改的问题
     */
    @Throws(NoSuchFieldException::class)
    private fun getFlingRunnableField(): Field? {
        return try {
            // support design 27及以下版本
            val headerBehaviorType: Class<*> = this.javaClass.superclass.superclass
            headerBehaviorType.getDeclaredField("mFlingRunnable")
        } catch (e: NoSuchFieldException) {
            // 可能是28及以上版本
            val headerBehaviorType: Class<*> = this.javaClass.superclass.superclass.superclass
            headerBehaviorType.getDeclaredField("flingRunnable")
        }
    }

    /**
     * 反射获取私有的scroller 属性，考虑support 28以后变量名修改的问题
     * @return Field
     */
    @Throws(NoSuchFieldException::class)
    private fun getScrollerField(): Field? {
        return try {
            // support design 27及以下版本
            val headerBehaviorType: Class<*> = this.javaClass.superclass.superclass
            headerBehaviorType.getDeclaredField("mScroller")
        } catch (e: NoSuchFieldException) {
            // 可能是28及以上版本
            val headerBehaviorType: Class<*> = this.javaClass.superclass.superclass.superclass
            headerBehaviorType.getDeclaredField("scroller")
        }
    }

    /**
     * 停止appbarLayout的fling事件
     * @param appBarLayout
     */
    private fun stopAppbarLayoutFling(appBarLayout: AppBarLayout) {
        //通过反射拿到HeaderBehavior中的flingRunnable变量
        try {
            val flingRunnableField = getFlingRunnableField()
            val scrollerField = getScrollerField()
            flingRunnableField!!.isAccessible = true
            scrollerField!!.isAccessible = true
            val flingRunnable = flingRunnableField[this] as? Runnable
            val overScroller = scrollerField[this] as? OverScroller
            if (flingRunnable != null) {
                appBarLayout.removeCallbacks(flingRunnable)
                flingRunnableField[this] = null
            }
            if (overScroller != null && !overScroller.isFinished) {
                overScroller.abortAnimation()
            }
        } catch (e: NoSuchFieldException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        }
    }

    override fun onStartNestedScroll(
        parent: CoordinatorLayout,
        child: AppBarLayout,
        directTargetChild: View,
        target: View,
        nestedScrollAxes: Int,
        type: Int
    ): Boolean {
        stopAppbarLayoutFling(child)
        return super.onStartNestedScroll(
            parent,
            child,
            directTargetChild,
            target,
            nestedScrollAxes,
            type
        )
    }

    override fun onNestedPreScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dx: Int,
        dy: Int,
        consumed: IntArray,
        type: Int
    ) {

        //type返回1时，表示当前target处于非touch的滑动，
        //该bug的引起是因为appbar在滑动时，CoordinatorLayout内的实现NestedScrollingChild2接口的滑动子类还未结束其自身的fling
        //所以这里监听子类的非touch时的滑动，然后block掉滑动事件传递给AppBarLayout
        if (type == TYPE_FLING) {
            isFlinging = true
        }
        if (!shouldBlockNestedScroll) {
            super.onNestedPreScroll(coordinatorLayout, child, target, dx, dy, consumed, type)
        }
    }

    override fun onNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        child: AppBarLayout,
        target: View,
        dxConsumed: Int,
        dyConsumed: Int,
        dxUnconsumed: Int,
        dyUnconsumed: Int,
        type: Int
    ) {
        if (!shouldBlockNestedScroll) {
            @Suppress("DEPRECATION")
            super.onNestedScroll(
                coordinatorLayout,
                child,
                target,
                dxConsumed,
                dyConsumed,
                dxUnconsumed,
                dyUnconsumed,
                type
            )
        }
    }

    override fun onStopNestedScroll(
        coordinatorLayout: CoordinatorLayout,
        abl: AppBarLayout,
        target: View,
        type: Int
    ) {
        super.onStopNestedScroll(coordinatorLayout, abl, target, type)
        isFlinging = false
        shouldBlockNestedScroll = false
    }

}