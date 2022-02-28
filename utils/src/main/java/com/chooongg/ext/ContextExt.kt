package com.chooongg.ext

import android.app.Activity
import android.app.Application
import android.app.Service
import android.content.Context
import android.content.ContextWrapper

fun Context.getActivity(): Activity? {
    if (this is Activity) return this
    if (this is Application || this is Service) return null
    var context: Context? = this
    while (context != null) {
        if (context is ContextWrapper) {
            context = context.baseContext
            if (context is Activity) return context
        } else {
            return null
        }
    }
    return null
}