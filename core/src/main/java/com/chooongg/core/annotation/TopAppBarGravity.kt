package com.chooongg.core.annotation

import android.view.Gravity
import androidx.annotation.GravityInt
import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class TopAppBarGravity(
    @GravityInt val collapsedTitleGravity: Int,
    @GravityInt val expandedTitleGravity: Int = Gravity.NO_GRAVITY
)
