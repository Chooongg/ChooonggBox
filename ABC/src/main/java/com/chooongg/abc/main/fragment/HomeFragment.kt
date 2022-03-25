package com.chooongg.abc.main.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chooongg.abc.databinding.FragmentHomeBinding
import com.chooongg.abc.databinding.ItemHomeBinding
import com.chooongg.abc.modules.StatusLayoutActivity
import com.chooongg.abc.modules.TopAppbarActivity
import com.chooongg.core.adapter.BindingAdapter
import com.chooongg.core.annotation.TopAppBar
import com.chooongg.core.ext.divider
import com.chooongg.core.ext.doOnItemChildClick
import com.chooongg.core.ext.doOnItemClick
import com.chooongg.core.ext.startActivityTransitionPage
import com.chooongg.core.fragment.BoxBindingFragment
import com.chooongg.ext.attrColor
import com.chooongg.ext.dp2px
import com.chooongg.ext.resourcesDimensionPixelSize

@TopAppBar(TopAppBar.TYPE_SMALL)
class HomeFragment : BoxBindingFragment<FragmentHomeBinding>(), OnItemClickListener {

    private val adapter = Adapter()

    override fun initConfig(savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.divider {
            color(attrColor(com.google.android.material.R.attr.colorOutline, Color.GRAY))
            size(resourcesDimensionPixelSize(com.chooongg.R.dimen.divider))
            insets(dp2px(16f), dp2px(16f))
        }
        adapter.doOnItemClick(this)
    }

    override fun initContent(savedInstanceState: Bundle?) {
        adapter.setNewInstance(
            arrayListOf(
                "TopAppBar",
                "StatusLayout",
                "EChartsView"
            )
        )
    }

    override fun initContentByLazy() {
    }

    override fun onItemClick(a: BaseQuickAdapter<*, *>, view: View, position: Int) {
        when (adapter.data[position]) {
            "TopAppBar" -> startActivityTransitionPage(TopAppbarActivity::class, view)
            "StatusLayout" -> startActivityTransitionPage(StatusLayoutActivity::class,view)
        }
    }

    private class Adapter : BindingAdapter<String, ItemHomeBinding>() {
        override fun convert(holder: BaseViewHolder, binding: ItemHomeBinding, item: String) {
            binding.tvTitle.text = item
        }
    }
}