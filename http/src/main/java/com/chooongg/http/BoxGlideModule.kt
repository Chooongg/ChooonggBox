package com.chooongg.http

import android.content.Context
import android.util.Log
import com.bumptech.glide.GlideBuilder
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.bumptech.glide.request.RequestOptions
import com.chooongg.ext.isAppDebug

@Suppress("unused")
@GlideModule(glideName = "BoxGlide")
class BoxGlideModule : AppGlideModule() {

    @Suppress("CheckResult")
    override fun applyOptions(context: Context, builder: GlideBuilder) {
        builder.setLogRequestOrigins(isAppDebug())
        builder.setLogLevel(Log.INFO)
        builder.setDefaultRequestOptions {
            return@setDefaultRequestOptions RequestOptions().apply {
                placeholder(com.chooongg.R.color.outline)
                fallback(com.chooongg.R.color.outline)
                error(com.chooongg.R.color.outline)
                useAnimationPool(true)
            }
        }
    }
}