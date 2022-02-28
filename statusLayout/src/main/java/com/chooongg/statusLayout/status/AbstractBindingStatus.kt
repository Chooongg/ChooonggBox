package com.chooongg.statusLayout.status

import android.view.LayoutInflater
import android.view.View
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

/**
 * 继承此类必须保留空参构造方法
 */
abstract class AbstractBindingStatus<BINDING : ViewBinding> :
    AbstractStatus() {

    private lateinit var binding: BINDING

    @Suppress("UNCHECKED_CAST")
    override fun onBuildView(): View {
        val type = javaClass.genericSuperclass
        val clazz = (type as ParameterizedType).actualTypeArguments[0] as Class<*>
        val method = clazz.getMethod("inflate", LayoutInflater::class.java)
        binding = method.invoke(null, LayoutInflater.from(context)) as BINDING
        return binding.root
    }

    abstract fun onAttach(binding: BINDING, message: CharSequence?)
    abstract fun onChangeMessage(binding: BINDING, message: CharSequence?)
    abstract fun getReloadEventView(binding: BINDING): View?
    abstract fun onDetach(binding: BINDING)

    override fun onAttach(view: View, message: CharSequence?) =
        onAttach(binding, message)

    override fun onChangeMessage(view: View, message: CharSequence?) =
        onChangeMessage(binding, message)

    override fun getReloadEventView(view: View): View? =
        getReloadEventView(binding)

    override fun onDetach(view: View) =
        onDetach(binding)
}