package com.chooongg.core.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding

abstract class BoxBindingActivity<BINDING : ViewBinding> : BoxActivity() {

    protected abstract fun initBinding(): BINDING

    private var isCreated = false

    private var mBinding: BINDING? = null

    protected val binding: BINDING get() = mBinding!!

//    @Suppress("UNCHECKED_CAST")
//    protected val binding by lazy {
//        if (!isCreated) throw BoxException("Please use binding after created")
//        var clazz: Class<*> = javaClass
//        while (clazz.superclass != null) {
//            val generic = clazz.genericSuperclass
//            if (generic is ParameterizedType) {
//                val type = generic.actualTypeArguments[0] as Class<*>
//                if (ViewBinding::class.java.isAssignableFrom(type)) {
//                    val method = type.getMethod("inflate", LayoutInflater::class.java)
//                    return@lazy method.invoke(null, layoutInflater) as BINDING
//                }
//            }
//            clazz = clazz.superclass
//        }
//        throw NullPointerException("Not found binding class")
//    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        isCreated = true
        mBinding = initBinding()
        setContentView(mBinding!!.root)
    }
}