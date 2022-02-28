package com.chooongg.core.adapter

import androidx.viewbinding.ViewBinding
import com.chad.library.adapter.base.entity.SectionEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chooongg.ext.getTClass
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
abstract class BindingSectionAdapter<T : SectionEntity, HEADER : ViewBinding, BINDING : ViewBinding> :
    BindingMultiItemAdapter<T>() {

    init {
        addItemType(
            SectionEntity.HEADER_TYPE,
            this.javaClass.getTClass(1).kotlin as KClass<out ViewBinding>
        )
        addItemType(
            SectionEntity.NORMAL_TYPE,
            this.javaClass.getTClass(2).kotlin as KClass<out ViewBinding>
        )
    }

    override fun isFixedViewType(type: Int): Boolean {
        return super.isFixedViewType(type) || type == SectionEntity.HEADER_TYPE
    }

    protected abstract fun convertHeader(helper: BaseViewHolder, binding: ViewBinding, item: T)

    /**
     * 重写此处，设置 Diff Header
     * @param helper VH
     * @param item T?
     * @param payloads MutableList<Any>
     */
    protected open fun convertHeader(
        helper: BaseViewHolder,
        binding: ViewBinding,
        item: T,
        payloads: MutableList<Any>
    ) {
    }

    override fun onBindViewHolder(holder: BindingHolder<*>, position: Int) {
        if (holder.itemViewType == SectionEntity.HEADER_TYPE) {
//            setFullSpan(holder)
            convertHeader(holder, holder.binding, getItem(position - headerLayoutCount))
        } else {
            super.onBindViewHolder(holder, position)
        }
    }

    override fun onBindViewHolder(
        holder: BindingHolder<*>,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }
        if (holder.itemViewType == SectionEntity.HEADER_TYPE) {
            convertHeader(holder, holder.binding, getItem(position - headerLayoutCount), payloads)
        } else {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}