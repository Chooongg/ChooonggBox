package com.chooongg.abc.main.fragment

import android.os.Bundle
import com.chooongg.abc.databinding.FragmentHomeBinding
import com.chooongg.core.annotation.TopAppBar
import com.chooongg.core.fragment.BoxBindingFragment

@TopAppBar(TopAppBar.TYPE_SMALL)
class HomeFragment : BoxBindingFragment<FragmentHomeBinding>() {
    override fun initConfig(savedInstanceState: Bundle?) {
    }

    override fun initContent(savedInstanceState: Bundle?) {
    }

    override fun initContentByLazy() {
    }
}