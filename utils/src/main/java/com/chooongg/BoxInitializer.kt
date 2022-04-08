package com.chooongg

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.chooongg.ext.BoxLog
import com.chooongg.ext.isAppDebug
import com.chooongg.ext.setNightMode
import com.chooongg.manager.ApplicationManager
import com.chooongg.manager.BoxMMKV
import com.facebook.stetho.Stetho
import com.tencent.mmkv.MMKV

@Suppress("unused")
class BoxInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        ApplicationManager.initialize(context as Application)
        MMKV.initialize(context)
        setNightMode(BoxMMKV.DayNightMode.get())
        BoxLog.setEnable(isAppDebug())
        Stetho.initializeWithDefaults(context)
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> = mutableListOf()
}