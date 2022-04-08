package com.chooongg.abc.main.fragment

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
import com.chooongg.core.ext.doOnItemClick
import com.chooongg.core.ext.startActivity
import com.chooongg.core.ext.startActivityTransitionPage
import com.chooongg.core.fragment.BoxBindingFragment
import com.chooongg.ext.resourcesDimensionPixelSize
import com.chooongg.ext.showToast

@TopAppBar(TopAppBar.TYPE_SMALL)
class HomeFragment : BoxBindingFragment<FragmentHomeBinding>(), OnItemClickListener {

    private val adapter = Adapter()

    override fun initConfig(savedInstanceState: Bundle?) {
        binding.recyclerView.adapter = adapter
        binding.recyclerView.divider {
            asSpace().showFirstDivider().showLastDivider().showSideDividers()
            size(resourcesDimensionPixelSize(com.chooongg.R.dimen.contentMedium))
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
            "TopAppBar" -> startActivity(TopAppbarActivity::class)
            "StatusLayout" -> startActivityTransitionPage(StatusLayoutActivity::class, view)
        }
    }

    override fun onReselected() {
        super.onReselected()
        showToast("onReselected ColorFragment")
    }

    private class Adapter : BindingAdapter<String, ItemHomeBinding>() {
        override fun convert(holder: BaseViewHolder, binding: ItemHomeBinding, item: String) {
            binding.root.text = item
        }
    }
}