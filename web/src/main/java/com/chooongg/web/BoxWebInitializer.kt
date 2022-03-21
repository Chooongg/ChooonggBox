package com.chooongg.web

import android.content.Context
import androidx.startup.Initializer
import com.chooongg.ext.logD
import com.tencent.smtt.sdk.QbSdk

@Suppress("unused")
class BoxWebInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        QbSdk.initX5Environment(context, object : QbSdk.PreInitCallback {
            override fun onCoreInitFinished() {
                logD("X5内核初始化完成")
            }

            override fun onViewInitFinished(isX5: Boolean) {
                if (isX5) {
                    logD("已启用X5内核")
                } else {
                    logD("暂未启用X5内核")
                }
            }
        })
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}