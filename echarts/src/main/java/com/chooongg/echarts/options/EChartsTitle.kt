package com.chooongg.echarts.options

import androidx.annotation.ColorInt
import androidx.annotation.Keep
import androidx.annotation.StringDef
import com.chooongg.echarts.EChartsHorizontalAlign
import com.chooongg.echarts.EChartsUtils
import com.chooongg.echarts.EChartsVerticalAlign

/**
 * 标题组件，包含主标题和副标题。
 */
@Keep
class EChartsTitle {

    private var id: Any? = null
    private var show: Any? = null
    private var text: Any? = null
    private var link: Any? = null
    private var target: Any? = null
    private var textStyle: Any? = null
    private var subtext: Any? = null
    private var sublink: Any? = null
    private var subtarget: Any? = null
    private var subtextStyle: Any? = null
    private var textAlign: Any? = null
    private var textVerticalAlign: Any? = null
    private var triggerEvent: Any? = null
    private var padding: Any? = null
    private var itemGap: Any? = null
    private var zlevel: Any? = null
    private var z: Any? = null
    private var left: Any? = null
    private var top: Any? = null
    private var right: Any? = null
    private var bottom: Any? = null
    private var backgroundColor: Any? = null
    private var borderColor: Any? = null
    private var borderWidth: Any? = null
    private var borderRadius: Any? = null
    private var shadowBlur: Any? = null
    private var shadowColor: Any? = null
    private var shadowOffsetX: Any? = null
    private var shadowOffsetY: Any? = null


    /**
     * 组件 ID。默认不指定。指定则可用于在 option 或者 API 中引用组件。
     */
    fun id(value: String) {
        this.id = value
    }

    /**
     * 是否显示标题组件。
     */
    fun show(value: Boolean) {
        this.show = value
    }

    /**
     * 主标题文本，支持使用 \n 换行。
     */
    fun text(value: String) {
        this.text = value
    }

    /**
     * 主标题文本超链接。
     */
    fun link(value: String) {
        this.link = value
    }

    /**
     * 指定窗口打开主标题超链接。
     */
    fun target(@Target value: String) {
        this.target = value
    }

    /**
     * 主标题文字样式
     */
    fun textStyle(block: EChartsTextStyle.() -> Unit) {
        val style = EChartsTextStyle()
        block.invoke(style)
        this.textStyle = style
    }

    /**
     * 副标题文本，支持使用 \n 换行。
     */
    fun subtext(value: String) {
        this.subtext = value
    }

    /**
     * 副标题文本超链接。
     */
    fun sublink(value: String) {
        this.sublink = value
    }

    /**
     * 指定窗口打开副标题超链接，可选：
     */
    fun subtarget(@Target value: String) {
        this.subtarget = value
    }

    /**
     * 副标题文字样式
     */
    fun subtextStyle(block: EChartsTextStyle.() -> Unit) {
        val style = EChartsTextStyle()
        block.invoke(style)
        this.subtextStyle = style
    }

    /**
     * 整体（包括 text 和 subtext）的水平对齐。
     */
    fun textAlign(@EChartsHorizontalAlign value: String) {
        this.textAlign = value
    }

    /**
     * 整体（包括 text 和 subtext）的垂直对齐。
     */
    fun textVerticalAlign(@EChartsVerticalAlign value: String) {
        this.textVerticalAlign = value
    }

    /**
     * 是否触发事件。
     */
    fun triggerEvent(value: Boolean) {
        this.triggerEvent = value
    }

    /**
     * 标题内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距。
     */
    fun padding(value: Int) {
        this.padding = value
    }

    /**
     * 标题内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距。
     */
    fun padding(top: Int, right: Int, bottom: Int, left: Int) {
        this.padding = "[${top},${right},${bottom},${left}]"
    }

    /**
     * 主副标题之间的间距。
     */
    fun itemGap(px: Int) {
        this.itemGap = px
    }

    /**
     * 所有图形的 zlevel 值。
     * zlevel用于 Canvas 分层，不同zlevel值的图形会放置在不同的 Canvas 中，Canvas 分层是一种常见的优化手段。
     */
    fun zlevel(value: Number) {
        this.zlevel = value
    }

    /**
     * 组件的所有图形的z值。控制图形的前后顺序。z值小的图形会被z值大的图形覆盖。
     */
    fun z(value: Number) {
        this.z = value
    }

    /**
     * title 组件离容器左侧的距离。
     */
    fun left(@EChartsHorizontalAlign value: String) {
        this.left = value
    }

    /**
     * title 组件离容器左侧的距离。
     */
    fun left(px: Int) {
        this.left = px
    }

    /**
     * title 组件离容器左侧的距离。
     */
    fun left(percent: Float) {
        this.left = "${percent * 100}%"
    }

    /**
     * title 组件离容器上侧的距离。
     */
    fun top(@EChartsVerticalAlign value: String) {
        this.top = value
    }

    /**
     * title 组件离容器上侧的距离。
     */
    fun top(px: Int) {
        this.top = px
    }

    /**
     * title 组件离容器上侧的距离。
     */
    fun top(percent: Float) {
        this.top = "${percent * 100}%"
    }

    /**
     * title 组件离容器右侧的距离。
     */
    fun right(px: Int) {
        this.right = px
    }

    /**
     * title 组件离容器右侧的距离。
     */
    fun right(percent: Float) {
        this.right = "${percent * 100}%"
    }

    /**
     * title 组件离容器下侧的距离。
     */
    fun bottom(px: Int) {
        this.bottom = px
    }

    /**
     * title 组件离容器下侧的距离。
     */
    fun bottom(percent: Float) {
        this.bottom = "${percent * 100}%"
    }

    /**
     * 标题背景色，默认透明。
     */
    fun backgroundColor(value: String) {
        this.backgroundColor = value
    }

    /**
     * 标题背景色，默认透明。
     */
    fun backgroundColor(@ColorInt value: Int) {
        this.backgroundColor = EChartsUtils.colorToString(value)
    }

    /**
     * 标题的边框颜色。支持的颜色格式同 backgroundColor。
     */
    fun borderColor(value: String) {
        this.borderColor = value
    }

    /**
     * 标题的边框颜色。支持的颜色格式同 backgroundColor。
     */
    fun borderColor(@ColorInt value: Int) {
        this.borderColor = EChartsUtils.colorToString(value)
    }

    /**
     * 标题的边框线宽。
     */
    fun borderWidth(px: Int) {
        this.borderWidth = px
    }

    /**
     * 圆角半径，单位px，支持传入数组分别指定 4 个圆角半径。
     */
    fun borderRadius(value: Int) {
        this.borderRadius = value
    }

    /**
     * 圆角半径，单位px，支持传入数组分别指定 4 个圆角半径。
     */
    fun borderRadius(leftTop: Int, rightTop: Int, rightBottom: Int, leftBottom: Int) {
        this.borderRadius = "[${leftTop},${rightTop},${rightBottom},${leftBottom}]"
    }

    /**
     * 图形阴影的模糊大小。该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
     */
    fun shadowBlur(value: Number) {
        this.shadowBlur = value
    }

    /**
     * 阴影颜色。支持的格式同color。
     */
    fun shadowColor(value: String) {
        this.shadowColor = value
    }

    /**
     * 阴影颜色。支持的格式同color。
     */
    fun shadowColor(@ColorInt value: Int) {
        this.shadowColor = EChartsUtils.colorToString(value)
    }

    /**
     * 阴影水平方向上的偏移距离。
     */
    fun shadowOffsetX(px: Int) {
        this.shadowOffsetX = px
    }

    /**
     * 阴影垂直方向上的偏移距离。
     */
    fun shadowOffsetY(px: Int) {
        this.shadowOffsetY = px
    }

    @StringDef("self", "blank")
    annotation class Target
}
