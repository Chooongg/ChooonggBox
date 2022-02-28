package com.chooongg.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chooongg.BoxException
import com.chooongg.ext.getTClass

abstract class BoxBindingFragment<BINDING : ViewBinding> : BoxFragment() {

    private var bindingInflater: LayoutInflater? = null
    private var bindingContainer: ViewGroup? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding by lazy {
        if (bindingInflater == null) throw BoxException("Please use binding after created")
        val method = javaClass.getTClass().getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        method.invoke(null, bindingInflater, bindingContainer, false) as BINDING
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingInflater = inflater
        bindingContainer = container
        return binding.root
    }
}