package com.chooongg.ext

import android.util.Log


private fun getTag(offset: Int): String {
    val stackTrace = Throwable().stackTrace
    val caller = when {
        3 + offset < 0 -> stackTrace[0]
        3 + offset > stackTrace.lastIndex -> stackTrace[stackTrace.lastIndex]
        else -> stackTrace[3 + offset]
    }
    return String.format("(%s:%d)", caller.fileName, caller.lineNumber)
}

fun logV(msg: String, e: Throwable? = null, offsetStack: Int = 0) {
    if (BoxLog.isEnable) {
        if (e == null) Log.v(getTag(offsetStack), msg)
        else Log.v(getTag(offsetStack), msg, e)
    }
}

fun logVTag(tag: String, msg: String, e: Throwable? = null) {
    if (BoxLog.isEnable) if (e == null) Log.v(tag, msg) else Log.v(tag, msg, e)
}

fun logD(msg: String, e: Throwable? = null, offsetStack: Int = 0) {
    if (BoxLog.isEnable) {
        if (e == null) Log.d(getTag(offsetStack), msg)
        else Log.d(getTag(offsetStack), msg, e)
    }
}

fun logDTag(tag: String, msg: String, e: Throwable? = null) {
    if (BoxLog.isEnable) if (e == null) Log.d(tag, msg) else Log.d(tag, msg, e)
}

fun logI(msg: String, e: Throwable? = null, offsetStack: Int = 0) {
    if (BoxLog.isEnable) {
        if (e == null) Log.i(getTag(offsetStack), msg)
        else Log.i(getTag(offsetStack), msg, e)
    }
}

fun logITag(tag: String, msg: String, e: Throwable? = null) {
    if (BoxLog.isEnable) if (e == null) Log.i(tag, msg) else Log.i(tag, msg, e)
}

fun logW(msg: String, e: Throwable? = null, offsetStack: Int = 0) {
    if (BoxLog.isEnable) {
        if (e == null) Log.w(getTag(offsetStack), msg)
        else Log.w(getTag(offsetStack), msg, e)
    }
}

fun logWTag(tag: String, msg: String, e: Throwable? = null) {
    if (BoxLog.isEnable) if (e == null) Log.w(tag, msg) else Log.w(tag, msg, e)
}

fun logE(msg: String, e: Throwable? = null, offsetStack: Int = 0) {
    if (BoxLog.isEnable) {
        if (e == null) Log.e(getTag(offsetStack), msg)
        else Log.e(getTag(offsetStack), msg, e)

    }
}

fun logETag(tag: String, msg: String, e: Throwable? = null) {
    if (BoxLog.isEnable) if (e == null) Log.e(tag, msg) else Log.e(tag, msg, e)
}

fun logWTF(msg: String, e: Throwable? = null, offsetStack: Int = 0) {
    if (BoxLog.isEnable) {
        if (e == null) Log.wtf(getTag(offsetStack), msg)
        else Log.wtf(getTag(offsetStack), msg, e)

    }
}

fun logWTFTag(tag: String, msg: String, e: Throwable? = null) {
    if (BoxLog.isEnable) if (e == null) Log.wtf(tag, msg) else Log.wtf(tag, msg, e)
}

object BoxLog {
    internal var isEnable = isAppDebug()

    fun setEnable(enable: Boolean) {
        isEnable = enable
    }
}

//fun Class<*>.getTag() {
//    var tag = "%s.%s(%s:%d)"
//    this.filename
//    caller.fileName, caller.lineNumber
//}