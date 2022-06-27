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

class StatusLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
    private var initializeSuccess: Boolean = false
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        internal const val STATUS_ITEM_TAG = "status_layout_item_status"
    }

    var enableAnimation = StatusPage.config.enableAnimation

    @StatusPageConfig.AnimationType
    var animationType: Int = StatusPage.config.animationType

    private var successView = FrameLayout(context)

    // 存在的状态不会包括SuccessStatus
    private val existingStatus = HashMap<KClass<out AbstractStatus>, AbstractStatus>()

    var currentStatus: KClass<out AbstractStatus> = SuccessStatus::class
        private set

    private var onRetryEventListener: ((KClass<out AbstractStatus>) -> Unit)? = null

    private var onStatusChangeListener: ((KClass<out AbstractStatus>) -> Unit)? = null

    init {
        successView.tag = STATUS_ITEM_TAG
        addView(successView)
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
            if (successView.alpha == 1f) successView.alpha = 0f
            if (successView.translationY == 0f) successView.translationY = dp2px(4f).toFloat()
            successView.visible()
            if (isAttachedToWindow && enableAnimation) successView.showAnimation()
            currentStatus = SuccessStatus::class
        } else {
            hideAllStatus()
            createAndShowStatus(statusClass, message)
            if (existingStatus[statusClass]?.isShowSuccess() == true) {
                successView.visible()
                if (isAttachedToWindow && enableAnimation) successView.showAnimation()
            } else {
                if (isAttachedToWindow && enableAnimation) {
                    successView.hideAnimation(SuccessStatus::class)
                } else {
                    successView.gone()
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
                    if (isAttachedToWindow && enableAnimation) {
                        when (animationType) {
                            StatusPageConfig.ANIMATION_TYPE_SHARED_AXIS_X -> {
                                targetView.alpha = 0f
                                targetView.translationX = dp2px(4f).toFloat()
                            }
                            StatusPageConfig.ANIMATION_TYPE_SHARED_AXIS_Y -> {
                                targetView.alpha = 0f
                                targetView.translationY = dp2px(4f).toFloat()
                            }
                            StatusPageConfig.ANIMATION_TYPE_SHARED_AXIS_Z -> {
                                targetView.alpha = 0f
                                targetView.scaleX = 0.9f
                                targetView.scaleY = 0.9f
                            }
                            else -> targetView.alpha = 0f
                        }
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
        if (isAttachedToWindow && enableAnimation) {
            status.targetView.showAnimation()
        }
    }

    private fun hideAllStatus() {
        existingStatus.forEach { hideStatus(it.key) }
    }

    private fun hideStatus(statusClass: KClass<out AbstractStatus>) {
        val status = existingStatus[statusClass] ?: return
        status.onDetach(status.targetView)
        if (isAttachedToWindow && enableAnimation) {
            status.targetView.hideAnimation(statusClass)
        } else {
            removeView(status.targetView)
            existingStatus.remove(statusClass)
        }
    }

    private fun createStatus(statusClass: KClass<out AbstractStatus>): AbstractStatus {
        return statusClass.java.newInstance()
    }

    private fun View.showAnimation() {
        setUpAnimationAttribute()
        animate().apply {
            cancel()
            interpolator = FastOutSlowInInterpolator()
            duration = resourcesInteger(android.R.integer.config_shortAnimTime).toLong()

            when (animationType) {
                StatusPageConfig.ANIMATION_TYPE_SHARED_AXIS_X -> {
                    alpha(1f).translationX(0f)
                }
                StatusPageConfig.ANIMATION_TYPE_SHARED_AXIS_Y -> {
                    alpha(1f).translationY(0f)
                }
                StatusPageConfig.ANIMATION_TYPE_SHARED_AXIS_Z -> {
                    alpha(1f).scaleX(1f).scaleY(1f)
                }
                else -> alpha(1f)
            }
        }
    }

    private fun View.hideAnimation(statusClass: KClass<out AbstractStatus>) {
        animate().apply {
            cancel()
            interpolator = FastOutSlowInInterpolator()
            duration =
                resourcesInteger(android.R.integer.config_shortAnimTime).toLong()
            when (animationType) {
                StatusPageConfig.ANIMATION_TYPE_SHARED_AXIS_X -> {
                    alpha(0f).translationX(-dp2px(4f).toFloat())
                }
                StatusPageConfig.ANIMATION_TYPE_SHARED_AXIS_Y -> {
                    alpha(0f).translationY(dp2px(4f).toFloat())
                }
                StatusPageConfig.ANIMATION_TYPE_SHARED_AXIS_Z -> {
                    alpha(0f).scaleX(0.9f).scaleY(0.9f)
                }
                else -> alpha(0f)
            }
            withEndAction {
                if (statusClass == SuccessStatus::class) {
                    successView.gone()
                } else {
                    val status = existingStatus[statusClass] ?: return@withEndAction
                    removeView(status.targetView)
                    existingStatus.remove(statusClass)
                }
            }
        }
    }

    private fun View.setUpAnimationAttribute() {
        when (animationType) {
            StatusPageConfig.ANIMATION_TYPE_SHARED_AXIS_X -> {
                if (translationX == -dp2px(4f).toFloat()) translationX = dp2px(4f).toFloat()
            }
        }
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child != null && child.tag != STATUS_ITEM_TAG) {
            successView.addView(child, params)
            return
        }
        super.addView(child, index, params)
    }

    override fun removeView(view: View?) {
        if (view == successView) return
        successView.removeView(view)
        super.removeView(view)
    }

    override fun removeAllViewsInLayout() {
        super.removeAllViewsInLayout()
        addView(successView)
    }

    override fun removeViewInLayout(view: View?) {
        if (view == successView) return
        successView.removeViewInLayout(view)
        super.removeViewInLayout(view)
    }
}