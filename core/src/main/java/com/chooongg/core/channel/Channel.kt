@file:Suppress("OPT_IN_USAGE", "ObjectPropertyName")

package com.chooongg.core.channel

import android.annotation.SuppressLint
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.BroadcastChannel
import kotlinx.coroutines.launch

/**
 * @suppress 一般代码不应该使用
 */
var _channel = BroadcastChannel<_Bus<Any>>(102400)

// <editor-fold desc="发送">

/**
 * 发送事件
 * @param event 事件
 * @param tag 标签, 使用默认值空, 则接受者将忽略标签, 仅匹配事件
 */
fun sendEvent(event: Any, tag: String? = null) = ChannelScope().launch {
    _channel.send(_Bus(event, tag))
}


/**
 * 发送标签
 * @param tag 标签
 */
fun sendTag(tag: String?) = ChannelScope().launch {
    _channel.send(_Bus(TagEvent(), tag))
}

// </editor-fold>


//<editor-fold desc="接收事件">

/**
 * 接收事件
 *
 * @param tags 可接受零个或多个标签, 如果标签为零个则匹配事件对象即可成功接收, 如果为多个则要求至少匹配一个标签才能成功接收到事件
 * @param block 接收到事件后执行函数
 */
inline fun <reified T> LifecycleOwner.receiveEvent(
    vararg tags: String? = emptyArray(),
    lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    noinline block: suspend CoroutineScope.(event: T) -> Unit
): Job {
    val coroutineScope = ChannelScope(this, lifeEvent)
    return coroutineScope.launch {
        for (bus in _channel.openSubscription()) {
            if (bus.event is T && (tags.isEmpty() || tags.contains(bus.tag))) {
                block(bus.event)
            }
        }
    }
}

/**
 * 使用LiveData将消息延迟到前台接收
 */
@SuppressLint("NullSafeMutableLiveData")
inline fun <reified T> LifecycleOwner.receiveEventLive(
    vararg tags: String? = arrayOf(),
    lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    noinline block: suspend CoroutineScope.(event: T) -> Unit
): Job {
    val coroutineScope = ChannelScope(this, lifeEvent)
    return coroutineScope.launch {
        for (bus in _channel.openSubscription()) {
            if (bus.event is T && (tags.isEmpty() || tags.contains(bus.tag))) {
                val liveData = MutableLiveData<T>()
                liveData.observe(this@receiveEventLive) { coroutineScope.launch { block(it) } }
                liveData.value = bus.event
            }
        }
    }
}

/**
 * 接收事件, 此事件要求执行[kotlinx.coroutines.cancel]手动注销
 *
 * @param tags 可接受零个或多个标签, 如果标签为零个则匹配事件对象即可成功接收, 如果为多个则要求至少匹配一个标签才能成功接收到事件
 * @param block 接收到事件后执行函数
 */
inline fun <reified T> receiveEventHandler(
    vararg tags: String? = arrayOf(),
    noinline block: suspend CoroutineScope.(event: T) -> Unit
): Job {
    val coroutineScope = ChannelScope()
    return coroutineScope.launch {
        for (bus in _channel.openSubscription()) {
            if (bus.event is T && (tags.isEmpty() || tags.contains(bus.tag))) {
                block(bus.event)
            }
        }
    }
}
//</editor-fold>


//<editor-fold desc="接收标签">


/**
 * 接收标签, 和[receiveEvent]不同之处在于该函数仅支持标签, 不支持事件+标签
 *
 * @param tags 可接受零个或多个标签, 如果标签为零个则匹配事件对象即可成功接收, 如果为多个则要求至少匹配一个标签才能成功接收到事件
 * @param block 接收到事件后执行函数
 */
fun LifecycleOwner.receiveTag(
    vararg tags: String?,
    lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    block: suspend CoroutineScope.(tag: String) -> Unit
): Job {
    val coroutineScope = ChannelScope(this, lifeEvent)
    return coroutineScope.launch {
        for (bus in _channel.openSubscription()) {
            if (bus.event is TagEvent && !bus.tag.isNullOrBlank() && tags.contains(bus.tag)) {
                block(bus.tag)
            }
        }
    }
}

/**
 * 使用LiveData将消息延迟到前台接收
 */
@SuppressLint("NullSafeMutableLiveData")
fun LifecycleOwner.receiveTagLive(
    vararg tags: String?,
    lifeEvent: Lifecycle.Event = Lifecycle.Event.ON_DESTROY,
    block: suspend CoroutineScope.(tag: String) -> Unit
): Job {
    val coroutineScope = ChannelScope(this, lifeEvent)
    return coroutineScope.launch {
        for (bus in _channel.openSubscription()) {
            if (bus.event is TagEvent && !bus.tag.isNullOrBlank() && tags.contains(bus.tag)) {
                val liveData = MutableLiveData<String>()
                liveData.observe(this@receiveTagLive) { coroutineScope.launch { block(it) } }
                liveData.value = bus.tag
            }
        }
    }
}

/**
 * 接收标签, 和[receiveEvent]不同之处在于该函数仅支持标签, 不支持事件+标签, 此事件要求执行[kotlinx.coroutines.cancel]手动注销
 *
 * @param tags 可接受零个或多个标签, 如果标签为零个则匹配事件对象即可成功接收, 如果为多个则要求至少匹配一个标签才能成功接收到事件
 * @param block 接收到事件后执行函数
 */
fun receiveTagHandler(
    vararg tags: String?,
    block: suspend CoroutineScope.(tag: String) -> Unit
): Job {
    val coroutineScope = ChannelScope()
    return coroutineScope.launch {
        for (bus in _channel.openSubscription()) {
            if (bus.event is TagEvent && !bus.tag.isNullOrEmpty() && tags.contains(bus.tag)) {
                block(bus.tag)
            }
        }
    }
}
//</editor-fold>