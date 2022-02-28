package com.chooongg.core.annotation

import androidx.annotation.IntDef
import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TopAppBar(@Type val value: Int = TYPE_SMALL) {

    companion object {
        const val TYPE_NONE = 0
        const val TYPE_SMALL = 1
        const val TYPE_MEDIUM = 2
        const val TYPE_LARGE = 3
    }

    @IntDef(TYPE_NONE, TYPE_SMALL, TYPE_MEDIUM, TYPE_LARGE)
    annotation class Type
}

