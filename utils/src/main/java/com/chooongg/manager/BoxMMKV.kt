package com.chooongg.manager

import androidx.appcompat.app.AppCompatDelegate
import com.chooongg.mmkv.MMKVKey
import com.chooongg.mmkv.MMKVSealed
import com.tencent.mmkv.MMKV

internal object BoxMMKV : MMKVSealed() {

    override fun createMMKV() = MMKV.mmkvWithID("AndroidBox")

    internal object DayNightMode :
        MMKVKey<Int>(getMMKV(), "day_night_mode", AppCompatDelegate.MODE_NIGHT_NO)
}