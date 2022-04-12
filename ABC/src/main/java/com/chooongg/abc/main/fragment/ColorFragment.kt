package com.chooongg.abc.main.fragment

import android.os.Bundle
import android.view.Gravity
import androidx.appcompat.widget.PopupMenu
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
    }

    override fun initContent(savedInstanceState: Bundle?) {
    }

    override fun initContentByLazy() {
    }
}