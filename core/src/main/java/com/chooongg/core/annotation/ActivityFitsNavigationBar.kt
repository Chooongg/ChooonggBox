package com.chooongg.core.annotation

import java.lang.annotation.Inherited

@Inherited
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class ActivityFitsNavigationBar(val value: Boolean = true)
