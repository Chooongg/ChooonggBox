package com.chooongg.abc.main.fragment

import android.os.Bundle
import com.chooongg.abc.databinding.FragmentHomeBinding
import com.chooongg.core.annotation.TopAppBar
import com.chooongg.core.fragment.BoxBindingFragment
import com.chooongg.echarts.options.EChartsOption
import com.chooongg.echarts.options.EChartsTitle
import com.chooongg.echarts.options.EChartsXAxis

@TopAppBar(TopAppBar.TYPE_SMALL)
class HomeFragment : BoxBindingFragment<FragmentHomeBinding>() {

    override fun initConfig(savedInstanceState: Bundle?) {
        binding.echartsView.setOptions(
            EChartsOption(
                title = EChartsTitle(
                    text = "测试标题"
                ),
                xAxis = EChartsXAxis(
                    type = "category",
                    data = mutableListOf("Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun")
                ),
                yAxis = EChartsXAxis(
                    type = "value"
                )
            )
        )
    }

    override fun initContent(savedInstanceState: Bundle?) {
    }

    override fun initContentByLazy() {
    }
}