package com.chooongg.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chooongg.ext.getTClass
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class BindingPagingAdapter<T : Any, BINDING : ViewBinding>(
    diffCallback: DiffUtil.ItemCallback<T>,
    mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    workerDispatcher: CoroutineDispatcher = Dispatchers.Default
) : BoxPagingAdapter<T, BindingHolder<BINDING>>(0, diffCallback, mainDispatcher, workerDispatcher) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<BINDING> {
        val clazz = javaClass.getTClass(1)
        val method = clazz.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        val binding =
            method.invoke(null, LayoutInflater.from(context), parent, false) as BINDING
        return BindingHolder(binding)
    }

    abstract fun convert(holder: BaseViewHolder, binding: BINDING, item: T?)

    open fun convert(holder: BaseViewHolder, binding: BINDING, item: T?, payloads: List<Any>) = Unit

    @Deprecated("弃用", ReplaceWith("convert(binding, holder, item)"))
    override fun convert(holder: BindingHolder<BINDING>, item: T?) {
        convert(holder, holder.binding, item)
    }

    @Deprecated("弃用", ReplaceWith("convert(binding, holder, item, payloads)"))
    override fun convert(holder: BindingHolder<BINDING>, item: T?, payloads: List<Any>) {
        convert(holder, holder.binding, item, payloads)
    }
}