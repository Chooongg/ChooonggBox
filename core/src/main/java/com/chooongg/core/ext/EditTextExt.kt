package com.chooongg.core.ext

import android.text.InputFilter
import android.text.Spanned
import android.widget.EditText

/**
 * 添加过滤器
 */
fun EditText.addFilter(filter: InputFilter) {
    val list = filters.toMutableList()
    list.add(filter)
    filters = list.toTypedArray()
}

/**
 * 禁用Emoji表情输入
 */
class EmojiExcludeFilter : InputFilter {
    override fun filter(
        source: CharSequence,
        start: Int,
        end: Int,
        dest: Spanned,
        dstart: Int,
        dend: Int
    ): CharSequence? {
        for (i in start until end) {
            val type = Character.getType(source[i])
            if (type == Character.SURROGATE.toInt() || type == Character.OTHER_SYMBOL.toInt()) {
                return ""
            }
        }
        return null
    }
}