package com.chooongg.core.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.chooongg.BoxException
import com.chooongg.ext.getTClass

abstract class BoxBindingActivity<BINDING : ViewBinding> : BoxActivity() {

    protected var isCreated = false

    @Suppress("UNCHECKED_CAST")
    protected val binding by lazy {
        if (!isCreated) throw BoxException("Please use binding after created")
        val method = javaClass.getTClass().getMethod("inflate", LayoutInflater::class.java)
        method.invoke(null, layoutInflater) as BINDING
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCreated = true
        setContentView(binding.root)
    }
}