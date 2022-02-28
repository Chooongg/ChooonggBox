package com.chooongg.ext

import android.os.Looper

/**
 * 是否在主线程
 */
fun isMainThread() = Looper.myLooper() == Looper.getMainLooper()