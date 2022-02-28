package com.chooongg.statusLayout

import androidx.annotation.AnimRes
import com.chooongg.statusLayout.status.AbstractStatus
import com.chooongg.statusLayout.status.SuccessStatus
import kotlin.reflect.KClass

class StatusPageConfig {

    var enableAnimation = true

    @AnimRes
    var animationResId: Int = com.chooongg.R.anim.fade_in

    var defaultState: KClass<out AbstractStatus> = SuccessStatus::class
}