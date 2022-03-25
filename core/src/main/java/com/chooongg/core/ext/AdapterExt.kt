package com.chooongg.core.ext

import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.chad.library.adapter.base.listener.OnItemChildLongClickListener
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.chad.library.adapter.base.listener.OnItemLongClickListener
import com.chooongg.ext.multipleValid

fun BaseQuickAdapter<*, *>.doOnItemClick(
    block: ((adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit)?
) = setOnItemClickListener { adapter, view, position ->
    if (multipleValid()) block?.invoke(adapter, view, position)
}

fun BaseQuickAdapter<*, *>.doOnItemClick(
    listener: OnItemClickListener?
) = setOnItemClickListener { adapter, view, position ->
    if (multipleValid()) listener?.onItemClick(adapter, view, position)
}


fun BaseQuickAdapter<*, *>.doOnItemLongClick(
    block: ((adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Boolean)?
) = setOnItemLongClickListener { adapter, view, position ->
    if (multipleValid()) block?.invoke(adapter, view, position) ?: false
    else false
}

fun BaseQuickAdapter<*, *>.doOnItemLongClick(
    listener: OnItemLongClickListener?
) = setOnItemLongClickListener { adapter, view, position ->
    if (multipleValid()) listener?.onItemLongClick(adapter, view, position) ?: false
    else false
}


fun BaseQuickAdapter<*, *>.doOnItemChildClick(
    block: ((adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Unit)?
) = setOnItemChildClickListener { adapter, view, position ->
    if (multipleValid()) block?.invoke(adapter, view, position)
}

fun BaseQuickAdapter<*, *>.doOnItemChildClick(
    listener: OnItemChildClickListener?
) = setOnItemChildClickListener { adapter, view, position ->
    if (multipleValid()) listener?.onItemChildClick(adapter, view, position)
}


fun BaseQuickAdapter<*, *>.doOnItemChildLongClick(
    block: ((adapter: BaseQuickAdapter<*, *>, view: View, position: Int) -> Boolean)?
) = setOnItemChildLongClickListener { adapter, view, position ->
    if (multipleValid()) block?.invoke(adapter, view, position) ?: false
    else false
}

fun BaseQuickAdapter<*, *>.doOnItemChildLongClick(
    listener: OnItemChildLongClickListener?
) = setOnItemChildLongClickListener { adapter, view, position ->
    if (multipleValid()) listener?.onItemChildLongClick(adapter, view, position) ?: false
    else false
}