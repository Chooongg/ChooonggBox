package com.chooongg.statusLayout

import androidx.annotation.IntDef
import com.chooongg.statusLayout.status.AbstractStatus
import com.chooongg.statusLayout.status.SuccessStatus
import kotlin.reflect.KClass

class StatusPageConfig internal constructor() {

    companion object {
        const val ANIMATION_TYPE_FADE = 0
        const val ANIMATION_TYPE_SHARED_AXIS_X = 1
        const val ANIMATION_TYPE_SHARED_AXIS_Y = 2
        const val ANIMATION_TYPE_SHARED_AXIS_Z = 3
    }

    var enableAnimation = true

    @AnimationType
    var animationType = ANIMATION_TYPE_FADE

    var defaultState: KClass<out AbstractStatus> = SuccessStatus::class

    @IntDef(
        ANIMATION_TYPE_FADE,
        ANIMATION_TYPE_SHARED_AXIS_X,
        ANIMATION_TYPE_SHARED_AXIS_Y,
        ANIMATION_TYPE_SHARED_AXIS_Z
    )
    annotation class AnimationType
}