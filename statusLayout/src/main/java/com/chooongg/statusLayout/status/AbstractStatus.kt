package com.chooongg.statusLayout.status

import android.content.Context
import android.view.View
import com.chooongg.statusLayout.StatusLayout

/**
 * 继承此类必须保留单Context构造方法
 */
abstract class AbstractStatus {

    internal lateinit var targetView: View

    protected lateinit var context: Context

    internal fun obtainTargetView(context: Context) {
        this.context = context
        this.targetView = onBuildView()
        targetView.tag = StatusLayout.STATUS_ITEM_TAG
    }

    protected abstract fun onBuildView(): View

    abstract fun onAttach(view: View, message: CharSequence?)

    abstract fun onChangeMessage(view: View, message: CharSequence?)

    open fun getReloadEventView(view: View): View? = targetView

    abstract fun onDetach(view: View)

    open fun isShowSuccess(): Boolean = false
}