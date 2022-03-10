package com.chooongg.echarts

import android.graphics.Color
import java.math.RoundingMode
import java.text.DecimalFormat

object EChartsUtils {

    fun colorToString(color: Int): String = buildString {
        val a = Color.alpha(color)
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        if (a == 255) {
            append("rgb(").append(r).append(',').append(g).append(',').append(b).append(')')
        } else {
            val format = DecimalFormat("0.##")
            format.roundingMode = RoundingMode.HALF_UP
            append("rgba(").append(r).append(',').append(g).append(',').append(b).append(',')
                .append(format.format(a / 255f)).append(')')
        }
    }
}