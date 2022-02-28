package com.chooongg.core.adapter

import android.util.SparseArray
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlin.reflect.KClass

abstract class BindingMultiItemAdapter<T : MultiItemEntity> :
    BaseMultiItemQuickAdapter<T, BindingHolder<*>>() {

    private val bindings: SparseArray<KClass<out ViewBinding>> by lazy(LazyThreadSafetyMode.NONE) { SparseArray() }

    protected fun addItemType(type: Int, bindingClass: KClass<out ViewBinding>) {
        bindings.put(type, bindingClass)
    }

    protected abstract fun convert(holder: BaseViewHolder, binding: ViewBinding, item: T)

    protected open fun convert(
        holder: BaseViewHolder,
        binding: ViewBinding,
        item: T,
        payloads: List<Any>
    ) = Unit

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): BindingHolder<*> {
        val binding = bindings.get(viewType)
        val method = binding.java.getMethod(
            "inflate",
            LayoutInflater::class.java,
            ViewGroup::class.java,
            Boolean::class.java
        )
        return BindingHolder(
            method.invoke(null, LayoutInflater.from(context), parent, false) as ViewBinding
        )
    }

    @Deprecated("弃用", ReplaceWith("convert(binding, holder, item)"))
    override fun convert(holder: BindingHolder<*>, item: T) {
        convert(holder, holder.binding, item)
    }

    @Deprecated("弃用", ReplaceWith("convert(binding, holder, item, payloads)"))
    override fun convert(holder: BindingHolder<*>, item: T, payloads: List<Any>) {
        convert(holder, holder.binding, item, payloads)
    }
}