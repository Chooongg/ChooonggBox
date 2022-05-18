package com.chooongg.core.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding

abstract class BoxBindingActivity<BINDING : ViewBinding> : BoxActivity() {

    protected abstract fun initBinding(): BINDING

    private var isCreated = false

    protected val binding: BINDING by lazy {
        if (!isCreated) throw NullPointerException("you should use after onCreate()!")
        initBinding()
    }

    override fun initView(inflater: LayoutInflater, container: ViewGroup?) = binding.root

    override fun onCreate(savedInstanceState: Bundle?) {
        isCreated = true
        super.onCreate(savedInstanceState)
    }
}