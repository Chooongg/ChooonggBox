package com.chooongg.echarts.options

import androidx.annotation.ColorInt
import androidx.annotation.Keep
import androidx.annotation.StringDef
import com.chooongg.echarts.EChartsUtils

@Keep
class EChartsTextStyle {

    /**
     * 文字的颜色。
     */
    private var color: Any? = null

    /**
     * 文字字体的风格。
     */
    private var fontStyle: Any? = null

    /**
     * 文字字体的粗细。
     */
    private var fontWeight: Any? = null

    /**
     * 文字的字体系列。
     */
    private var fontFamily: Any? = null

    /**
     * 文字的字体大小。
     */
    private var fontSize: Any? = null

    /**
     * 行高。
     */
    private var lineHeight: Any? = null

    /**
     * 文本显示宽度。
     */
    private var width: Any? = null

    /**
     * 文本显示高度。
     */
    private var height: Any? = null

    /**
     * 文字本身的描边颜色。
     */
    private var textBorderColor: Any? = null

    /**
     * 文字本身的描边宽度。
     */
    private var textBorderWidth: Any? = null

    /**
     * 文字本身的描边类型。
     */
    private var textBorderType: Any? = null

    /**
     * 用于设置虚线的偏移量，可搭配 textBorderType 指定 dash array 实现灵活的虚线效果。
     */
    private var textBorderDashOffset: Any? = null

    /**
     * 文字本身的阴影颜色。
     */
    private var textShadowColor: Any? = null

    /**
     * 文字本身的阴影长度。
     */
    private var textShadowBlur: Any? = null

    /**
     * 文字本身的阴影 X 偏移。
     */
    private var textShadowOffsetX: Any? = null

    /**
     * 文字本身的阴影 Y 偏移。
     */
    private var textShadowOffsetY: Any? = null

    /**
     * 文字超出宽度是否截断或者换行。配置width时有效
     */
    private var overflow: Any? = null

    /**
     * 在overflow配置为'truncate'的时候，可以通过该属性配置末尾显示的文本。
     */
    private var ellipsis: Any? = null

    fun color(value: String) {
        this.color = value
    }

    fun color(@ColorInt value: Int) {
        this.color = EChartsUtils.colorToString(value)
    }

    fun fontStyle(@FontStyle value: String) {
        this.fontStyle = value
    }

    fun fontWeight(@FontWeight value: String) {
        this.fontWeight = value
    }

    fun fontWeight(value: Int) {
        this.fontWeight = value
    }

    fun fontFamily(value: String) {
        this.fontFamily = value
    }

    fun lineHeight(px: Int) {
        this.lineHeight = px
    }

    fun width(px: Int) {
        this.width = px
    }

    fun height(px: Int) {
        this.height = px
    }

    fun textBorderColor(value: String) {
        this.textBorderColor = value
    }

    fun textBorderColor(@ColorInt value: Int) {
        this.textBorderColor = EChartsUtils.colorToString(value)
    }

    fun textBorderWidth(px: Int) {
        this.textBorderWidth = px
    }

    fun textBorderType(@BorderType value: String) {
        this.textBorderType = value
    }

    fun textBorderType(value: Int) {
        this.textBorderType = value
    }

    fun textBorderType(vararg value: Int) {
        this.textBorderType = value
    }

    fun textBorderDashOffset(px: Int) {
        this.textBorderDashOffset = px
    }

    fun textShadowColor(value: String) {
        this.textShadowColor = value
    }

    fun textShadowColor(@ColorInt value: Int) {
        this.textShadowColor = EChartsUtils.colorToString(value)
    }

    fun textShadowBlur(px: Int) {
        this.textShadowBlur = px
    }

    fun textShadowOffsetX(px: Int) {
        this.textShadowOffsetX = px
    }

    fun textShadowOffsetY(px: Int) {
        this.textShadowOffsetY = px
    }

    fun overflow(@Overflow value: String) {
        this.overflow = value
    }

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