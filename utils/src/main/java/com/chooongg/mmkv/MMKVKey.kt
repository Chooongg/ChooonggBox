package com.chooongg.mmkv

import com.chooongg.ext.getTClass
import com.tencent.mmkv.MMKV

/**
 * MMKV 字典
 * 使用时请使用 object 类继承此类
 */
open class MMKVKey<T> {

    protected val mmkv: MMKV?
    val key: String
    val default: T

    constructor(mmkv: MMKV?, key: String, default: T) : super() {
        this.mmkv = mmkv
        this.key = key
        this.default = default
    }

    constructor(key: String, default: T) : super() {
        this.mmkv = MMKV.defaultMMKV()
        this.key = key
        this.default = default
    }

    fun remove() {
        if (mmkv == null) return
        mmkv.removeValueForKey(key)
    }

    @Suppress("UNCHECKED_CAST")
    open fun set(value: T?) {
        if (mmkv == null) return
        if (value == null) mmkv.removeValueForKey(key)
        else when (javaClass.getTClass()) {
            Int::class.javaObjectType -> mmkv.encode(key, value as Int)
            Long::class.javaObjectType -> mmkv.encode(key, value as Long)
            Float::class.javaObjectType -> mmkv.encode(key, value as Float)
            Double::class.javaObjectType -> mmkv.encode(key, value as Double)
            String::class.javaObjectType -> mmkv.encode(key, value as String)
            Boolean::class.javaObjectType -> mmkv.encode(key, value as Boolean)
            ByteArray::class.javaObjectType -> mmkv.encode(key, value as ByteArray)
            Set::class.javaObjectType -> {
                val set = javaClass.getTClass() as Set<*>
                if (set.javaClass.getTClass() == String::class.javaObjectType) {
                    mmkv.encode(key, value as Set<String>)
                } else throw RuntimeException("MMKV can't support the data type(Set<${set.javaClass.getTClass()}>)")
            }
            else -> throw RuntimeException("MMKV can't support the data type(${javaClass.getTClass().name})")
        }
    }

    @Suppress("UNCHECKED_CAST")
    open fun get(): T {
        if (mmkv == null) return default
        return if (mmkv.containsKey(key)) {
            when (javaClass.getTClass()) {
                Int::class.javaObjectType -> mmkv.decodeInt(key) as T
                Long::class.javaObjectType -> mmkv.decodeLong(key) as T
                Float::class.javaObjectType -> mmkv.decodeFloat(key) as T
                Double::class.javaObjectType -> mmkv.decodeDouble(key) as T
                String::class.javaObjectType -> mmkv.decodeString(key) as T
                Boolean::class.javaObjectType -> mmkv.decodeBool(key) as T
                ByteArray::class.javaObjectType -> mmkv.decodeBytes(key) as T
                Set::class.javaObjectType -> {
                    val set = javaClass.getTClass() as Set<*>
                    if (set.javaClass.getTClass() == String::class.javaObjectType) {
                        mmkv.decodeStringSet(key) as T
                    } else throw RuntimeException("MMKV can't support the data type(Set<${set.javaClass.getTClass()}>)")
                }
                else -> throw RuntimeException("MMKV can't support the data type(${javaClass.getTClass().name})")
            }
        } else default
    }
}