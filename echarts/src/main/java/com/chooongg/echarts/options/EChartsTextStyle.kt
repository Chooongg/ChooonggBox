package com.chooongg.echarts.options

import androidx.annotation.ColorInt
import androidx.annotation.Keep
import androidx.annotation.StringDef
import com.chooongg.echarts.EChartsUtils

@Keep
class EChartsTextStyle {

    private var color: Any? = null
    private var fontStyle: Any? = null
    private var fontWeight: Any? = null
    private var fontFamily: Any? = null
    private var fontSize: Any? = null
    private var lineHeight: Any? = null
    private var width: Any? = null
    private var height: Any? = null
    private var textBorderColor: Any? = null
    private var textBorderWidth: Any? = null
    private var textBorderType: Any? = null
    private var textBorderDashOffset: Any? = null
    private var textShadowColor: Any? = null
    private var textShadowBlur: Any? = null
    private var textShadowOffsetX: Any? = null
    private var textShadowOffsetY: Any? = null
    private var overflow: Any? = null
    private var ellipsis: Any? = null


    /**
     * 文字的颜色。
     */
    fun color(value: String) {
        this.color = value
    }

    /**
     * 文字的颜色。
     */
    fun color(@ColorInt value: Int) {
        this.color = EChartsUtils.colorToString(value)
    }

    /**
     * 文字字体的风格。
     */
    fun fontStyle(@FontStyle value: String) {
        this.fontStyle = value
    }

    /**
     * 文字字体的粗细。
     */
    fun fontWeight(@FontWeight value: String) {
        this.fontWeight = value
    }

    /**
     * 文字字体的粗细。
     */
    fun fontWeight(value: Int) {
        this.fontWeight = value
    }

    /**
     * 文字的字体系列。
     */
    fun fontFamily(value: String) {
        this.fontFamily = value
    }

    /**
     * 文字的字体大小。
     */
    fun fontSize(px: Int) {
        this.fontSize = px
    }

    /**
     * 行高。
     */
    fun lineHeight(px: Int) {
        this.lineHeight = px
    }

    /**
     * 文本显示宽度。
     */
    fun width(px: Int) {
        this.width = px
    }

    /**
     * 文本显示高度。
     */
    fun height(px: Int) {
        this.height = px
    }

    /**
     * 文字本身的描边颜色。
     */
    fun textBorderColor(value: String) {
        this.textBorderColor = value
    }

    /**
     * 文字本身的描边颜色。
     */
    fun textBorderColor(@ColorInt value: Int) {
        this.textBorderColor = EChartsUtils.colorToString(value)
    }

    /**
     * 文字本身的描边宽度。
     */
    fun textBorderWidth(px: Int) {
        this.textBorderWidth = px
    }

    /**
     * 文字本身的描边类型。
     */
    fun textBorderType(@BorderType value: String) {
        this.textBorderType = value
    }

    /**
     * 文字本身的描边类型。
     */
    fun textBorderType(value: Int) {
        this.textBorderType = value
    }

    /**
     * 文字本身的描边类型。
     */
    fun textBorderType(vararg value: Int) {
        this.textBorderType = value
    }

    /**
     * 用于设置虚线的偏移量，可搭配 textBorderType 指定 dash array 实现灵活的虚线效果。
     */
    fun textBorderDashOffset(px: Int) {
        this.textBorderDashOffset = px
    }

    /**
     * 文字本身的阴影颜色。
     */
    fun textShadowColor(value: String) {
        this.textShadowColor = value
    }

    /**
     * 文字本身的阴影颜色。
     */
    fun textShadowColor(@ColorInt value: Int) {
        this.textShadowColor = EChartsUtils.colorToString(value)
    }

    /**
     * 文字本身的阴影长度。
     */
    fun textShadowBlur(px: Int) {
        this.textShadowBlur = px
    }

    /**
     * 文字本身的阴影 X 偏移。
     */
    fun textShadowOffsetX(px: Int) {
        this.textShadowOffsetX = px
    }

    /**
     * 文字本身的阴影 Y 偏移。
     */
    fun textShadowOffsetY(px: Int) {
        this.textShadowOffsetY = px
    }

    /**
     * 文字超出宽度是否截断或者换行。配置width时有效
     */
    fun overflow(@Overflow value: String) {
        this.overflow = value
    }

    /**
     * 在overflow配置为'truncate'的时候，可以通过该属性配置末尾显示的文本。
     */
    fun ellipsis(value: String) {
        this.ellipsis = value
    }

    @StringDef("normal", "italic", "oblique")
    annotation class FontStyle

    @StringDef("normal", "bold", "bolder", "lighter")
    annotation class FontWeight

    @StringDef("solid", "dashed", "dotted")
    annotation class BorderType

    @StringDef("truncate", "break", "breakAll")
    annotation class Overflow
}