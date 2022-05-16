package com.chooongg.ext

import android.app.Activity
import android.view.View
import android.view.Window
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment

fun Window.getWindowInsetsController() = WindowCompat.getInsetsController(this, decorView)

/**
 * 显示系统栏
 */
fun Fragment.showSystemBars() = requireActivity().showSystemBars()
fun Activity.showSystemBars() {
    window.getWindowInsetsController().show(WindowInsetsCompat.Type.systemBars())
}

/**
 * 隐藏系统栏
 */
fun Fragment.hideSystemBars() = requireActivity().hideSystemBars()
fun Activity.hideSystemBars() {
    window.getWindowInsetsController().hide(WindowInsetsCompat.Type.systemBars())
}

/**
 * 显示状态栏
 */
fun Fragment.showStatusBars() = requireActivity().showStatusBars()
fun Activity.showStatusBars() {
    window.getWindowInsetsController().show(WindowInsetsCompat.Type.statusBars())
}

/**
 * 隐藏状态栏
 */
fun Fragment.hideStatusBars() = requireActivity().hideStatusBars()
fun Activity.hideStatusBars() {
    window.getWindowInsetsController().hide(WindowInsetsCompat.Type.statusBars())
}

/**
 * 显示导航栏
 */
fun Fragment.showNavigationBars() = requireActivity().showNavigationBars()
fun Activity.showNavigationBars() {
    window.getWindowInsetsController().show(WindowInsetsCompat.Type.navigationBars())
}

/**
 * 隐藏导航栏
 */
fun Fragment.hideNavigationBars() = requireActivity().hideNavigationBars()
fun Activity.hideNavigationBars() {
    window.getWindowInsetsController().hide(WindowInsetsCompat.Type.navigationBars())
}

/**
 * 显示输入法
 */
fun Fragment.showIME(view: View? = null) = requireActivity().showIME(view)
fun Activity.showIME(view: View? = null) {
    view?.requestFocus()
    window.getWindowInsetsController().show(WindowInsetsCompat.Type.ime())
}

/**
 * 隐藏输入法
 */
fun Fragment.hideIME() = requireActivity().hideIME()
fun Activity.hideIME() {
    window.getWindowInsetsController().hide(WindowInsetsCompat.Type.ime())
}

/**
 * 设置亮色状态栏
 */
fun Fragment.setLightStatusBars(isLightMode: Boolean) =
    requireActivity().setLightStatusBars(isLightMode)

fun Activity.setLightStatusBars(isLightMode: Boolean) {
    window.getWindowInsetsController().isAppearanceLightStatusBars = isLightMode
}

/**
 * 是否是亮色状态栏
 */
fun Fragment.isLightStatusBars() = requireActivity().isLightStatusBars()
fun Activity.isLightStatusBars(): Boolean {
    return window.getWindowInsetsController().isAppearanceLightStatusBars
}

/**
 * 设置亮色导航栏
 */
fun Fragment.setLightNavigationBars(isLightMode: Boolean) =
    requireActivity().setLightNavigationBars(isLightMode)

fun Activity.setLightNavigationBars(isLightMode: Boolean) {
    window.getWindowInsetsController().isAppearanceLightNavigationBars = isLightMode
}

/**
 * 是否是亮色导航栏
 */
fun Fragment.isLightNavigationBars() = requireActivity().isLightNavigationBars()
fun Activity.isLightNavigationBars(): Boolean {
    return window.getWindowInsetsController().isAppearanceLightNavigationBars
}