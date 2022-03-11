package com.chooongg.echarts.options

import androidx.annotation.ColorInt
import androidx.annotation.Keep
import androidx.annotation.StringDef
import com.chooongg.echarts.EChartsHorizontalAlign
import com.chooongg.echarts.EChartsIcon
import com.chooongg.echarts.EChartsUtils
import com.chooongg.echarts.EChartsVerticalAlign
import org.json.JSONArray
import org.json.JSONObject

/**
 * 图例组件。
 * 图例组件展现了不同系列的标记(symbol)，颜色和名字。可以通过点击图例控制哪些系列不显示。
 */
@Keep
class EChartsLegend {

    private var type: Any? = null
    private var id: Any? = null
    private var show: Any? = null
    private var zlevel: Any? = null
    private var z: Any? = null
    private var left: Any? = null
    private var top: Any? = null
    private var right: Any? = null
    private var bottom: Any? = null
    private var width: Any? = null
    private var height: Any? = null
    private var orient: Any? = null
    private var align: Any? = null
    private var padding: Any? = null
    private var itemGap: Any? = null
    private var itemWidth: Any? = null
    private var itemHeight: Any? = null
    private var itemStyle: Any? = null
    private var lineStyle: Any? = null
    private var symbolRotate: Any? = null
    private var formatter: Any? = null
    private var selectedMode: Any? = null
    private var inactiveColor: Any? = null
    private var inactiveBorderColor: Any? = null
    private var inactiveBorderWidth: Any? = null
    private var selected: Any? = null
    private var textStyle: Any? = null
    private var tooltip: Any? = null
    private var icon: Any? = null
    private var data: Any? = null
    private var backgroundColor: Any? = null
    private var borderColor: Any? = null
    private var borderWidth: Any? = null
    private var borderRadius: Any? = null
    private var shadowBlur: Any? = null
    private var shadowColor: Any? = null
    private var shadowOffsetX: Any? = null
    private var shadowOffsetY: Any? = null
    private var scrollDataIndex: Any? = null
    private var pageButtonItemGap: Any? = null
    private var pageButtonGap: Any? = null
    private var pageButtonPosition: Any? = null
    private var pageFormatter: Any? = null
    private var pageIconColor: Any? = null
    private var pageIconInactiveColor: Any? = null
    private var pageIconSize: Any? = null
    private var pageTextStyle: Any? = null
    private var animation: Any? = null
    private var animationDurationUpdate: Any? = null
    private var emphasis: Any? = null
    private var selector: Any? = null
    private var selectorLabel: Any? = null
    private var selectorPosition: Any? = null
    private var selectorItemGap: Any? = null
    private var selectorButtonGap: Any? = null


    /**
     * 图例的类型。可选值：
     * 'plain'：普通图例。缺省就是普通图例。
     * 'scroll'：可滚动翻页的图例。当图例数量较多时可以使用。
     */
    fun type(@EChartsColor.Type value: String) {
        this.type = value
    }

    /**
     * 组件 ID。默认不指定。指定则可用于在 option 或者 API 中引用组件。
     */
    fun id(value: String) {
        this.id = value
    }

    /**
     * 是否显示
     */
    fun show(value: Boolean) {
        this.show = value
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
     * 图例组件离容器左侧的距离。
     */
    fun left(@EChartsHorizontalAlign value: String) {
        this.left = value
    }

    /**
     * 图例组件离容器左侧的距离。
     */
    fun left(px: Int) {
        this.left = px
    }

    /**
     * 图例组件离容器左侧的距离。
     */
    fun left(percent: Float) {
        this.left = "${percent * 100}%"
    }

    /**
     * 图例组件离容器上侧的距离。
     */
    fun top(@EChartsVerticalAlign value: String) {
        this.top = value
    }

    /**
     * 图例组件离容器上侧的距离。
     */
    fun top(px: Int) {
        this.top = px
    }

    /**
     * 图例组件离容器上侧的距离。
     */
    fun top(percent: Float) {
        this.top = "${percent * 100}%"
    }

    /**
     * 图例组件离容器右侧的距离。
     */
    fun right(px: Int) {
        this.right = px
    }

    /**
     * 图例组件离容器右侧的距离。
     */
    fun right(percent: Float) {
        this.right = "${percent * 100}%"
    }

    /**
     * 图例组件离容器下侧的距离。
     */
    fun bottom(px: Int) {
        this.bottom = px
    }

    /**
     * 图例组件离容器下侧的距离。
     */
    fun bottom(percent: Float) {
        this.bottom = "${percent * 100}%"
    }

    /**
     * 图例组件的宽度。默认自适应。
     */
    fun width(px: Int) {
        this.width = px
    }

    /**
     * 图例组件的高度。默认自适应。
     */
    fun height(px: Int) {
        this.height = px
    }

    /**
     * 图例列表的布局朝向。
     */
    fun orient(@Orient value: String) {
        this.orient = value
    }

    /**
     * 图例标记和文本的对齐。
     * 默认自动，根据组件的位置和 orient 决定，当组件的 left 值为 'right' 以及纵向布局（orient 为 'vertical'）的时候为右对齐，即为 'right'。
     */
    fun align(@Align value: String) {
        this.align = value
    }

    /**
     * 图例内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距。
     */
    fun padding(value: Int) {
        this.padding = value
    }

    /**
     * 图例内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距。
     */
    fun padding(top: Int, right: Int, bottom: Int, left: Int) {
        this.padding = "[${top},${right},${bottom},${left}]"
    }

    /**
     * 图例每项之间的间隔。
     * 横向布局时为水平间隔，纵向布局时为纵向间隔。
     */
    fun itemGap(px: Int) {
        this.itemGap = px
    }

    /**
     * 图例标记的图形宽度。
     */
    fun itemWidth(px: Int) {
        this.itemWidth = px
    }

    /**
     * 图例标记的图形高度。
     */
    fun itemHeight(px: Int) {
        this.itemHeight = px
    }

    /**
     * 图例的图形样式。
     * 其属性的取值为 'inherit' 时，表示继承系列中的属性值。
     */
    fun itemStyle(block: EChartsLegendItemStyle.() -> Unit) {
        val style = EChartsLegendItemStyle()
        block.invoke(style)
        this.itemStyle = style
    }

    /**
     * 图例图形中线的样式，用于诸如折线图图例横线的样式设置。
     * 其属性的取值为 'inherit' 时，表示继承系列中的属性值。
     */
    fun lineStyle(block: EChartsLegendLineStyle.() -> Unit) {
        val style = EChartsLegendLineStyle()
        block.invoke(style)
        this.lineStyle = style
    }

    /**
     * 图形旋转角度，类型为 number | 'inherit'。
     * 如果为 'inherit'，表示取系列的 symbolRotate。
     */
    fun symbolRotate(value: Int) {
        this.symbolRotate = value
    }

    /**
     * 用来格式化图例文本，支持字符串模板和回调函数两种形式。
     */
    fun formatter(value: String) {
        this.formatter = value
    }

    /**
     * 图例选择的模式，控制是否可以通过点击图例改变系列的显示状态。
     * 默认开启图例选择，可以设成 false 关闭。
     */
    fun selectedMode(value: Boolean) {
        this.selectedMode = value
    }

    /**
     * 图例选择的模式，控制是否可以通过点击图例改变系列的显示状态。
     * 除此之外也可以设成 'single' 或者 'multiple' 使用单选或者多选模式。
     */
    fun selectedMode(@SelectedMode value: String) {
        this.selectedMode = value
    }

    /**
     * 图例关闭时的颜色。
     */
    fun inactiveColor(value: String) {
        this.inactiveColor = value
    }

    /**
     * 图例关闭时的颜色。
     */
    fun inactiveColor(@ColorInt value: Int) {
        this.inactiveColor = EChartsUtils.colorToString(value)
    }

    /**
     * 图例关闭时的描边颜色。
     */
    fun inactiveBorderColor(value: String) {
        this.inactiveBorderColor = value
    }

    /**
     * 图例关闭时的描边颜色。
     */
    fun inactiveBorderColor(@ColorInt value: Int) {
        this.inactiveBorderColor = EChartsUtils.colorToString(value)
    }

    /**
     * 图例关闭时的描边粗细。
     */
    fun inactiveBorderWidth(px: Int) {
        this.inactiveBorderWidth = px
    }

    /**
     * 图例关闭时的描边粗细。
     * 如果为 'auto' 表示：如果系列存在描边，则取 2，如果系列不存在描边，则取 0。
     * 如果为 'inherit' 则表示：始终取系列的描边粗细。
     */
    fun inactiveBorderWidth(@InactiveBorderWidth value: String) {
        this.inactiveBorderWidth = value
    }

    /**
     * 图例选中状态表。
     * 示例：
     * selected: {
     *     // 选中'系列1'
     *     '系列1': true,
     *     // 不选中'系列2'
     *     '系列2': false
     * }
     */
    fun selected(vararg item: Pair<String, Boolean>) {
        val json = JSONObject()
        item.forEach { json.put(it.first, it.second) }
        this.selected = json.toString()
    }

    /**
     * 图例选中状态表。
     * 示例：
     * selected: {
     *     // 选中'系列1'
     *     '系列1': true,
     *     // 不选中'系列2'
     *     '系列2': false
     * }
     */
    fun selected(items: MutableMap<String, Boolean>) {
        val json = JSONObject()
        items.forEach { json.put(it.key, it.value) }
        this.selected = json.toString()
    }

    /**
     * 图例的公用文本样式。
     */
    fun textStyle(block: EChartsTextStyle.() -> Unit) {
        val style = EChartsTextStyle()
        block.invoke(style)
        this.textStyle = style
    }

    /**
     * 图例的 tooltip 配置，配置项同 tooltip。
     * 默认不显示，可以在 legend 文字很多的时候对文字做裁剪并且开启 tooltip
     */
    fun tooltip(block: EChartsTooltip.() -> Unit) {
        val tooltip = EChartsTooltip()
        block.invoke(tooltip)
        this.tooltip = tooltip
    }

    /**
     * 图例项的 icon。
     * ECharts 提供的标记类型包括
     * 'circle', 'rect', 'roundRect', 'triangle', 'diamond', 'pin', 'arrow', 'none'
     */
    fun icon(@EChartsIcon value: String) {
        this.icon = value
    }

    /**
     * 图例的数据数组。数组项通常为一个字符串，每一项代表一个系列的 name（如果是饼图，也可以是饼图单个数据的 name）。
     * 图例组件会自动根据对应系列的图形标记（symbol）来绘制自己的颜色和标记，特殊字符串 ''（空字符串）或者 '\n'（换行字符串）用于图例的换行。
     */
    fun data(vararg block: EChartsLegendData.() -> Unit) {
        val data = ArrayList<EChartsLegendData>()
        block.forEach {
            val item = EChartsLegendData()
            it.invoke(item)
            data.add(item)
        }
        this.data = data
    }

    /**
     * 图例的数据数组。数组项通常为一个字符串，每一项代表一个系列的 name（如果是饼图，也可以是饼图单个数据的 name）。
     * 图例组件会自动根据对应系列的图形标记（symbol）来绘制自己的颜色和标记，特殊字符串 ''（空字符串）或者 '\n'（换行字符串）用于图例的换行。
     */
    fun data(value: MutableList<EChartsLegendData>) {
        this.data = value
    }

    /**
     * 图例背景色，默认透明。
     */
    fun backgroundColor(value: String) {
        this.backgroundColor = value
    }

    /**
     * 图例背景色，默认透明。
     */
    fun backgroundColor(@ColorInt value: Int) {
        this.backgroundColor = EChartsUtils.colorToString(value)
    }

    /**
     * 图例的边框颜色。支持的颜色格式同 backgroundColor。
     */
    fun borderColor(value: String) {
        this.borderColor = value
    }

    /**
     * 图例的边框颜色。支持的颜色格式同 backgroundColor。
     */
    fun borderColor(@ColorInt value: Int) {
        this.borderColor = EChartsUtils.colorToString(value)
    }

    /**
     * 图例的边框线宽。
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

    /**
     * 图例当前最左上显示项的 dataIndex。
     */
    fun scrollDataIndex(value: Int) {
        this.scrollDataIndex = value
    }

    /**
     * 图例控制块中，按钮和页信息之间的间隔。
     * type 为 'scroll' 时有效。
     */
    fun pageButtonItemGap(px: Int) {
        this.pageButtonItemGap = px
    }

    /**
     * 图例控制块和图例项之间的间隔。
     * type 为 'scroll' 时有效。
     */
    fun pageButtonGap(px: Int) {
        this.pageButtonGap = px
    }

    /**
     * 图例控制块的位置。
     * type 为 'scroll' 时有效。
     * 可选值为：
     * 'start'：控制块在左或上。
     * 'end'：控制块在右或下。
     */
    fun pageButtonPosition(@Position value: String) {
        this.pageButtonPosition = value
    }

    /**
     * 图例控制块中，页信息的显示格式。
     * type 为 'scroll' 时有效。
     * 默认为 '{current}/{total}'，其中 {current} 是当前页号（从 1 开始计数），{total} 是总页数。
     * 如果 pageFormatter 使用函数，须返回字符串，参数为：
     * {
     *     current: number
     *     total: number
     * }
     */
    fun pageFormatter(value: String) {
        this.pageFormatter = value
    }

    /**
     * 翻页按钮的颜色。
     * type 为 'scroll' 时有效。
     */
    fun pageIconColor(value: String) {
        this.pageIconColor = value
    }

    /**
     * 翻页按钮的颜色。
     * type 为 'scroll' 时有效。
     */
    fun pageIconColor(@ColorInt value: Int) {
        this.pageIconColor = EChartsUtils.colorToString(value)
    }

    /**
     * 翻页按钮不激活时（即翻页到头时）的颜色。
     * type 为 'scroll' 时有效。
     */
    fun pageIconInactiveColor(value: String) {
        this.pageIconInactiveColor = value
    }

    /**
     * 翻页按钮不激活时（即翻页到头时）的颜色。
     * type 为 'scroll' 时有效。
     */
    fun pageIconInactiveColor(@ColorInt value: Int) {
        this.pageIconInactiveColor = EChartsUtils.colorToString(value)
    }

    /**
     * 翻页按钮的大小。
     * type 为 'scroll' 时有效。
     * 可以是数字，也可以是数组，如 [10, 3]，表示 [宽，高]。
     */
    fun pageIconSize(px: Int) {
        this.pageIconSize = px
    }

    /**
     * 翻页按钮的大小。
     * type 为 'scroll' 时有效。
     * 可以是数字，也可以是数组，如 [10, 3]，表示 [宽，高]。
     */
    fun pageIconSize(width: Int, height: Int) {
        this.pageIconSize = "[${width},${height}]"
    }

    /**
     * 图例页信息的文字样式。
     * type 为 'scroll' 时有效。
     */
    fun pageTextStyle(block: EChartsTextStyle.() -> Unit) {
        val style = EChartsTextStyle()
        block.invoke(style)
        this.pageTextStyle = style
    }

    /**
     * 图例翻页是否使用动画。
     */
    fun animation(value: Boolean) {
        this.animation = value
    }

    /**
     * 图例翻页时的动画时长。
     */
    fun animationDurationUpdate(value: Long) {
        this.animationDurationUpdate = value
    }

    /**
     * 强调
     */
    fun emphasis(block: EChartsLegendEmphasis.() -> Unit) {
        val emphasis = EChartsLegendEmphasis()
        block.invoke(emphasis)
        this.emphasis = emphasis
    }

    /**
     * 图例组件中的选择器按钮，目前包括全选和反选两种功能。
     * 默认不显示，用户可手动开启，也可以手动配置每个按钮的标题。
     */
    fun selector(value: Boolean) {
        this.selector = value
    }

    /**
     * 图例组件中的选择器按钮，目前包括全选和反选两种功能。
     * 默认不显示，用户可手动开启，也可以手动配置每个按钮的标题。
     * selector: ['all', 'inverse']
     */
    fun selector(@Selector vararg value: String) {
        val json = JSONArray()
        value.forEach { json.put(it) }
        this.selector = json.toString()
    }

    /**
     * 图例组件中的选择器按钮，目前包括全选和反选两种功能。
     * 默认不显示，用户可手动开启，也可以手动配置每个按钮的标题。
     * selector: [
     *     {
     *         type: 'all or inverse',
     *         // 可以是任意你喜欢的 title
     *         title: '全选'
     *         },
     *         {
     *         type: 'inverse',
     *         title: '反选'
     *     }
     * ]
     */
    fun selector(vararg value: Pair<@Selector String, String>) {
        val json = JSONArray()
        value.forEach {
            val jsonObject = JSONObject()
            jsonObject.put("type", it.first)
            jsonObject.put("title", it.second)
            json.put(jsonObject)
        }
        this.selector = json.toString()
    }

    /**
     * 选择器按钮的文本标签样式，默认显示。
     */
    fun selectorLabel(block: EChartsLegendLabel.() -> Unit) {
        val label = EChartsLegendLabel()
        block.invoke(label)
        this.selectorLabel = label
    }

    /**
     * 选择器的位置，可以放在图例的尾部或者头部，对应的值分别为 'end' 和 'start'。
     * 默认情况下，图例横向布局的时候，选择器放在图例的尾部；图例纵向布局的时候，选择器放在图例的头部。
     */
    fun selectorPosition(@Position value: String) {
        this.selectorPosition = value
    }

    /**
     * 选择器按钮之间的间隔。
     */
    fun selectorItemGap(px: Int) {
        this.selectorItemGap = px
    }

    /**
     * 选择器按钮与图例组件之间的间隔。
     */
    fun selectorButtonGap(px: Int) {
        this.selectorButtonGap = px
    }

    @StringDef("plain", "scroll")
    annotation class Type

    @StringDef("horizontal", "vertical")
    annotation class Orient

    @StringDef("auto", "left", "right")
    annotation class Align

    @StringDef("single", "multiple")
    annotation class SelectedMode

    @StringDef("auto", "inherit")
    annotation class InactiveBorderWidth

    @StringDef("start", "end")
    annotation class Position

    @Target(AnnotationTarget.VALUE_PARAMETER, AnnotationTarget.TYPE)
    @StringDef("all", "inverse")
    annotation class Selector
}