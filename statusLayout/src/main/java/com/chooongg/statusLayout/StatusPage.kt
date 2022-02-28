package com.chooongg.statusLayout

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import com.chooongg.statusLayout.status.AbstractStatus
import kotlin.reflect.KClass

object StatusPage {

    internal val config: StatusPageConfig = StatusPageConfig()

    fun configDefault(block: StatusPageConfig.() -> Unit) {
        block(config)
    }

    /**
     * View 绑定状态布局
     */
    fun bindStatePage(
        targetView: View,
        onRetryEventListener: ((KClass<out AbstractStatus>) -> Unit)? = null
    ): StatusLayout {
        val parent = targetView.parent as ViewGroup?
        var targetViewIndex = 0
        val statusLayout = StatusLayout(targetView.context)
        statusLayout.addView(targetView)
        if (onRetryEventListener != null) {
            statusLayout.setOnRetryListener(onRetryEventListener)
        }
        parent?.let { targetViewParent ->
            for (i in 0 until targetViewParent.childCount) {
                if (targetViewParent.getChildAt(i) == targetView) {
                    targetViewIndex = i
                    break
                }
            }
            targetViewParent.removeView(targetView)
            targetViewParent.addView(statusLayout, targetViewIndex, targetView.layoutParams)
        }
        return statusLayout
    }

    /**
     * Activity 绑定状态布局
     */
    fun bindStatePage(
        activity: Activity,
        onRetryEventListener: ((KClass<out AbstractStatus>) -> Unit)? = null
    ): StatusLayout {
        val targetView = activity.findViewById<ViewGroup>(android.R.id.content)
        val targetViewIndex = 0
        val oldContent: View = targetView.getChildAt(targetViewIndex)
        targetView.removeView(oldContent)
        val oldLayoutParams = oldContent.layoutParams
        val statusLayout = StatusLayout(oldContent.context)
        statusLayout.addView(oldContent)
        if (onRetryEventListener != null) {
            statusLayout.setOnRetryListener(onRetryEventListener)
        }
        targetView.addView(statusLayout, targetViewIndex, oldLayoutParams)
        return statusLayout
    }

}