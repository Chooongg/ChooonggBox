package com.chooongg.abc.main.fragment

import android.os.Bundle
import android.view.Gravity
import com.chooongg.abc.databinding.FragmentColorBinding
import com.chooongg.core.annotation.Title
import com.chooongg.core.annotation.TopAppBar
import com.chooongg.core.ext.getMaterialColor
import com.chooongg.core.fragment.BoxBindingFragment
import com.chooongg.core.popupMenu.popupMenu
import com.chooongg.ext.doOnClick
import com.chooongg.ext.style

@Title("Color")
@TopAppBar(TopAppBar.TYPE_SMALL)
class ColorFragment : BoxBindingFragment<FragmentColorBinding>() {
    override fun initConfig(savedInstanceState: Bundle?) {
        binding.test.doOnClick {
//            startActivityTransitionPage(MainActivity::class, it)
            popupMenu {
                this.dropdownGravity = Gravity.END
                dropDownVerticalOffset = -it.height / 2
                section {
//                    title = "测试标签"
                    item {
                        label = "测试".style {
                            setForegroundColor(binding.test.getMaterialColor(com.google.android.material.R.attr.colorPrimary))
                        }.build()
                    }
                    item {
                        label = "学习"
                    }
                }
            }.show(requireContext(), it)
        }
    }

    override fun initContent(savedInstanceState: Bundle?) {
    }

    override fun initContentByLazy() {
    }
}