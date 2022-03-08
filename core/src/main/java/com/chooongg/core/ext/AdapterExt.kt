package com.chooongg.core.ext

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chooongg.ext.multipleValid

fun BaseQuickAdapter<*, *>.doOnItemClick(
    block: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit
) = setOnItemClickListener { adapter, view, position ->
    if (multipleValid()) block.invoke(adapter, view, position)
}

fun BaseQuickAdapter<*, *>.doOnItemLongClick(
    block: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Boolean
) = setOnItemLongClickListener { adapter, view, position ->
    if (multipleValid()) block.invoke(adapter, view, position)
    else false
}

fun BaseQuickAdapter<*, *>.doOnItemChildClick(
    block: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit
) = setOnItemChildClickListener { adapter, view, position ->
    if (multipleValid()) block.invoke(adapter, view, position)
}

fun BaseQuickAdapter<*, *>.doOnItemChildLongClick(
    block: (adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Boolean
) = setOnItemChildLongClickListener { adapter, view, position ->
    if (multipleValid()) block.invoke(adapter, view, position)
    else false
}