package com.chooongg.abc.modules

import android.os.Bundle
import com.chooongg.abc.databinding.ActivityTopAppbarBinding
import com.chooongg.core.activity.BoxBindingActivity
import com.chooongg.core.annotation.TopAppBar

@TopAppBar(TopAppBar.TYPE_NONE)
class TopAppbarActivity : BoxBindingActivity<ActivityTopAppbarBinding>() {
    override fun initConfig(savedInstanceState: Bundle?) {
    }

    override fun initContent(savedInstanceState: Bundle?) {
    }
}