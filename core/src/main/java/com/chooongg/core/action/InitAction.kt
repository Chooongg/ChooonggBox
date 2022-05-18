package com.chooongg.core.action

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

interface InitAction {

    @LayoutRes
    fun initViewRes(): Int = View.NO_ID

    fun initView(inflater: LayoutInflater, container: ViewGroup?): View? = null

    fun initConfig(savedInstanceState: Bundle?)

    fun initContent(savedInstanceState: Bundle?)

    fun initContentByLazy()

}