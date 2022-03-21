package com.chooongg.echarts.options

import androidx.annotation.Keep
import com.chooongg.echarts.options.axis.EChartsAngleAxis
import com.chooongg.echarts.options.axis.EChartsGridAxis
import com.chooongg.echarts.options.axis.EChartsRadiusAxis

/**
 * ECharts 配置项
 */
@Keep
class EChartsOption {

    private var title: Any? = null
    private var legend: Any? = null
    private var grid: Any? = null
    private var xAxis: Any? = null
    private var yAxis: Any? = null
    private var polar: Any? = null
    private var radiusAxis: Any? = null
    private var angleAxis: Any? = null
    private var radar: Any? = null
    private var dataZoom: Any? = null
    private var visualMap: Any? = null

    /**
     * 提示框组件。
     */
    private var tooltip: Any? = null

    /**
     * 这是坐标轴指示器（axisPointer）的全局公用设置。
     */
    private var axisPointer: Any? = null

    /**
     * 工具栏。内置有导出图片，数据视图，动态类型切换，数据区域缩放，重置五个工具。
     */
    private var toolbox: Any? = null

    /**
     * brush 是区域选择组件，用户可以选择图中一部分数据，从而便于向用户展示被选中数据，或者他们的一些统计计算结果。
     */
    private var brush: Any? = null

    /**
     * 地理坐标系组件。
     */
    private var geo: Any? = null

    /**
     * 平行坐标系（Parallel Coordinates） 是一种常用的可视化高维数据的图表。
     */
    private var parallel: Any? = null

    /**
     * 这个组件是平行坐标系中的坐标轴。
     */
    private var parallelAxis: Any? = null

    /**
     * 单轴。可以被应用到散点图中展现一维数据
     */
    private var singleAxis: Any? = null

    /**
     * timeline 组件，提供了在多个 ECharts option 间进行切换、播放等操作的功能。
     */
    private var timeline: Any? = null

    /**
     * graphic 是原生图形元素组件。
     */
    private var graphic: Any? = null

    /**
     * 日历坐标系组件。
     */
    private var calendar: Any? = null

    /**
     * 数据集（dataset）组件用于单独的数据集声明
     * 从而数据可以单独管理，被多个组件复用，并且可以自由指定数据到视觉的映射。
     */
    private var dataset: Any? = null

    /**
     * W3C 制定了无障碍富互联网应用规范集（WAI-ARIA，the Accessible Rich Internet Applications Suite）
     * 致力于使得网页内容和网页应用能够被更多残障人士访问。
     * Apache ECharts 4 遵从这一规范，支持自动根据图表配置项智能生成描述，使得盲人可以在朗读设备的帮助下了解图表内容，
     * 让图表可以被更多人群访问。除此之外，Apache ECharts 5 新增支持贴花纹理，作为颜色的辅助表达，进一步用以区分数据。
     */
    private var aria: Any? = null

    /**
     * 各种图表
     */
    private var series: Any? = null

    private var darkMode: Boolean? = null

    /**
     * 调色盘颜色列表。如果系列没有设置颜色，则会依次循环从该列表中取颜色作为系列颜色。
     * 默认为：['#5470c6', '#91cc75', '#fac858', '#ee6666', '#73c0de', '#3ba272', '#fc8452', '#9a60b4', '#ea7ccc']
     */
    private var color: Any? = null

    /**
     * 背景色，默认无背景。
     */
    private var backgroundColor: Any? = null

    /**
     * 全局的字体样式。
     */
    private var textStyle: Any? = null

    /**
     * 是否开启动画。
     */
    private var animation: Any? = null

    /**
     * 是否开启动画的阈值，当单个系列显示的图形数量大于这个阈值时会关闭动画。
     */
    private var animationThreshold: Any? = null

    /**
     * 初始动画的时长，支持回调函数，可以通过每个数据返回不同的时长实现更戏剧的初始动画效果：
     * animationDuration: function (idx) {
     *     // 越往后的数据时长越大
     *     return idx * 100;
     * }
     */
    private var animationDuration: Any? = null

    /**
     * 初始动画的缓动效果。
     */
    private var animationEasing: Any? = null

    /**
     * 初始动画的延迟，支持回调函数，可以通过每个数据返回不同的 delay 时间实现更戏剧的初始动画效果。
     * animationDuration: function (idx) {
     *     // 越往后的数据延迟越大
     *     return idx * 100;
     * }
     */
    private var animationDelay: Any? = null

    /**
     * 数据更新动画的时长。
     */
    private var animationDurationUpdate: Any? = null

    /**
     * 数据更新动画的缓动效果。
     */
    private var animationEasingUpdate: Any? = null

    /**
     * 数据更新动画的延迟，支持回调函数，可以通过每个数据返回不同的 delay 时间实现更戏剧的更新动画效果。
     * animationDuration: function (idx) {
     *     // 越往后的数据延迟越大
     *     return idx * 100;
     * }
     */
    private var animationDelayUpdate: Any? = null

    /**
     * 状态切换的动画配置，支持在每个系列里设置单独针对该系列的配置。
     */
    private var stateAnimation: Any? = null

    /**
     * 图形的混合模式，不同的混合模式见
     * https://developer.mozilla.org/zh-CN/docs/Web/API/CanvasRenderingContext2D/globalCompositeOperation
     */
    private var blendMode: Any? = null

    /**
     * 图形数量阈值，决定是否开启单独的 hover 层，在整个图表的图形数量大于该阈值时开启单独的 hover 层。
     */
    private var hoverLayerThreshold: Any? = null

    /**
     * 是否使用 UTC 时间。
     */
    private var useUTC: Any? = null

    /**
     * 移动端自适应。
     */
    private var media: Any? = null

    /**
     * 标题组件，包含主标题和副标题。
     */
    fun title(block: EChartsTitle.() -> Unit) {
        val title = EChartsTitle()
        block.invoke(title)
        this.title = title
    }

    /**
     * 图例组件。
     */
    fun legend(block: EChartsLegend.() -> Unit) {
        val legend = EChartsLegend()
        block.invoke(legend)
        this.legend = legend
    }

    /**
     * 直角坐标系内绘图网格，单个 grid 内最多可以放置上下两个 X 轴，左右两个 Y 轴。
     */
    fun grid(block: EChartsGrid.() -> Unit) {
        val grid = EChartsGrid()
        block.invoke(grid)
        this.grid = grid
    }

    /**
     * 直角坐标系 grid 中的 x 轴，一般情况下单个 grid 组件最多只能放上下两个 x 轴
     * 多于两个 x 轴需要通过配置 offset 属性防止同个位置多个 x 轴的重叠。
     */
    fun xAxis(block: EChartsGridAxis.() -> Unit) {
        val axis = EChartsGridAxis()
        block.invoke(axis)
        this.xAxis = axis
    }

    /**
     * 直角坐标系 grid 中的 x 轴，一般情况下单个 grid 组件最多只能放上下两个 x 轴
     * 多于两个 x 轴需要通过配置 offset 属性防止同个位置多个 x 轴的重叠。
     */
    fun xAxis(vararg block: EChartsGridAxis.() -> Unit) {
        val axisList = ArrayList<EChartsGridAxis>()
        block.forEach {
            val axis = EChartsGridAxis()
            it.invoke(axis)
            axisList.add(axis)
        }
        this.xAxis = axisList
    }

    /**
     * 直角坐标系 grid 中的 y 轴，一般情况下单个 grid 组件最多只能放左右两个 y 轴
     * 多于两个 y 轴需要通过配置 offset 属性防止同个位置多个 Y 轴的重叠。
     */
    fun yAxis(block: EChartsGridAxis.() -> Unit) {
        val axis = EChartsGridAxis()
        block.invoke(axis)
        this.yAxis = axis
    }

    /**
     * 直角坐标系 grid 中的 y 轴，一般情况下单个 grid 组件最多只能放左右两个 y 轴
     * 多于两个 y 轴需要通过配置 offset 属性防止同个位置多个 Y 轴的重叠。
     */
    fun yAxis(vararg block: EChartsGridAxis.() -> Unit) {
        val axisList = ArrayList<EChartsGridAxis>()
        block.forEach {
            val axis = EChartsGridAxis()
            it.invoke(axis)
            axisList.add(axis)
        }
        this.yAxis = axisList
    }

    /**
     * 极坐标系，可以用于散点图和折线图。每个极坐标系拥有一个角度轴和一个半径轴。
     */
    fun polar(block: EChartsPolar.() -> Unit) {
        val polar = EChartsPolar()
        block.invoke(polar)
        this.polar = polar
    }

    /**
     * 极坐标系的径向轴。
     */
    fun radiusAxis(block: EChartsRadiusAxis.() -> Unit) {
        val axis = EChartsRadiusAxis()
        block.invoke(axis)
        this.radiusAxis = axis
    }

    /**
     * 极坐标系的径向轴。
     */
    fun angleAxis(block: EChartsAngleAxis.() -> Unit) {
        val axis = EChartsAngleAxis()
        block.invoke(axis)
        this.angleAxis = axis
    }

    /**
     * 雷达图坐标系组件，只适用于雷达图。
     */
    fun radar(block: EChartsRadar.() -> Unit) {
        val radar = EChartsRadar()
        block.invoke(radar)
        this.radar = radar
    }

    /**
     * dataZoom 组件 用于区域缩放，从而能自由关注细节的数据信息，或者概览数据整体，或者去除离群点的影响。
     */
    fun dataZoom(block: EChartsDataZoom.() -> Unit) {
        val dataZoom = EChartsDataZoom()
        block.invoke(dataZoom)
        this.dataZoom = dataZoom
    }

    /**
     * 是否是暗黑模式，默认会根据背景色 backgroundColor 的亮度自动设置。
     * 如果是设置了容器的背景色而无法判断到，就可以使用该配置手动指定，echarts 会根据是否是暗黑模式调整文本等的颜色。
     */
    fun darkMode(value: Boolean) {
        this.darkMode = value
    }
}