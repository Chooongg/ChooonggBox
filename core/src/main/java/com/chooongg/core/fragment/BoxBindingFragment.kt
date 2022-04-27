package com.chooongg.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.chooongg.BoxException
import java.lang.reflect.ParameterizedType

abstract class BoxBindingFragment<BINDING : ViewBinding> : BoxFragment() {

    private var bindingInflater: LayoutInflater? = null
    private var bindingContainer: ViewGroup? = null

    @Suppress("UNCHECKED_CAST")
    protected val binding by lazy {
        if (bindingInflater == null) throw BoxException("Please use binding after created")
        var clazz: Class<*> = javaClass
        while (clazz.superclass != null) {
            val generic = clazz.genericSuperclass
            if (generic is ParameterizedType) {
                val type = generic.actualTypeArguments[0] as Class<*>
                if (ViewBinding::class.java.isAssignableFrom(type)) {
                    val method = type.getMethod(
                        "inflate",
                        LayoutInflater::class.java,
                        ViewGroup::class.java,
                        Boolean::class.java
                    )
                    return@lazy method.invoke(
                        null, bindingInflater, bindingContainer, false
                    ) as BINDING
                }
            }
            clazz = clazz.superclass
        }
        throw NullPointerException("Not found binding class")
    }

    override fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingInflater = inflater
        bindingContainer = container
        return binding.root
    }
}