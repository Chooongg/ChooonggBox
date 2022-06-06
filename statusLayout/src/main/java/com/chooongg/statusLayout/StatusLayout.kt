package com.chooongg.statusLayout

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.chooongg.ext.*
import com.chooongg.statusLayout.status.AbstractStatus
import com.chooongg.statusLayout.status.SuccessStatus
import kotlin.reflect.KClass

open class StatusLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private var initializeSuccess: Boolean = false
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        internal const val STATUS_ITEM_TAG = "status_layout_item_status"
    }

    var enableAnimation = StatusPage.config.enableAnimation
        get() = isAttachedToWindow && StatusPage.config.enableAnimation

    private var successViews: ArrayList<View> = ArrayList()

    // 存在的状态不会包括SuccessStatus
    private val existingStatus = HashMap<KClass<out AbstractStatus>, AbstractStatus>()

    var currentStatus: KClass<out AbstractStatus> = SuccessStatus::class
        private set

    private var onRetryEventListener: ((KClass<out AbstractStatus>) -> Unit)? = null

    private var onStatusChangeListener: ((KClass<out AbstractStatus>) -> Unit)? = null

    init {
        val a = context.obtainStyledAttributes(attrs, R.styleable.StatusLayout, defStyleAttr, 0)
        initializeSuccess =
            a.getBoolean(R.styleable.StatusLayout_initializeSuccess, initializeSuccess)
        a.recycle()
        if (!isInEditMode && !initializeSuccess && StatusPage.config.defaultState != SuccessStatus::class) {
            createAndShowStatus(StatusPage.config.defaultState, null)
        }
    }

    fun setOnRetryListener(block: (KClass<out AbstractStatus>) -> Unit) {
        onRetryEventListener = block
    }

    fun setOnStatusChangeListener(block: (KClass<out AbstractStatus>) -> Unit) {
        onStatusChangeListener = block
    }

    fun showSuccess() = show(SuccessStatus::class)

    fun show(statusClass: KClass<out AbstractStatus>, message: CharSequence? = null) {
        if (currentStatus == statusClass) return
        if (statusClass == SuccessStatus::class) {
            hideAllStatus()
            successViews.forEach {
                if (it.alpha == 1f) it.alpha = 0f
                if (it.translationY == 0f) it.translationY = dp2px(4f).toFloat()
                it.visible()
                if (enableAnimation) it.animate().apply {
                    cancel()
                    interpolator = FastOutSlowInInterpolator()
                    duration = resourcesInteger(android.R.integer.config_shortAnimTime).toLong()
                    alpha(1f).translationY(0f)
                }
            }
            currentStatus = SuccessStatus::class
        } else {
            hideAllStatus()
            createAndShowStatus(statusClass, message)
            if (existingStatus[statusClass]?.isShowSuccess() == true) {
                successViews.forEach {
                    if (it.alpha == 1f) it.alpha = 0f
                    if (it.translationY == 0f) it.translationY = dp2px(4f).toFloat()
                    it.visible()
                    if (enableAnimation) it.animate().apply {
                        cancel()
                        interpolator = FastOutSlowInInterpolator()
                        duration = resourcesInteger(android.R.integer.config_shortAnimTime).toLong()
                        alpha(1f).translationY(0f)
                    }
                }
            } else {
                successViews.forEach {
                    if (enableAnimation) {
                        it.animate().apply {
                            cancel()
                            interpolator = FastOutSlowInInterpolator()
                            duration =
                                resourcesInteger(android.R.integer.config_shortAnimTime).toLong()
                            alpha(0f).translationY(dp2px(4f).toFloat())
                            withEndAction { it.gone() }
                        }
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
            addView(status.targetView, LayoutParams(-2, -2, Gravity.CENTER))
        }
        currentStatus = statusClass
        if (enableAnimation) {
            status.targetView.animate().apply {
                cancel()
                interpolator = FastOutSlowInInterpolator()
                duration = resourcesInteger(android.R.integer.config_shortAnimTime).toLong()
                alpha(1f).translationY(0f)
            }
        }
    }

    private fun hideAllStatus() {
        existingStatus.forEach { hideStatus(it.key) }
    }

    private fun hideStatus(statusClass: KClass<out AbstractStatus>) {
        val status = existingStatus[statusClass] ?: return
        status.onDetach(status.targetView)
        if (enableAnimation) {
            status.targetView.animate().apply {
                cancel()
                interpolator = FastOutSlowInInterpolator()
                duration =
                    resourcesInteger(android.R.integer.config_shortAnimTime).toLong()
                alpha(0f).translationY(dp2px(4f).toFloat())
                withEndAction {
                    removeView(status.targetView)
                    existingStatus.remove(statusClass)
                }
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
        if (child != null && child.tag != STATUS_ITEM_TAG) {
            if (currentStatus != SuccessStatus::class) {
                child.gone()
            }
            successViews.add(child)
        }
        super.addView(child, index, params)
    }

    override fun removeView(view: View?) {
        successViews.remove(view)
        super.removeView(view)
    }

    override fun removeViewAt(index: Int) {
        successViews.remove(getChildAt(index))
        super.removeViewAt(index)
    }

    override fun removeAllViewsInLayout() {
        successViews.clear()
        super.removeAllViewsInLayout()
    }

    override fun removeViews(start: Int, count: Int) {
        for (i in start until start + count) {
            successViews.remove(getChildAt(i))
        }
        super.removeViews(start, count)
    }

    override fun removeViewInLayout(view: View?) {
        successViews.remove(view)
        super.removeViewInLayout(view)
    }

    override fun removeViewsInLayout(start: Int, count: Int) {
        for (i in start until start + count) {
            successViews.remove(getChildAt(i))
        }
        super.removeViewsInLayout(start, count)
    }
}