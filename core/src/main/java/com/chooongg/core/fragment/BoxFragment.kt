package com.chooongg.core.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import com.chooongg.core.R
import com.chooongg.core.action.InitAction
import com.chooongg.core.annotation.*
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

    protected open fun initView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return null
    }

    /**
     * 为适配 TopAppbar 布局, 请使用 [initView] 方法初始化 View
     */
    @Deprecated(
        "请使用 initView 方法进行初始化 View 操作",
        ReplaceWith("initView(inflater, container, savedInstanceState)")
    )
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        logDTag("BOX --> Fragment", "${javaClass.simpleName}(${title}) onCreatedView")
        val view = when (getTopAppBar4Annotation()) {
            TopAppBar.TYPE_SMALL ->
                inflater.inflate(R.layout.box_activity_root_small, container, false)
            TopAppBar.TYPE_MEDIUM ->
                inflater.inflate(R.layout.box_activity_root_medium, container, false)
            TopAppBar.TYPE_LARGE ->
                inflater.inflate(R.layout.box_activity_root_large, container, false)
            else -> return initView(inflater, container, savedInstanceState)
        }
        toolbar = view.findViewById(R.id.toolbar)
        toolbar?.apply {
            title = this@BoxFragment.title
            if (isShowTopAppBarDefaultNavigation4Annotation()) {
                setNavigationIcon(R.drawable.ic_app_bar_back)
                setNavigationOnClickListener {
                    if (onBackPressed()) requireActivity().onBackPressed()
                }
            }
        }
        val childView = initView(inflater, container, savedInstanceState)
        if (childView != null) {
            childView.id = com.chooongg.R.id.box_content_view
            (view as CoordinatorLayout).addView(
                childView, CoordinatorLayout.LayoutParams(-1, -1).apply {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                })
        }
        val appBarLayout = view.findViewById<AppBarLayout>(R.id.appbar_layout)
        val liftOnScrollTargetId = getLiftOnScrollTargetId()
        if (liftOnScrollTargetId != null) {
            appBarLayout.liftOnScrollTargetViewId = liftOnScrollTargetId
        }
        val collapsingLayout =
            view.findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
                ?: return view
        val topAppBarGravity = getTopAppBarGravity4Annotation() ?: return view
        if (topAppBarGravity.collapsedTitleGravity != Gravity.NO_GRAVITY) {
            collapsingLayout.collapsedTitleGravity = topAppBarGravity.collapsedTitleGravity
        }
        if (topAppBarGravity.expandedTitleGravity != Gravity.NO_GRAVITY) {
            collapsingLayout.expandedTitleGravity = topAppBarGravity.expandedTitleGravity
        }
        return view
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

    private fun getTopAppBar4Annotation() =
        javaClass.getAnnotation(TopAppBar::class.java)?.value ?: TopAppBar.TYPE_NONE

    private fun isShowTopAppBarDefaultNavigation4Annotation() =
        javaClass.getAnnotation(TopAppBarDefaultNavigation::class.java)?.isShow ?: false

    private fun getTopAppBarGravity4Annotation() =
        javaClass.getAnnotation(TopAppBarGravity::class.java)

    open fun onBackPressed() = true

    override fun onDestroyView() {
        super.onDestroyView()
        logDTag("BOX --> Fragment", "${javaClass.simpleName}(${title}) onDestroyView")
        isLoaded = false
    }
}