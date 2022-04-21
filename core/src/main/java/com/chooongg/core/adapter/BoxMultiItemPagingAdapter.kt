package com.chooongg.core.adapter

import android.util.SparseIntArray
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DiffUtil
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

abstract class BoxMultiItemPagingAdapter<T : MultiItemEntity, VH : BaseViewHolder>(
    diffCallback: DiffUtil.ItemCallback<T>,
    mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    workerDispatcher: CoroutineDispatcher = Dispatchers.Default
) : BoxPagingAdapter<T, VH>(0, diffCallback, mainDispatcher, workerDispatcher) {

    private val layouts: SparseIntArray by lazy(LazyThreadSafetyMode.NONE) { SparseIntArray() }

    open fun getDefItemViewType(position: Int): Int {
        return getItem(position)?.itemType ?: 0
    }

    final override fun getItemViewType(position: Int): Int {
        return getDefItemViewType(position)
    }

    override fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): VH {
        val layoutResId = layouts.get(viewType)
        require(layoutResId != 0) { "ViewType: $viewType found layoutResId，please use addItemType() first!" }
        return createBaseViewHolder(parent, layoutResId)
    }

    /**
     * 调用此方法，设置多布局
     * @param type Int
     * @param layoutResId Int
     */
    protected fun addItemType(type: Int, @LayoutRes layoutResId: Int) {
        layouts.put(type, layoutResId)
    }

}