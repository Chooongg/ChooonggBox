package com.chooongg.ext

import android.app.Activity
import android.graphics.Rect

/**
 * 软键盘显示监听
 */
fun Activity.onKeyboardShowListener(listener: (isShow: Boolean) -> Unit) {
    contentView.viewTreeObserver.addOnGlobalLayoutListener {
        val rect = Rect()
        window.decorView.getWindowVisibleDisplayFrame(rect)
        val screenHeight = window.decorView.rootView.height
        val heightDifference = screenHeight - rect.bottom
        if (heightDifference > screenHeight / 3) {
            listener.invoke(true)
        } else {
            listener.invoke(false)
        }
    }
}