package com.chooongg.abc.main.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import com.chooongg.abc.R
import com.chooongg.abc.Test
import com.chooongg.abc.databinding.FragmentColorBinding
import com.chooongg.core.annotation.Title
import com.chooongg.core.channel.receiveEvent
import com.chooongg.core.channel.sendEvent
import com.chooongg.core.fragment.BoxBindingFragment
import com.chooongg.ext.doOnClick
import com.chooongg.ext.setNightMode
import com.chooongg.ext.showToast

@Title("Color")
class ColorFragment : BoxBindingFragment<FragmentColorBinding>() {

    override fun initBinding(inflater: LayoutInflater, container: ViewGroup?) =
        FragmentColorBinding.inflate(inflater, container, false)

    override fun initConfig(savedInstanceState: Bundle?) {
        toolbar?.apply {
            inflateMenu(R.menu.night_mode)
            setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.light -> {
                        setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                        true
                    }
                    R.id.dark -> {
                        setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                        true
                    }
                    R.id.system -> {
                        setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    override fun initContent(savedInstanceState: Bundle?) {
        receiveEvent<Test> { showToast("测试") }
        binding.btnPrimary.doOnClick {
            sendEvent(Test())
        }
    }

    override fun initContentByLazy() {
    }
}