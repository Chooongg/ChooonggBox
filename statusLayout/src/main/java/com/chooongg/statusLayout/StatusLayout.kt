package com.chooongg.statusLayout

import android.animation.AnimatorInflater
import android.animation.LayoutTransition
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.widget.NestedScrollView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.chooongg.ext.doOnClick
import com.chooongg.ext.gone
import com.chooongg.ext.inVisible
import com.chooongg.ext.visible
import com.chooongg.statusLayout.status.AbstractStatus
import com.chooongg.statusLayout.status.SuccessStatus
import kotlin.reflect.KClass

class StatusLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : NestedScrollView(context, attrs, defStyleAttr) {

    private val rootView: FrameLayout = FrameLayout(context)

    var enableAnimation = StatusPage.config.enableAnimation

    private var successView: View? = null

    // 存在的状态不会包括SuccessStatus
    private val existingStatus = HashMap<KClass<out AbstractStatus>, AbstractStatus>()

    private var currentStatus: KClass<out AbstractStatus>? = null

    private var onRetryEventListener: ((KClass<out AbstractStatus>) -> Unit)? = null

    private var onStatusChangeListener: ((KClass<out AbstractStatus>) -> Unit)? = null

    init {
        isFillViewport = true
        addView(rootView, -1, generateDefaultLayoutParams())
        setEnableAnimator(enableAnimation)
        if (!isInEditMode) show(StatusPage.config.defaultState)
    }

    fun setOnRetryListener(block: (KClass<out AbstractStatus>) -> Unit) {
        onRetryEventListener = block
    }

    fun setOnStatusChangeListener(block: (KClass<out AbstractStatus>) -> Unit) {
        onStatusChangeListener = block
    }

    fun showSuccess() {
        show(SuccessStatus::class)
    }

    fun show(statusClass: KClass<out AbstractStatus>, message: CharSequence? = null) {
        if (currentStatus == statusClass) return
        if (statusClass == SuccessStatus::class) {
            hideAllStatus()
            successView?.visible()
            currentStatus = SuccessStatus::class
        } else {
            hideAllStatus()
            createAndShowStatus(statusClass, message)
            if (existingStatus[statusClass]?.isShowSuccess() == true) {
                successView?.visible()
            } else successView?.gone()
        }
        onStatusChangeListener?.invoke(statusClass)
    }

    private fun createAndShowStatus(
        statusClass: KClass<out AbstractStatus>,
        message: CharSequence?
    ) {
        if (existingStatus[statusClass] == null) {
            existingStatus[statusClass] =
                createStatus(statusClass).apply {
                    obtainTargetView(context)
                    onAttach(targetView, message)
                    getReloadEventView(targetView)?.doOnClick {
                        onRetryEventListener?.invoke(statusClass)
                    }
                }
        }
        val status = existingStatus[statusClass]!!
        rootView.addView(status.targetView, LayoutParams(-2, -2, Gravity.CENTER))
        currentStatus = statusClass
    }

    private fun hideAllStatus() {
        existingStatus.forEach { hideStatus(it.key) }
    }

    private fun hideStatus(statusClass: KClass<out AbstractStatus>) {
        val status = existingStatus[statusClass] ?: return
        status.onDetach(status.targetView)

        rootView.removeView(status.targetView)
        existingStatus.remove(statusClass)
    }

    private fun createStatus(statusClass: KClass<out AbstractStatus>): AbstractStatus {
        return statusClass.java.newInstance()
    }

    override fun addView(child: View?) {
        addView(child, -1)
    }

    override fun addView(child: View?, index: Int) {
        addView(child, index, generateDefaultLayoutParams())
    }

    override fun addView(child: View?, width: Int, height: Int) {
        addView(child, -1, generateDefaultLayoutParams().apply {
            this.width = width
            this.height = height
        })
    }

    override fun addView(child: View?, params: ViewGroup.LayoutParams?) {
        addView(child, -1, params)
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child == rootView) {
            super.addView(child, index, params)
            return
        }
        if (successView == null) {
            if (child == null) return
            successView = child
            if (child.parent != null && child.parent is ViewGroup) {
                (child.parent as ViewGroup).removeView(child)
            }
            rootView.addView(successView, 0, params)
            if (currentStatus == SuccessStatus::class) {
                successView!!.visible()
            } else {
                successView!!.inVisible()
            }
        } else throw IllegalStateException("StatusLayout can host only one direct child")
    }

    fun setEnableAnimator(enable: Boolean) {
        this.enableAnimation = enable
        if (enable) {
            rootView.layoutTransition = LayoutTransition().apply {
                setStartDelay(LayoutTransition.APPEARING, 0)
                setAnimator(
                    LayoutTransition.APPEARING,
                    AnimatorInflater.loadAnimator(context, R.animator.status_layout_in)
                )

                setStartDelay(LayoutTransition.DISAPPEARING, 0)
                setAnimator(
                    LayoutTransition.DISAPPEARING,
                    AnimatorInflater.loadAnimator(context, R.animator.status_layout_out)
                )
            }
        } else {
            rootView.layoutTransition = null
        }
    }
}