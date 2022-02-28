package com.chooongg.ext

import android.content.res.Resources
import android.util.TypedValue

fun dp2px(dp: Float) = (dp * getScreenDensity() + 0.5f).toInt()

fun px2dp(px: Int) = (px / getScreenDensity()).toInt() + 0.5f

fun sp2px(sp: Float) = (sp * getScreenScaledDensity() + 0.5f).toInt()

fun px2sp(px: Int) = (px / getScreenScaledDensity()).toInt() + 0.5f

fun applyDimension(value: Float, unit: Int): Float {
    val metrics = Resources.getSystem().displayMetrics
    when (unit) {
        TypedValue.COMPLEX_UNIT_PX -> return value
        TypedValue.COMPLEX_UNIT_DIP -> return value * metrics.density
        TypedValue.COMPLEX_UNIT_SP -> return value * metrics.scaledDensity
        TypedValue.COMPLEX_UNIT_PT -> return value * metrics.xdpi * (1.0f / 72)
        TypedValue.COMPLEX_UNIT_IN -> return value * metrics.xdpi
        TypedValue.COMPLEX_UNIT_MM -> return value * metrics.xdpi * (1.0f / 25.4f)
    }
    return 0f
}