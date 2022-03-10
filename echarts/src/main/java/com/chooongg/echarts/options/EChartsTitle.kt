package com.chooongg.echarts.options

import androidx.annotation.ColorInt
import androidx.annotation.Keep
import androidx.annotation.StringDef
import com.chooongg.echarts.EChartsUtils

/**
 * 标题组件，包含主标题和副标题。
 */
@Keep
class EChartsTitle {
    /**
     * 组件 ID。默认不指定。指定则可用于在 option 或者 API 中引用组件。
     */
    private var id: Any? = null

    /**
     * 是否显示标题组件。
     */
    private var show: Any? = null

    /**
     * 主标题文本，支持使用 \n 换行。
     */
    private var text: Any? = null

    /**
     * 主标题文本超链接。
     */
    private var link: Any? = null

    /**
     * 指定窗口打开主标题超链接。
     */
    private var target: Any? = null

    /**
     * 主标题文字样式
     */
    private var textStyle: Any? = null

    /**
     * 副标题文本，支持使用 \n 换行。
     */
    private var subtext: Any? = null

    /**
     * 副标题文本超链接。
     */
    private var sublink: Any? = null

    /**
     * 指定窗口打开副标题超链接，可选：
     */
    private var subtarget: Any? = null

    /**
     * 副标题文字样式
     */
    private var subtextStyle: Any? = null

    /**
     * 整体（包括 text 和 subtext）的水平对齐。
     */
    private var textAlign: Any? = null

    /**
     * 整体（包括 text 和 subtext）的垂直对齐。
     */
    private var textVerticalAlign: Any? = null

    /**
     * 是否触发事件。
     */
    private var triggerEvent: Any? = null

    /**
     * 标题内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距。
     */
    private var padding: Any? = null

    /**
     * 主副标题之间的间距。
     */
    private var itemGap: Any? = null

    /**
     * 所有图形的 zlevel 值。
     * zlevel用于 Canvas 分层，不同zlevel值的图形会放置在不同的 Canvas 中，Canvas 分层是一种常见的优化手段。
     */
    private var zlevel: Any? = null

    /**
     * 组件的所有图形的z值。控制图形的前后顺序。z值小的图形会被z值大的图形覆盖。
     */
    private var z: Any? = null

    /**
     * title 组件离容器左侧的距离。
     */
    private var left: Any? = null

    /**
     * title 组件离容器上侧的距离。
     */
    private var top: Any? = null

    /**
     * title 组件离容器右侧的距离。
     */
    private var right: Any? = null

    /**
     * title 组件离容器下侧的距离。
     */
    private var bottom: Any? = null

    /**
     * 标题背景色，默认透明。
     */
    private var backgroundColor: Any? = null

    /**
     * 标题的边框颜色。支持的颜色格式同 backgroundColor。
     */
    private var borderColor: Any? = null

    /**
     * 标题的边框线宽。
     */
    private var borderWidth: Any? = null

    /**
     * 圆角半径，单位px，支持传入数组分别指定 4 个圆角半径。
     */
    private var borderRadius: Any? = null

    /**
     * 图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
     */
    private var shadowBlur: Any? = null

    /**
     * 阴影颜色。支持的格式同color。
     */
    private var shadowColor: Any? = null

    /**
     * 阴影水平方向上的偏移距离。
     */
    private var shadowOffsetX: Any? = null

    /**
     * 阴影垂直方向上的偏移距离。
     */
    private var shadowOffsetY: Any? = null

    fun id(value: String) {
        this.id = value
    }

    fun show(value: Boolean) {
        this.show = value
    }

    fun text(value: String) {
        this.text = value
    }

    fun link(value: String) {
        this.link = value
    }

    fun target(@Target value: String) {
        this.target = value
    }

    fun textStyle(block: EChartsTextStyle.() -> Unit) {
        val style = EChartsTextStyle()
        block.invoke(style)
        this.textStyle = style
    }

    fun subtext(value: String) {
        this.subtext = value
    }

    fun sublink(value: String) {
        this.sublink = value
    }

    fun subtarget(@Target value: String) {
        this.subtarget = value
    }

    fun subtextStyle(block: EChartsTextStyle.() -> Unit) {
        val style = EChartsTextStyle()
        block.invoke(style)
        this.subtextStyle = style
    }

    fun textAlign(@Align value: String) {
        this.textAlign = value
    }

    fun textVerticalAlign(@VerticalAlign value: String) {
        this.textVerticalAlign = value
    }

    fun triggerEvent(value: Boolean) {
        this.triggerEvent = value
    }

    fun padding(value: Int) {
        this.padding = value
    }

    fun padding(top: Int, right: Int, bottom: Int, left: Int) {
        this.padding = buildString {
            append('[').append(top).append(',').append(right).append(',')
                .append(bottom).append(',').append(left).append(',').append(']')
        }
    }

    fun itemGap(value: Number) {
        this.itemGap = value
    }

    fun zlevel(value: Number) {
        this.zlevel = value
    }

    fun z(value: Number) {
        this.z = value
    }

    fun left(@Align value: String) {
        this.left = value
    }

    fun left(px: Int) {
        this.left = px
    }

    fun left(percent: Float) {
        this.left = "${percent * 100}%"
    }

    fun top(@VerticalAlign value: String) {
        this.top = value
    }

    fun top(px: Int) {
        this.top = px
    }

    fun top(percent: Float) {
        this.top = "${percent * 100}%"
    }

    fun right(px: Int) {
        this.right = px
    }

    fun right(percent: Float) {
        this.right = "${percent * 100}%"
    }

    fun bottom(px: Int) {
        this.bottom = px
    }

    fun bottom(percent: Float) {
        this.bottom = "${percent * 100}%"
    }

    fun backgroundColor(value: String) {
        this.backgroundColor = value
    }

    fun backgroundColor(@ColorInt value: Int) {
        this.backgroundColor = EChartsUtils.colorToString(value)
    }

    fun borderColor(value: String) {
        this.borderColor = value
    }

    fun borderColor(@ColorInt value: Int) {
        this.borderColor = EChartsUtils.colorToString(value)
    }

    fun borderWidth(px: Int) {
        this.borderWidth = px
    }

    fun borderRadius(value: Int) {
        this.borderRadius = value
    }

    fun borderRadius(leftTop: Int, rightTop: Int, rightBottom: Int, leftBottom: Int) {
        this.borderRadius = buildString {
            append('[').append(leftTop).append(',').append(rightTop).append(',')
                .append(rightBottom).append(',').append(leftBottom).append(',').append(']')
        }
    }

    fun shadowBlur(value: Number) {
        this.shadowBlur = value
    }

    fun shadowColor(value: String) {
        this.shadowColor = value
    }

    fun shadowColor(@ColorInt value: Int) {
        this.shadowColor = EChartsUtils.colorToString(value)
    }

    fun shadowOffsetX(px: Int) {
        this.shadowOffsetX = px
    }

    fun shadowOffsetY(px: Int) {
        this.shadowOffsetY = px
    }

    @StringDef("self", "blank")
    annotation class Target

    @StringDef("auto", "left", "right", "center")
    annotation class Align

    @StringDef("auto", "top", "bottom", "middle")
    annotation class VerticalAlign
}
