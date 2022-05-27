package com.chooongg.core.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.chooongg.core.R
import com.chooongg.core.action.InitAction
import com.chooongg.core.annotation.AutoHideIME
import com.chooongg.core.annotation.Title
import com.chooongg.core.annotation.TitleRes
import com.chooongg.ext.hideIME
import com.chooongg.ext.logDTag
import com.chooongg.ext.resourcesString
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

abstract class BoxFragment : Fragment(), InitAction {

    var isLoaded = false; private set

    val isShowing get() = !isHidden && isResumed

    var title: CharSequence? = null
        get() = field ?: getTitle4Annotation()

    protected var toolbar: Toolbar? = null
        private set

    @IdRes
    open fun getLiftOnScrollTargetId(): Int? = null

    open fun onReselected() = Unit

    open fun onRefresh(any: Any? = null) = Unit

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logDTag("BOX --> Fragment", "${javaClass.simpleName}(${title}) onCreateView")
        return if (initViewRes() != View.NO_ID) inflater.inflate(initViewRes(), container, false)
        else initView(layoutInflater, container)
            ?: super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        logDTag("BOX --> Fragment", "${javaClass.simpleName}(${title}) onViewCreated")
        super.onViewCreated(view, savedInstanceState)
        if (isAutoHideIME4Annotation()) view.setOnClickListener {
            it.clearFocus()
            hideIME()
        }
        initConfig(savedInstanceState)
        initContent(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        if (!isLoaded && !isHidden) {
            isLoaded = true
            initContentByLazy()
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!isLoaded && !isHidden) {
            isLoaded = true
            initContentByLazy()
        }
    }

    protected fun getCoordinatorLayout(): CoordinatorLayout? =
        view?.findViewById(R.id.coordinator_layout)

    protected fun getAppBarLayout(): AppBarLayout? =
        view?.findViewById(R.id.appbar_layout)

    protected fun getCollapsingToolbarLayout(): CollapsingToolbarLayout? =
        view?.findViewById(R.id.collapsing_toolbar_layout)

    private fun getTitle4Annotation() = when {
        javaClass.isAnnotationPresent(TitleRes::class.java) ->
            resourcesString(javaClass.getAnnotation(TitleRes::class.java)!!.value)
        javaClass.isAnnotationPresent(Title::class.java) ->
            javaClass.getAnnotation(Title::class.java)!!.value
        activity != null && requireActivity().title != null ->
            requireActivity().title
        else -> null
    }

    private fun isAutoHideIME4Annotation() =
        javaClass.getAnnotation(AutoHideIME::class.java)?.isEnable ?: true

    open fun onBackPressed() = false

    override fun onDestroyView() {
        super.onDestroyView()
        logDTag("BOX --> Fragment", "${javaClass.simpleName}(${title}) onDestroyView")
        isLoaded = false
    }
}