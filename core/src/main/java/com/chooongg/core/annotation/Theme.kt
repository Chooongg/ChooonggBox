package com.chooongg.core.annotation

import androidx.annotation.StyleRes
import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Theme(@StyleRes val value: Int)