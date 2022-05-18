package com.chooongg.core.fragment

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BoxBindingFragment<BINDING : ViewBinding> : BoxFragment() {

    abstract fun initBinding(inflater: LayoutInflater, container: ViewGroup?): BINDING

    override fun initView(inflater: LayoutInflater, container: ViewGroup?): View {
        binding = initBinding(inflater, container)
        return binding.root
    }

    private var bindingInflater: LayoutInflater? = null
    private var bindingContainer: ViewGroup? = null

    protected lateinit var binding: BINDING
        private set
}