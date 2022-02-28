package com.chooongg.mmkv

import android.os.Parcelable
import com.chooongg.ext.getTClass
import com.tencent.mmkv.MMKV

/**
 * MMKV 字典
 * 使用时请使用 object 类继承此类
 */
open class MMKVParcelableKey<T : Parcelable?> : MMKVKey<T> {
    constructor(mmkv: MMKV?, key: String, default: T) : super(mmkv, key, default)
    constructor(key: String, default: T) : super(key, default)

    override fun set(value: T?) {
        if (mmkv == null) return
        if (value == null) mmkv.removeValueForKey(key)
        mmkv.encode(key, value)
    }

    @Suppress("UNCHECKED_CAST")
    override fun get(): T {
        if (mmkv == null) return default
        return mmkv.decodeParcelable(key, javaClass.getTClass() as Class<Parcelable>, default) as T
    }
}