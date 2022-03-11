package com.chooongg.echarts.options

import androidx.annotation.Keep
import com.chooongg.echarts.EChartsIcon

/**
 * 图例的数据数组。
 */
@Keep
class EChartsLegendData {
    private var name: Any? = null
    private var icon: Any? = null
    private var textStyle: Any? = null

    fun name(value: String) {
        this.name = value
    }

    fun icon(@EChartsIcon value: String) {
        this.icon = value
    }

    fun textStyle(block: EChartsTextStyle.() -> Unit) {
        val style = EChartsTextStyle()
        block.invoke(style)
        this.textStyle = style
    }
}