package com.chooongg.ext

import android.text.Spannable
import android.text.SpannableString
import android.text.method.LinkMovementMethod
import android.text.style.URLSpan
import android.widget.TextView
import com.chooongg.BoxException
import com.chooongg.utils.SpanUtils

fun CharSequence.style(init: SpanUtils.() -> Unit): SpanUtils = SpanUtils(this).apply {
    init()
    return this
}

fun TextView.setText(span: SpanUtils) {
    val builder = StringBuilder()
    span.textConstructor.forEach { builder.append(it) }
    val spanStr = SpannableString(builder.toString())
    var index = 0
    span.textConstructor.forEachIndexed { position, str ->
        val end = index + str.length
        span.styles[position].forEach {
            spanStr.setSpan(it, index, end, Spannable.SPAN_INCLUSIVE_EXCLUSIVE)
            if (it is URLSpan) this.movementMethod = LinkMovementMethod()
        }
        index += str.length
    }
    text = spanStr
}

var TextView.textSpan: SpanUtils
    set(value) = setText(value)
    get() {
        throw BoxException("Span Text not get value")
    }