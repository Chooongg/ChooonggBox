package com.chooongg.abc

import android.app.Application
import com.chooongg.statusLayout.StatusPage
import com.chooongg.statusLayout.status.ProgressStatus

@Suppress("unused")
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        StatusPage.configDefault {
            defaultState = ProgressStatus::class
        }
    }
}