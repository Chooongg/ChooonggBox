package com.chooongg.utils

import com.tencent.mmkv.MMKV

object MMKVUtils {
    fun clearKey(mmkv: MMKV?, key: String) {
        mmkv?.removeValueForKey(key)
    }

    fun clearKeys(mmkv: MMKV?, vararg keys: String) {
        mmkv?.removeValuesForKeys(keys)
    }
}