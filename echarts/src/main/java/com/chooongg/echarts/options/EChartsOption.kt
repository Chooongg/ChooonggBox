package com.chooongg.echarts.options

import androidx.annotation.Keep

@Keep
data class EChartsOption(
    var title: EChartsTitle? = null,
    var xAxis:EChartsXAxis? = null,
    var yAxis:EChartsXAxis? = null
)