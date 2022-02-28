package com.chooongg.statusLayout.status

import android.view.View

class SuccessStatus : AbstractStatus() {

    override fun onBuildView() = View(context)

    override fun onAttach(view: View, message: CharSequence?) = Unit

    override fun onChangeMessage(view: View, message: CharSequence?) = Unit

    override fun getReloadEventView(view: View): View? = null

    override fun onDetach(view: View) = Unit
}