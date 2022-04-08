package com.chooongg.abc.main.fragment

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.widget.PopupMenu
import com.chooongg.abc.databinding.FragmentColorBinding
import com.chooongg.abc.main.MainActivity
import com.chooongg.core.annotation.Title
import com.chooongg.core.annotation.TopAppBar
import com.chooongg.core.ext.startActivityTransitionPage
import com.chooongg.core.fragment.BoxBindingFragment
import com.chooongg.ext.doOnClick
import com.chooongg.ext.showToast
import com.github.zawadz88.materialpopupmenu.popupMenu

@Title("Color")
@TopAppBar(TopAppBar.TYPE_SMALL)
class ColorFragment : BoxBindingFragment<FragmentColorBinding>() {
    override fun initConfig(savedInstanceState: Bundle?) {
        binding.test.doOnClick {
//            startActivityTransitionPage(MainActivity::class, it)
            popupMenu {
                this.fixedContentWidthInPx = it.width
                this.dropdownGravity = Gravity.START
                section {
//                    title = "测试标签"
                    item {
                        label = "测试"
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