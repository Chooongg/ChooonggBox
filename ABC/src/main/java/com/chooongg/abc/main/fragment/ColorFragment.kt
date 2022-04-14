package com.chooongg.abc.main.fragment

import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import com.chooongg.abc.R
import com.chooongg.abc.databinding.FragmentColorBinding
import com.chooongg.core.annotation.Title
import com.chooongg.core.annotation.TopAppBar
import com.chooongg.core.fragment.BoxBindingFragment
import com.chooongg.ext.setNightMode

@Title("Color")
@TopAppBar(TopAppBar.TYPE_SMALL)
class ColorFragment : BoxBindingFragment<FragmentColorBinding>() {
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
    }

    override fun initContentByLazy() {
    }
}