package com.chooongg.manager

import android.app.Application
import com.chooongg.BoxException

internal object ApplicationManager {

    private var myApplication: Application? = null

    internal val application
        get() = myApplication ?: throw BoxException("AppManager has not been initialized")

    fun initialize(application: Application) {
        if (myApplication != null) throw BoxException("You have initialize AppManager")
        myApplication = application
        application.registerActivityLifecycleCallbacks(ActivityLifecycleManager)
    }
}