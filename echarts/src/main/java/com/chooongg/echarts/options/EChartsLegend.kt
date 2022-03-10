package com.chooongg.echarts.options

import androidx.annotation.Keep

/**
 * 图例组件。
 * 图例组件展现了不同系列的标记(symbol)，颜色和名字。可以通过点击图例控制哪些系列不显示。
 */
@Keep
class EChartsLegend {

    /**
     * 图例的类型。可选值：
     * 'plain'：普通图例。缺省就是普通图例。
     * 'scroll'：可滚动翻页的图例。当图例数量较多时可以使用。
     */
    private var type: Any? = null

    /**
     * 组件 ID。默认不指定。指定则可用于在 option 或者 API 中引用组件。
     */
    private var id: Any? = null

    /**
     * 是否显示
     */
    private var show: Any? = null

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
     * 图例组件离容器左侧的距离。
     */
    private var left: Any? = null

    /**
     * 图例组件离容器上侧的距离。
     */
    private var top: Any? = null

    /**
     * 图例组件离容器右侧的距离。
     */
    private var right: Any? = null

    /**
     * 图例组件离容器下侧的距离。
     */
    private var bottom: Any? = null

    /**
     * 图例组件的宽度。默认自适应。
     */
    private var width: Any? = null

    /**
     * 图例组件的高度。默认自适应。
     */
    private var height: Any? = null

    /**
     * 图例列表的布局朝向。
     */
    private var orient: Any? = null

    /**
     * 图例标记和文本的对齐。
     * 默认自动，根据组件的位置和 orient 决定，当组件的 left 值为 'right' 以及纵向布局（orient 为 'vertical'）的时候为右对齐，即为 'right'。
     */
    private var align: Any? = null

    /**
     * 图例内边距，单位px，默认各方向内边距为5，接受数组分别设定上右下左边距。
     */
    private var padding: Any? = null

    /**
     * 图例每项之间的间隔。
     * 横向布局时为水平间隔，纵向布局时为纵向间隔。
     */
    private var itemGap: Any? = null

    /**
     * 图例标记的图形宽度。
     */
    private var itemWidth: Any? = null

    /**
     * 图例标记的图形高度。
     */
    private var itemHeight: Any? = null

    /**
     * 图例的图形样式。
     * 其属性的取值为 'inherit' 时，表示继承系列中的属性值。
     */
    private var itemStyle: Any? = null

    /**
     * 图例图形中线的样式，用于诸如折线图图例横线的样式设置。
     * 其属性的取值为 'inherit' 时，表示继承系列中的属性值。
     */
    private var lineStyle: Any? = null

    /**
     * 图形旋转角度，类型为 number | 'inherit'。
     * 如果为 'inherit'，表示取系列的 symbolRotate。
     */
    private var symbolRotate: Any? = null

    /**
     * 用来格式化图例文本，支持字符串模板和回调函数两种形式。
     */
    private var formatter: Any? = null

    /**
     * 图例选择的模式，控制是否可以通过点击图例改变系列的显示状态。
     * 默认开启图例选择，可以设成 false 关闭。
     */
    private var selectedMode: Any? = null

    /**
     * 图例关闭时的颜色。
     */
    private var inactiveColor: Any? = null

    /**
     * 图例关闭时的描边颜色。
     */
    private var inactiveBorderColor: Any? = null

    /**
     * 图例关闭时的描边粗细。
     * 如果为 'auto' 表示：如果系列存在描边，则取 2，如果系列不存在描边，则取 0。
     * 如果为 'inherit' 则表示：始终取系列的描边粗细。
     */
    private var inactiveBorderWidth: Any? = null

    /**
     * 图例选中状态表。
     */
    private var selected: Any? = null

    /**
     * 图例的公用文本样式。
     */
    private var textStyle: Any? = null

    /**
     * 图例的 tooltip 配置，配置项同 tooltip。
     * 默认不显示，可以在 legend 文字很多的时候对文字做裁剪并且开启 tooltip
     */
    private var tooltip: Any? = null

    /**
     * 图例项的 icon。
     * ECharts 提供的标记类型包括
     * 'circle', 'rect', 'roundRect', 'triangle', 'diamond', 'pin', 'arrow', 'none'
     */
    private var icon: Any? = null

    /**
     * 图例的数据数组。数组项通常为一个字符串，每一项代表一个系列的 name（如果是饼图，也可以是饼图单个数据的 name）。
     * 图例组件会自动根据对应系列的图形标记（symbol）来绘制自己的颜色和标记，特殊字符串 ''（空字符串）或者 '\n'（换行字符串）用于图例的换行。
     */
    private var data: Any? = null

    /**
     * 图例背景色，默认透明。
     */
    private var backgroundColor: Any? = null

    /**
     * 图例的边框颜色。支持的颜色格式同 backgroundColor。
     */
    private var borderColor: Any? = null

    /**
     * 图例的边框线宽。
     */
    private var borderWidth: Any? = null

    /**
     * 圆角半径，单位px，支持传入数组分别指定 4 个圆角半径。
     */
    private var borderRadius: Any? = null

    /**
     * 图形阴影的模糊大小。
     * 该属性配合 shadowColor,shadowOffsetX, shadowOffsetY 一起设置图形的阴影效果。
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

    /**
     * 图例当前最左上显示项的 dataIndex。
     */
    private var scrollDataIndex: Any? = null

    /**
     * 图例控制块中，按钮和页信息之间的间隔。
     * type 为 'scroll' 时有效。
     */
    private var pageButtonItemGap: Any? = null

    /**
     * 图例控制块和图例项之间的间隔。
     * type 为 'scroll' 时有效。
     */
    private var pageButtonGap: Any? = null

    /**
     * 图例控制块的位置。
     * type 为 'scroll' 时有效。
     * 可选值为：
     * 'start'：控制块在左或上。
     * 'end'：控制块在右或下。
     */
    private var pageButtonPosition: Any? = null

    /**
     * 图例控制块中，页信息的显示格式。
     * type 为 'scroll' 时有效。
     * 默认为 '{current}/{total}'，其中 {current} 是当前页号（从 1 开始计数），{total} 是总页数。
     */
    private var pageFormatter: Any? = null

    /**
     * 图例控制块的图标。
     * type 为 'scroll' 时有效。
     */
    private var pageIcons: Any? = null

    /**
     * 翻页按钮的颜色。
     * type 为 'scroll' 时有效。
     */
    private var pageIconColor: Any? = null

    /**
     * 翻页按钮不激活时（即翻页到头时）的颜色。
     * type 为 'scroll' 时有效。
     */
    private var pageIconInactiveColor: Any? = null

    /**
     * 翻页按钮的大小。
     * type 为 'scroll' 时有效。
     * 可以是数字，也可以是数组，如 [10, 3]，表示 [宽，高]。
     */
    private var pageIconSize: Any? = null

    /**
     * 图例页信息的文字样式。
     * type 为 'scroll' 时有效。
     */
    private var pageTextStyle: Any? = null

    /**
     * 图例翻页是否使用动画。
     */
    private var animation: Any? = null

    /**
     * 图例翻页时的动画时长。
     */
    private var animationDurationUpdate: Any? = null

    /**
     * 强调
     */
    private var emphasis: Any? = null

    /**
     * 图例组件中的选择器按钮，目前包括全选和反选两种功能。
     * 默认不显示，用户可手动开启，也可以手动配置每个按钮的标题。
     */
    private var selector: Any? = null

    /**
     * 选择器按钮的文本标签样式，默认显示。
     */
    private var selectorLabel: Any? = null

    /**
     * 选择器的位置，可以放在图例的尾部或者头部，对应的值分别为 'end' 和 'start'。
     * 默认情况下，图例横向布局的时候，选择器放在图例的尾部；图例纵向布局的时候，选择器放在图例的头部。
     */
    private var selectorPosition: Any? = null

    /**
     * 选择器按钮之间的间隔。
     */
    private var selectorItemGap: Any? = null

    /**
     * 选择器按钮与图例组件之间的间隔。
     */
    private var selectorButtonGap: Any? = null
}