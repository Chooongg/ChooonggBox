package com.chooongg.echarts.options

import androidx.annotation.ColorInt
import androidx.annotation.FloatRange
import androidx.annotation.Keep
import androidx.annotation.StringDef
import com.chooongg.echarts.EChartsUtils

/**
 * 渐变色
 */
@Keep
class EChartsColor {
    private var type: Any? = null
    private var x: Any? = null
    private var y: Any? = null
    private var x2: Any? = null
    private var y2: Any? = null
    private var r: Any? = null
    private var colorStops: MutableList<ColorOffset>? = null
    private var global: Boolean? = null

    fun type(@Type value: String) {
        this.type = value
    }

    fun x(@FloatRange(from = 0.0, to = 1.0) value: Float) {
        this.x = value
    }

    fun x(value: Int) {
        this.x = value
    }

    fun y(@FloatRange(from = 0.0, to = 1.0) value: Float) {
        this.y = value
    }

    fun y(value: Int) {
        this.y = value
    }

    fun x2(@FloatRange(from = 0.0, to = 1.0) value: Float) {
        this.x2 = value
    }

    fun x2(value: Int) {
        this.x2 = value
    }

    fun y2(@FloatRange(from = 0.0, to = 1.0) value: Float) {
        this.y2 = value
    }

    fun y2(value: Int) {
        this.y2 = value
    }

    fun r(@FloatRange(from = 0.0, to = 1.0) value: Float) {
        this.r = value
    }

    fun r(value: Int) {
        this.r = value
    }

    fun colorStops(vararg blocks: ColorOffset.() -> Unit) {
        val list = ArrayList<ColorOffset>()
        blocks.forEach {
            val item = ColorOffset()
            it.invoke(item)
            list.add(item)
        }
        this.colorStops = list
    }

    fun global(value: Boolean) {
        this.global = value
    }

    @StringDef("linear", "radial")
    annotation class Type

    class ColorOffset {
        private var offset: Float? = null
        private var color: String? = null

        fun offset(@FloatRange(from = 0.0, to = 1.0) value: Float) {
            this.offset = value
        }

        fun color(value: String) {
            this.color = value
        }

        fun color(@ColorInt value: Int) {
            this.color = EChartsUtils.colorToString(value)
        }
    }
}