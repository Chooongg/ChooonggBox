package com.chooongg.core.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.chooongg.BoxException
import com.chooongg.ext.getTClass
import java.lang.reflect.ParameterizedType

abstract class BoxBindingActivity<BINDING : ViewBinding> : BoxActivity() {

    private var isCreated = false

    @Suppress("UNCHECKED_CAST")
    protected val binding by lazy {
        if (!isCreated) throw BoxException("Please use binding after created")
        var clazz: Class<*> = javaClass
        while (clazz.superclass != null) {
            val generic = clazz.genericSuperclass
            if (generic is ParameterizedType) {
                val type = generic.actualTypeArguments[0] as Class<*>
                if (ViewBinding::class.java.isAssignableFrom(type)) {
                    val method = type.getMethod("inflate", LayoutInflater::class.java)
                    return@lazy method.invoke(null, layoutInflater) as BINDING
                }
            }
            clazz = clazz.superclass
        }
        throw NullPointerException("Not found binding class")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCreated = true
        setContentView(binding.root)
    }
}