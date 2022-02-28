package com.chooongg.ext

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import com.chooongg.manager.ActivityLifecycleManager

/**
 * Toast 唯一实例
 */
private var boxToast: Toast? = null

/**
 * 展示 Toast
 */
fun showToast(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    if (boxToast != null) {
        boxToast!!.cancel()
        boxToast!!.setText(text)
        boxToast!!.duration = duration
    } else {
        boxToast = Toast.makeText(
            ActivityLifecycleManager.activityTop as? Context ?: APPLICATION,
            text,
            duration
        )
    }
    boxToast!!.show()
}

/**
 * 展示 Toast
 */
fun showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    if (boxToast != null) {
        boxToast!!.cancel()
        boxToast!!.setText(resId)
        boxToast!!.duration = duration
    } else {
        boxToast = Toast.makeText(
            ActivityLifecycleManager.activityTop as? Context ?: APPLICATION,
            resId,
            duration
        )
    }
    boxToast!!.show()
}

/**
 * 取消Toast
 */
fun cancelToast() {
    boxToast?.cancel()
    boxToast = null
}