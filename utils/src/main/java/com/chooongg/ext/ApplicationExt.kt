package com.chooongg.ext

import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import com.chooongg.manager.ApplicationManager

/**
 * 全局 Application 常量
 */
val APPLICATION get() = ApplicationManager.application

/**
 * 判断是否是 Debug 版本
 *
 * @param packageName 包名-默认本App包名
 */
fun isAppDebug(packageName: String = APPLICATION.packageName): Boolean {
    if (packageName.isBlank()) return false
    return try {
        val ai = APPLICATION.packageManager.getApplicationInfo(packageName, 0)
        ai.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
        false
    }
}

/**
 * Debug 才运行的代码块
 */
fun debug(init: () -> Unit) = if (isAppDebug()) init() else Unit

/**
 * Release 才运行的代码块
 */
fun release(init: () -> Unit) = if (!isAppDebug()) init() else Unit