package com.chooongg.ext

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatDelegate
import com.chooongg.manager.BoxMMKV

/**
 * 判断当前是否深色模式
 */
fun isNightMode(): Boolean {
    return when (APPLICATION.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) {
        Configuration.UI_MODE_NIGHT_YES -> true
        else -> false
    }
}

/**
 * 获取当前的夜间模式
 */
fun getNightMode() = AppCompatDelegate.getDefaultNightMode()

/**
 * 设置深色模式
 */
fun setNightMode(@AppCompatDelegate.NightMode mode: Int) {
    AppCompatDelegate.setDefaultNightMode(mode)
    BoxMMKV.DayNightMode.set(mode)
}