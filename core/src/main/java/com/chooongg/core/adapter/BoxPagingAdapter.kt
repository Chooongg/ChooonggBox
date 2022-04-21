package com.chooongg.core.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.util.getItemView
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import java.lang.reflect.Constructor
import java.lang.reflect.InvocationTargetException
import java.lang.reflect.Modifier
import java.lang.reflect.ParameterizedType

abstract class BoxPagingAdapter<T : Any, VH : BaseViewHolder>(
    @LayoutRes private val layoutResId: Int,
    diffCallback: DiffUtil.ItemCallback<T>,
    mainDispatcher: CoroutineDispatcher = Dispatchers.Main,
    workerDispatcher: CoroutineDispatcher = Dispatchers.Default
) : PagingDataAdapter<T, VH>(diffCallback, mainDispatcher, workerDispatcher) {

    var recyclerViewOrNull: RecyclerView? = null
        private set

    val recyclerView: RecyclerView
        get() {
            checkNotNull(recyclerViewOrNull) {
                "Please get it after onAttachedToRecyclerView()"
            }
            return recyclerViewOrNull!!
        }

    val context: Context
        get() {
            return recyclerView.context
        }

    private var mOnItemClickListener: OnItemClickListener? = null
    private var mOnItemLongClickListener: OnItemLongClickListener? = null
    private var mOnItemChildClickListener: OnItemChildClickListener? = null
    private var mOnItemChildLongClickListener: OnItemChildLongClickListener? = null

    protected abstract fun convert(holder: VH, item: T?)

    protected open fun convert(holder: VH, item: T?, payloads: List<Any>) {
        convert(holder, item)
    }

    final override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val viewHolder = onCreateDefViewHolder(parent, viewType)
        bindViewClickListener(viewHolder, viewType)
        return viewHolder
    }

    protected open fun onCreateDefViewHolder(parent: ViewGroup, viewType: Int): VH {
        return createBaseViewHolder(parent.getItemView(layoutResId))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        convert(holder, getItem(position))
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        recyclerViewOrNull = recyclerView
    }

    override fun onDetachedFromRecyclerView(recyclerView: RecyclerView) {
        super.onDetachedFromRecyclerView(recyclerView)
        recyclerViewOrNull = null
    }

    override fun onBindViewHolder(holder: VH, position: Int, payloads: MutableList<Any>) {
        convert(holder, getItem(position), payloads)
    }

    private val childClickViewIds = LinkedHashSet<Int>()

    fun getChildClickViewIds(): LinkedHashSet<Int> {
        return childClickViewIds
    }

    /**
     * 设置需要点击事件的子view
     * @param viewIds IntArray
     */
    fun addChildClickViewIds(@IdRes vararg viewIds: Int) {
        for (viewId in viewIds) {
            childClickViewIds.add(viewId)
        }
    }

    /**
     * 用于保存需要设置长按点击事件的 item
     */
    private val childLongClickViewIds = LinkedHashSet<Int>()

    fun getChildLongClickViewIds(): LinkedHashSet<Int> {
        return childLongClickViewIds
    }

    /**
     * 设置需要长按点击事件的子view
     * @param viewIds IntArray
     */
    fun addChildLongClickViewIds(@IdRes vararg viewIds: Int) {
        for (viewId in viewIds) {
            childLongClickViewIds.add(viewId)
        }
    }

    protected open fun setOnItemClick(v: View, position: Int) {
        mOnItemClickListener?.onItemClick(this, v, position)
    }

    /**
     * override this method if you want to override longClick event logic
     *
     * 如果你想重新实现 item 长按事件逻辑，请重写此方法
     * @param v
     * @param position
     * @return
     */
    protected open fun setOnItemLongClick(v: View, position: Int): Boolean {
        return mOnItemLongClickListener?.onItemLongClick(this, v, position) ?: false
    }

    protected open fun setOnItemChildClick(v: View, position: Int) {
        mOnItemChildClickListener?.onItemChildClick(this, v, position)
    }

    protected open fun setOnItemChildLongClick(v: View, position: Int): Boolean {
        return mOnItemChildLongClickListener?.onItemChildLongClick(this, v, position) ?: false
    }

    /**
     * 绑定 item 点击事件
     * @param viewHolder VH
     */
    protected open fun bindViewClickListener(viewHolder: VH, viewType: Int) {
        mOnItemClickListener?.let {
            viewHolder.itemView.setOnClickListener { v ->
                val position = viewHolder.bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) {
                    return@setOnClickListener
                }
                setOnItemClick(v, position)
            }
        }
        mOnItemLongClickListener?.let {
            viewHolder.itemView.setOnLongClickListener { v ->
                val position = viewHolder.bindingAdapterPosition
                if (position == RecyclerView.NO_POSITION) {
                    return@setOnLongClickListener false
                }
                setOnItemLongClick(v, position)
            }
        }

        mOnItemChildClickListener?.let {
            for (id in getChildClickViewIds()) {
                viewHolder.itemView.findViewById<View>(id)?.let { childView ->
                    if (!childView.isClickable) {
                        childView.isClickable = true
                    }
                    childView.setOnClickListener { v ->
                        val position = viewHolder.bindingAdapterPosition
                        if (position == RecyclerView.NO_POSITION) {
                            return@setOnClickListener
                        }
                        setOnItemChildClick(v, position)
                    }
                }
            }
        }
        mOnItemChildLongClickListener?.let {
            for (id in getChildLongClickViewIds()) {
                viewHolder.itemView.findViewById<View>(id)?.let { childView ->
                    if (!childView.isLongClickable) {
                        childView.isLongClickable = true
                    }
                    childView.setOnLongClickListener { v ->
                        val position = viewHolder.bindingAdapterPosition
                        if (position == RecyclerView.NO_POSITION) {
                            return@setOnLongClickListener false
                        }
                        setOnItemChildLongClick(v, position)
                    }
                }
            }
        }
    }

    protected open fun createBaseViewHolder(parent: ViewGroup, @LayoutRes layoutResId: Int): VH {
        return createBaseViewHolder(parent.getItemView(layoutResId))
    }

    @Suppress("UNCHECKED_CAST")
    protected open fun createBaseViewHolder(view: View): VH {
        var temp: Class<*>? = javaClass
        var z: Class<*>? = null
        while (z == null && null != temp) {
            z = getInstancedGenericKClass(temp)
            temp = temp.superclass
        }
        // 泛型擦除会导致z为null
        val vh: VH? = if (z == null) {
            BaseViewHolder(view) as VH
        } else {
            createBaseGenericKInstance(z, view)
        }
        return vh ?: BaseViewHolder(view) as VH
    }

    private fun getInstancedGenericKClass(z: Class<*>): Class<*>? {
        try {
            val type = z.genericSuperclass
            if (type is ParameterizedType) {
                val types = type.actualTypeArguments
                for (temp in types) {
                    if (temp is Class<*>) {
                        if (BaseViewHolder::class.java.isAssignableFrom(temp)) {
                            return temp
                        }
                    } else if (temp is ParameterizedType) {
                        val rawType = temp.rawType
                        if (rawType is Class<*> && BaseViewHolder::class.java.isAssignableFrom(
                                rawType
                            )
                        ) {
                            return rawType
                        }
                    }
                }
            }
        } catch (e: java.lang.reflect.GenericSignatureFormatError) {
            e.printStackTrace()
        } catch (e: TypeNotPresentException) {
            e.printStackTrace()
        } catch (e: java.lang.reflect.MalformedParameterizedTypeException) {
            e.printStackTrace()
        }
        return null
    }

    @Suppress("UNCHECKED_CAST")
    private fun createBaseGenericKInstance(z: Class<*>, view: View): VH? {
        try {
            val constructor: Constructor<*>
            // inner and unstatic class
            return if (z.isMemberClass && !Modifier.isStatic(z.modifiers)) {
                constructor = z.getDeclaredConstructor(javaClass, View::class.java)
                constructor.isAccessible = true
                constructor.newInstance(this, view) as VH
            } else {
                constructor = z.getDeclaredConstructor(View::class.java)
                constructor.isAccessible = true
                constructor.newInstance(view) as VH
            }
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

        return null
    }
}