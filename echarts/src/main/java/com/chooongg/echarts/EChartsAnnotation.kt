package com.chooongg.echarts

import androidx.annotation.StringDef

@StringDef("auto", "left", "right", "center")
annotation class EChartsHorizontalAlign

@StringDef("auto", "top", "bottom", "middle")
annotation class EChartsVerticalAlign

@StringDef("circle", "rect", "roundRect", "triangle", "diamond", "pin", "arrow", "none")
annotation class EChartsIcon