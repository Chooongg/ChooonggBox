package com.chooongg.core.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chooongg.ext.getTClass

abstract class BindingAdapter<T, BINDING : ViewBinding> :
    BaseQuickAdapter<T, BindingHolder<BINDING>>(ResourcesCompat.ID_NULL) {

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

    abstract fun convert(holder: BaseViewHolder, binding: BINDING, item: T)

    open fun convert(holder: BaseViewHolder, binding: BINDING, item: T, payloads: List<Any>) = Unit

    @Deprecated("弃用", ReplaceWith("convert(binding, holder, item)"))
    override fun convert(holder: BindingHolder<BINDING>, item: T) {
        convert(holder, holder.binding, item)
    }

    @Deprecated("弃用", ReplaceWith("convert(binding, holder, item, payloads)"))
    override fun convert(holder: BindingHolder<BINDING>, item: T, payloads: List<Any>) {
        convert(holder, holder.binding, item, payloads)
    }
}