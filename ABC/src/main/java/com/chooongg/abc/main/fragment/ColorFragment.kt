package com.chooongg.abc.main.fragment

import android.os.Bundle
import com.chooongg.abc.databinding.FragmentColorBinding
import com.chooongg.abc.main.MainActivity
import com.chooongg.core.activity.BoxBindingActivity
import com.chooongg.core.annotation.Title
import com.chooongg.core.annotation.TopAppBar
import com.chooongg.core.ext.startActivityTransitionPage
import com.chooongg.ext.doOnClick

@Title("Color")
@TopAppBar(TopAppBar.TYPE_SMALL)
class ColorFragment : BoxBindingActivity<FragmentColorBinding>() {
    override fun initConfig(savedInstanceState: Bundle?) {
        binding.test.doOnClick {
            startActivityTransitionPage(MainActivity::class, it)
        }
    }

    override fun initContent(savedInstanceState: Bundle?) {
    }

    override fun initContentByLazy() {
    }
}