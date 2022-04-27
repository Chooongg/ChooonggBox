package com.chooongg.statusLayout.status

import android.graphics.Color
import android.view.View
import com.chooongg.ext.dp2px
import com.chooongg.ext.gone
import com.google.android.material.progressindicator.CircularProgressIndicator

class ProgressStatus : AbstractStatus() {

    override fun onBuildView() = CircularProgressIndicator(context).apply {
        gone()
        isIndeterminate = true
        indicatorSize = dp2px(56f)
        trackCornerRadius = dp2px(16f)
        hide()
        setBackgroundColor(Color.RED)
    }

    override fun onAttach(view: View, message: CharSequence?) {
        (view as CircularProgressIndicator).show()
    }

    override fun onChangeMessage(view: View, message: CharSequence?) = Unit

    override fun getReloadEventView(view: View): View? = null

    override fun onDetach(view: View) {
    }
}