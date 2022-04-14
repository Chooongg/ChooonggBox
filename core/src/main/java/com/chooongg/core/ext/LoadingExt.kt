package com.chooongg.core.ext

import android.animation.Animator
import android.annotation.SuppressLint
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.chooongg.core.R
import com.chooongg.ext.*

/**
 * 显示加载框
 */
fun Activity.showLoading(
    message: CharSequence?
) {
    if (this is ComponentActivity) {
        lifecycleScope.launchMain { showLoadingForActivity(message) }
    } else showLoadingForActivity(message)
}

fun Activity.showLoading(@StringRes resId: Int, vararg format: Any?) {
    if (format.isNullOrEmpty()) {
        showLoading(resourcesString(resId))
    } else {
        showLoading(resourcesString(resId, *format))
    }
}

/**
 * 隐藏加载框
 */
fun Activity.hideLoading() {
    if (this is ComponentActivity) {
        lifecycleScope.launchMain { hideLoadingForActivity() }
    } else hideLoadingForActivity()
}

/**
 * 显示加载框
 */
fun Fragment.showLoading(message: CharSequence?) {
    activity?.showLoading(message)
}

fun Fragment.showLoading(@StringRes resId: Int, vararg format: Any?) {
    activity?.showLoading(resId, *format)
}

/**
 * 隐藏加载框
 */
fun Fragment.hideLoading() {
    activity?.hideLoading()
}

@SuppressLint("InflateParams")
private fun Activity.showLoadingForActivity(message: CharSequence?) {
    var loadingView = decorView.findViewById<View>(R.id.box_activity_loading)
    if (loadingView != null) {
        loadingView.animate().setListener(null).cancel()
    } else {
        loadingView =
            LayoutInflater.from(this).inflate(R.layout.box_activity_loading, null)
        decorView.addView(loadingView)
    }
    val textView = loadingView.findViewById<TextView>(R.id.tv_message)
    if (message.isNullOrEmpty()) {
        textView.gone()
    } else {
        textView.text = message
        textView.visible()
    }
    loadingView.isClickable = true
    loadingView.animate().setInterpolator(AccelerateDecelerateInterpolator())
        .alpha(1f).scaleX(1f).scaleY(1f)
}

private fun Activity.hideLoadingForActivity() {
    decorView.findViewById<View>(R.id.box_activity_loading)?.apply {
        animate().setInterpolator(AccelerateDecelerateInterpolator())
            .alpha(0f).scaleX(0.9f).scaleY(0.9f)
            .setListener(object : Animator.AnimatorListener {
                override fun onAnimationStart(animation: Animator?) = Unit
                override fun onAnimationCancel(animation: Animator?) = Unit
                override fun onAnimationRepeat(animation: Animator?) = Unit
                override fun onAnimationEnd(animation: Animator?) {
                    decorView.removeView(this@apply)
                }
            })
    }
}