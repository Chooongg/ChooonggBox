package com.chooongg.abc.main.fragment

import android.graphics.Color
import android.os.Bundle
import com.chooongg.abc.databinding.FragmentHomeBinding
import com.chooongg.core.annotation.TopAppBar
import com.chooongg.core.fragment.BoxBindingFragment
import com.chooongg.echarts.options.EChartsOption
import com.chooongg.ext.logD

@TopAppBar(TopAppBar.TYPE_SMALL)
class HomeFragment : BoxBindingFragment<FragmentHomeBinding>() {

    override fun initConfig(savedInstanceState: Bundle?) {
        binding.echartsView.setOptions(EChartsOption {
            title {
                text("测试数据")
                textStyle {
                    color(Color.parseColor("#88000000"))
                    textBorderType(1,2,3,4,5)
                }
            }
        })
    }

    override fun initContent(savedInstanceState: Bundle?) {
    }

    override fun initContentByLazy() {
    }
}