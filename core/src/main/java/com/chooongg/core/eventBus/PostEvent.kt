package com.chooongg.core.eventBus

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

//Application范围的事件
inline fun <reified T> postEvent(event: T, timeMillis: Long = 0L) {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .postEvent(T::class.java.name, event!!, timeMillis)
}

fun postEventName(name: String, timeMillis: Long = 0L) {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .postEvent(name, name, timeMillis)
}

//限定范围的事件
inline fun <reified T> postEvent(scope: ViewModelStoreOwner, event: T, timeMillis: Long = 0L) {
    ViewModelProvider(scope)[EventBusCore::class.java]
        .postEvent(T::class.java.name, event!!, timeMillis)
}

fun postEvent(scope: ViewModelStoreOwner, name: String, timeMillis: Long = 0L) {
    ViewModelProvider(scope)[EventBusCore::class.java]
        .postEvent(name, name, timeMillis)
}