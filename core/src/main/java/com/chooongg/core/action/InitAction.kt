package com.chooongg.core.action

import android.os.Bundle

interface InitAction {

    fun initConfig(savedInstanceState: Bundle?)

    fun initContent(savedInstanceState: Bundle?)

    fun initContentByLazy()

}