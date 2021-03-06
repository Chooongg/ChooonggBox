package chooongg.box.mmkv

import android.os.Parcelable
import com.tencent.mmkv.MMKV

/**
 * MMKV控制器
 */
open class MMKVController {

    val mmkv: MMKV?

    constructor() {
        mmkv = MMKV.defaultMMKV()
    }

    constructor(mode: Int, cryptKey: String?) {
        mmkv = MMKV.defaultMMKV(mode, cryptKey)
    }

    constructor(mmkv: MMKV) {
        this.mmkv = mmkv
    }

    constructor(mmapID: String) {
        mmkv = MMKV.mmkvWithID(mmapID)
    }

    constructor(mmapID: String, mode: Int) {
        mmkv = MMKV.mmkvWithID(mmapID, mode)
    }

    constructor(mmapID: String, mode: Int, cryptKey: String?) {
        mmkv = MMKV.mmkvWithID(mmapID, mode, cryptKey)
    }

    fun <T> encode(mmkvKey: MMKVKey<T>, value: T?) {
        if (mmkv == null) return
        if (value == null) mmkv.removeValueForKey(mmkvKey.key)
        else when (mmkvKey.getTClass()) {
            Int::class.javaObjectType -> mmkv.encode(mmkvKey.key, value as Int)
            Long::class.javaObjectType -> mmkv.encode(mmkvKey.key, value as Long)
            Float::class.javaObjectType -> mmkv.encode(mmkvKey.key, value as Float)
            Double::class.javaObjectType -> mmkv.encode(mmkvKey.key, value as Double)
            String::class.javaObjectType -> mmkv.encode(mmkvKey.key, value as String)
            Boolean::class.javaObjectType -> mmkv.encode(mmkvKey.key, value as Boolean)
            ByteArray::class.javaObjectType -> mmkv.encode(mmkvKey.key, value as ByteArray)
            else -> throw RuntimeException("MMKV can't support the data type(${mmkvKey.getTClass().name})")
        }
    }

    @Suppress("UNCHECKED_CAST")
    fun <T> decode(mmkvKey: MMKVKey<T>, defaultValue: T = mmkvKey.defaultValue): T {
        if (mmkv == null) return defaultValue
        return if (mmkv.containsKey(mmkvKey.key)) {
            when (mmkvKey.getTClass()) {
                Int::class.javaObjectType -> mmkv.decodeInt(mmkvKey.key) as T
                Long::class.javaObjectType -> mmkv.decodeLong(mmkvKey.key) as T
                Float::class.javaObjectType -> mmkv.decodeFloat(mmkvKey.key) as T
                Double::class.javaObjectType -> mmkv.decodeDouble(mmkvKey.key) as T
                String::class.javaObjectType -> mmkv.decodeString(mmkvKey.key) as T
                Boolean::class.javaObjectType -> mmkv.decodeBool(mmkvKey.key) as T
                ByteArray::class.javaObjectType -> mmkv.decodeBytes(mmkvKey.key) as T
                else -> throw RuntimeException("MMKV can't support the data type(${mmkvKey.getTClass().name})")
            }
        } else defaultValue
    }

    fun <T : Parcelable?> encode(mmkvKey: MMKVKeyParcelable<T>, value: T?) {
        if (mmkv == null) return
        if (value == null) mmkv.removeValueForKey(mmkvKey.key)
        else mmkv.encode(mmkvKey.key, value)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Parcelable?> decode(
        mmkvKey: MMKVKeyParcelable<T>,
        defaultValue: T = mmkvKey.defaultValue
    ): T {
        if (mmkv == null) return defaultValue
        return if (mmkv.containsKey(mmkvKey.key)) mmkv.decodeParcelable(
            mmkvKey.key,
            mmkvKey.getTClass() as Class<Parcelable>
        ) as T
        else defaultValue
    }

    fun remove(vararg mmkvKey: MMKVKey<*>) {
        if (mmkv == null) return
        if (mmkvKey.isNotEmpty()) {
            val keys = Array(mmkvKey.size) {
                mmkvKey[it].key
            }
            mmkv.removeValuesForKeys(keys)
        }
    }

    fun remove(vararg mmkvKeyParcelable: MMKVKeyParcelable<*>) {
        if (mmkv == null) return
        if (mmkvKeyParcelable.isNotEmpty()) {
            val keys = Array(mmkvKeyParcelable.size) {
                mmkvKeyParcelable[it].key
            }
            mmkv.removeValuesForKeys(keys)
        }
    }
}