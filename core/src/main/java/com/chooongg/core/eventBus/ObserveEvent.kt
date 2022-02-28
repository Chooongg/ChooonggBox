package com.chooongg.core.eventBus

import androidx.activity.ComponentActivity
import androidx.annotation.MainThread
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import kotlinx.coroutines.*

//监听App Scope 事件
@MainThread
inline fun <reified T> LifecycleOwner.observeEvent(
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job = ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
    .observeEvent(this, T::class.java.name, minActiveState, dispatcher, isSticky, onReceived)

@MainThread
fun LifecycleOwner.observeEventName(
    name: String,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    onReceived: (String) -> Unit
): Job = ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
    .observeEvent(this, name, minActiveState, dispatcher, isSticky, onReceived)

//监听Fragment Scope 事件
@MainThread
inline fun <reified T> observeEvent(
    scope: Fragment,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job = ViewModelProvider(scope)[EventBusCore::class.java]
    .observeEvent(scope, T::class.java.name, minActiveState, dispatcher, isSticky, onReceived)

@MainThread
fun observeEvent(
    scope: Fragment,
    name: String,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    onReceived: (String) -> Unit
): Job = ViewModelProvider(scope)[EventBusCore::class.java]
    .observeEvent(scope, name, minActiveState, dispatcher, isSticky, onReceived)

//Fragment 监听Activity Scope 事件
@MainThread
inline fun <reified T> observeEvent(
    scope: ComponentActivity,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job = ViewModelProvider(scope)[EventBusCore::class.java]
    .observeEvent(scope, T::class.java.name, minActiveState, dispatcher, isSticky, onReceived)

@MainThread
fun observeEvent(
    scope: ComponentActivity,
    name: String,
    dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
    minActiveState: Lifecycle.State = Lifecycle.State.STARTED,
    isSticky: Boolean = false,
    onReceived: (String) -> Unit
): Job = ViewModelProvider(scope)[EventBusCore::class.java]
    .observeEvent(scope, name, minActiveState, dispatcher, isSticky, onReceived)

@MainThread
inline fun <reified T> observeEvent(
    coroutineScope: CoroutineScope,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job = coroutineScope.launch {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .observeWithoutLifecycle(T::class.java.name, isSticky, onReceived)
}

@MainThread
fun observeEvent(
    coroutineScope: CoroutineScope,
    name: String,
    isSticky: Boolean = false,
    onReceived: (String) -> Unit
): Job = coroutineScope.launch {
    ApplicationScopeViewModelProvider.getApplicationScopeViewModel(EventBusCore::class.java)
        .observeWithoutLifecycle(name, isSticky, onReceived)
}

@MainThread
inline fun <reified T> observeEvent(
    scope: ViewModelStoreOwner,
    coroutineScope: CoroutineScope,
    isSticky: Boolean = false,
    noinline onReceived: (T) -> Unit
): Job = coroutineScope.launch {
    ViewModelProvider(scope)[EventBusCore::class.java]
        .observeWithoutLifecycle(T::class.java.name, isSticky, onReceived)
}

@MainThread
fun observeEvent(
    scope: ViewModelStoreOwner,
    name: String,
    coroutineScope: CoroutineScope,
    isSticky: Boolean = false,
    onReceived: (String) -> Unit
): Job = coroutineScope.launch {
    ViewModelProvider(scope)[EventBusCore::class.java]
        .observeWithoutLifecycle(name, isSticky, onReceived)
}