package com.chooongg.echarts

import android.graphics.Color
import java.math.RoundingMode
import java.text.DecimalFormat

object EChartsUtils {

    fun colorToString(color: Int): String {
        val a = Color.alpha(color)
        val r = Color.red(color)
        val g = Color.green(color)
        val b = Color.blue(color)
        return if (a == 255) {
            "rgb(${r},${g},${b})"
        } else {
            val format = DecimalFormat("0.##")
            format.roundingMode = RoundingMode.HALF_UP
            "rgba(${r},${g},${b},${format.format(a / 255f)})"
        }
    }
}