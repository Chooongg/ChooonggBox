package com.chooongg.statusLayout

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.core.view.children
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.chooongg.ext.*
import com.chooongg.statusLayout.status.AbstractStatus
import com.chooongg.statusLayout.status.SuccessStatus
import kotlin.reflect.KClass

class StatusLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        internal const val STATUS_ITEM_TAG = "status_layout_item_status"
    }

    private var isCreated = false
    private var initStatus: KClass<out AbstractStatus>? = null
    private var initStatusMessage: CharSequence? = null

    var enableAnimation = StatusPage.config.enableAnimation
        get() = isCreated && StatusPage.config.enableAnimation

    private var successViews: ArrayList<View> = ArrayList()

    // 存在的状态不会包括SuccessStatus
    private val existingStatus = HashMap<KClass<out AbstractStatus>, AbstractStatus>()

    private var currentStatus: KClass<out AbstractStatus> = SuccessStatus::class

    private var onRetryEventListener: ((KClass<out AbstractStatus>) -> Unit)? = null

    private var onStatusChangeListener: ((KClass<out AbstractStatus>) -> Unit)? = null

    internal fun onBindFinished() {
        successViews.clear()
        children.forEach {
            if (it.tag != STATUS_ITEM_TAG) {
                successViews.add(it)
            }
        }
        if (!isInEditMode) show(StatusPage.config.defaultState)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        onBindFinished()
        isCreated = true
        initStatus?.let { show(it, initStatusMessage) }
    }

    private fun getEnableAnimation(isForceNoAnim: Boolean) =
        if (isForceNoAnim) false else enableAnimation

    fun setOnRetryListener(block: (KClass<out AbstractStatus>) -> Unit) {
        onRetryEventListener = block
    }

    fun setOnStatusChangeListener(block: (KClass<out AbstractStatus>) -> Unit) {
        onStatusChangeListener = block
    }

    fun beginShowSuccess() {
        val animation = enableAnimation
        enableAnimation = false
        showSuccess()
        enableAnimation = animation
    }

    fun showSuccess() = show(SuccessStatus::class)


    fun beginShow(statusClass: KClass<out AbstractStatus>, message: CharSequence? = null) {
        val animation = enableAnimation
        enableAnimation = false
        show(statusClass, message)
        enableAnimation = animation
    }

    fun show(statusClass: KClass<out AbstractStatus>, message: CharSequence? = null) {
        if (!isCreated) {
            initStatus = statusClass
            initStatusMessage = message
            return
        }
        if (currentStatus == statusClass) return
        if (statusClass == SuccessStatus::class) {
            hideAllStatus()
            successViews.forEach {
                it.visible()
                if (enableAnimation) {
                    it.animate().cancel()
                    it.animate().setInterpolator(FastOutSlowInInterpolator()).setDuration(
                        resourcesInteger(android.R.integer.config_shortAnimTime).toLong()
                    ).alpha(1f).translationY(0f)
                }
            }
            currentStatus = SuccessStatus::class
        } else {
            hideAllStatus()
            createAndShowStatus(statusClass, message)
            if (existingStatus[statusClass]?.isShowSuccess() == true) {
                successViews.forEach {
                    it.visible()
                    if (enableAnimation) {
                        it.animate().cancel()
                        it.animate().setInterpolator(FastOutSlowInInterpolator()).setDuration(
                            resourcesInteger(android.R.integer.config_shortAnimTime).toLong()
                        ).alpha(1f).translationY(0f)
                    }
                }
            } else {
                successViews.forEach {
                    if (enableAnimation) {
                        it.animate().cancel()
                        it.animate().setInterpolator(FastOutSlowInInterpolator()).setDuration(
                            resourcesInteger(android.R.integer.config_shortAnimTime).toLong()
                        ).alpha(0f).translationY(dp2px(4f).toFloat())
                            .withEndAction { it.gone() }
                    } else {
                        it.gone()
                    }
                }
            }
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
                    if (enableAnimation) {
                        targetView.alpha = 0f
                        targetView.translationY = dp2px(4f).toFloat()
                    }
                    onAttach(targetView, message)
                    getReloadEventView(targetView)?.doOnClick {
                        onRetryEventListener?.invoke(statusClass)
                    }
                }
        }
        val status = existingStatus[statusClass]!!
        if (status.targetView.parent == null) {
            status.targetView.tag = STATUS_ITEM_TAG
            addView(status.targetView, LayoutParams(-2, -2, Gravity.CENTER))
        }
        currentStatus = statusClass
        if (enableAnimation) {
            status.targetView.animate().cancel()
            status.targetView.animate().setInterpolator(FastOutSlowInInterpolator()).setDuration(
                resourcesInteger(android.R.integer.config_shortAnimTime).toLong()
            ).alpha(1f).translationY(0f)
        }
    }

    private fun hideAllStatus() {
        existingStatus.forEach { hideStatus(it.key) }
    }

    private fun hideStatus(statusClass: KClass<out AbstractStatus>) {
        val status = existingStatus[statusClass] ?: return
        status.onDetach(status.targetView)
        if (enableAnimation) {
            status.targetView.animate().cancel()
            status.targetView.animate().setInterpolator(FastOutSlowInInterpolator()).setDuration(
                resourcesInteger(android.R.integer.config_shortAnimTime).toLong()
            ).alpha(0f).translationY(dp2px(4f).toFloat())
                .withEndAction {
                    removeView(status.targetView)
                    existingStatus.remove(statusClass)
                }
        } else {
            removeView(status.targetView)
            existingStatus.remove(statusClass)
        }
    }

    private fun createStatus(statusClass: KClass<out AbstractStatus>): AbstractStatus {
        return statusClass.java.newInstance()
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (currentStatus != SuccessStatus::class && child?.tag == STATUS_ITEM_TAG) child.gone()
        super.addView(child, index, params)
    }

    override fun removeView(view: View?) {
        super.removeView(view)
    }

    override fun removeViewAt(index: Int) {
        super.removeViewAt(index)
    }

    override fun removeAllViews() {
        super.removeAllViews()
    }

    override fun removeAllViewsInLayout() {
        super.removeAllViewsInLayout()
    }

    override fun removeViews(start: Int, count: Int) {
        super.removeViews(start, count)
    }

    override fun removeViewInLayout(view: View?) {
        super.removeViewInLayout(view)
    }

    override fun removeViewsInLayout(start: Int, count: Int) {
        super.removeViewsInLayout(start, count)
    }
}