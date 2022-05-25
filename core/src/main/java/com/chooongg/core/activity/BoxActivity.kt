package com.chooongg.core.activity

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.FitWindowsLinearLayout
import androidx.appcompat.widget.FitWindowsViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import com.chooongg.core.R
import com.chooongg.core.action.InitAction
import com.chooongg.core.annotation.*
import com.chooongg.core.toolbar.BoxToolbar
import com.chooongg.ext.contentView
import com.chooongg.ext.hideIME
import com.chooongg.ext.logDTag
import com.chooongg.ext.resourcesString
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.transition.platform.MaterialSharedAxis

@ActivityEdgeToEdge
abstract class BoxActivity : AppCompatActivity(), InitAction {

    inline val activity: BoxActivity get() = this

    inline val context: Context get() = this

    protected open fun initTransitions() = Unit

    open fun initTopAppBar(parent: ViewGroup?, toolbar: Toolbar?) = Unit

    override fun initContentByLazy() = Unit

    open fun onRefresh(any: Any? = null) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        logDTag("BOX --> Activity", "${javaClass.simpleName}(${title}) onCreated")
        // 设置主题
        val theme4Annotation = getTheme4Annotation()
        if (theme4Annotation != null) setTheme(theme4Annotation)
        // 如果开启 Activity 过渡动画则初始化 Material 效果
        if (getActivityTransitions4Annotation()) {
            window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            window.apply {
                sharedElementsUseOverlay = true
                enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
                exitTransition = null
                returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
                reenterTransition = null
            }
            initTransitions()
        }
        super.onCreate(savedInstanceState)
        // 注解标题解析
        val title4Annotation = getTitle4Annotation()
        if (title4Annotation != null) title = title4Annotation
        // Edge To Edge 实现
        if (getEdgeToEdge4Annotation()) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            window.statusBarColor = Color.TRANSPARENT
            if (getActivityFitsNavigationBar()) {
                ViewCompat.setOnApplyWindowInsetsListener(contentView) { view, insets ->
                    if (insets.isVisible(WindowInsetsCompat.Type.navigationBars())) {
                        val navigationInsets =
                            insets.getInsets(WindowInsetsCompat.Type.navigationBars())
                        view.setPadding(
                            navigationInsets.left,
                            navigationInsets.top,
                            navigationInsets.right,
                            navigationInsets.bottom
                        )
                    }
                    insets
                }
            }
        }
        if (initViewRes() != View.NO_ID) super.setContentView(initViewRes())
        else initView(layoutInflater, null)?.let { super.setContentView(it) }
        configTopAppBar()
        initConfig(savedInstanceState)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        logDTag("BOX --> Activity", "${javaClass.simpleName}(${title}) onPostCreated")
        super.onPostCreate(savedInstanceState)
        val contentView = contentView
//        if (contentView.background == null) {
//            contentView.setBackgroundColor(attrColor(android.R.attr.colorBackground))
//        }
        contentView.isFocusable = true
        contentView.isFocusableInTouchMode = true
        // 设置自动隐藏软键盘
        if (isAutoHideIME4Annotation()) contentView.setOnClickListener {
            it.clearFocus()
            hideIME()
        }
        initContent(savedInstanceState)
        initContentByLazy()
    }

    protected open fun configTopAppBar() {
        if (isShowTopAppBar4Annotation()) {
            val parent = contentView.parent
            if (parent is FitWindowsViewGroup && parent is ViewGroup) {
                val topAppbarGroup =
                    layoutInflater.inflate(R.layout.box_activity_top_appbar, parent, false)
                val toolbar = topAppbarGroup.findViewById<BoxToolbar>(R.id.toolbar)
                parent.addView(topAppbarGroup, if (parent is FitWindowsLinearLayout) 0 else -1)
                topAppbarGroup.elevation = toolbar.elevation
                toolbar.elevation = 0f
                topAppbarGroup.background = toolbar.background
                toolbar.background = null
                if (getEdgeToEdge4Annotation()) {
                    ViewCompat.setOnApplyWindowInsetsListener(topAppbarGroup) { view, insets ->
                        if (insets.isVisible(WindowInsetsCompat.Type.statusBars())) {
                            val statusBarInsets =
                                insets.getInsets(WindowInsetsCompat.Type.statusBars())
                            view.setPadding(
                                statusBarInsets.left,
                                statusBarInsets.top,
                                statusBarInsets.right,
                                statusBarInsets.bottom
                            )
                        }
                        insets
                    }
                }
                initTopAppBar(topAppbarGroup as ViewGroup, toolbar)
            }
        }
    }

    override fun setContentView(layoutResID: Int) = Unit
    override fun setContentView(view: View?) = Unit
    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) = Unit

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else super.onOptionsItemSelected(item)
    }

    override fun setSupportActionBar(toolbar: Toolbar?) {
        super.setSupportActionBar(toolbar)
        // 设置返回按钮
        if (isShowTopAppBarDefaultNavigation4Annotation()) {
            supportActionBar!!.setHomeButtonEnabled(true)
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_app_bar_back)
        }
    }

//    private fun buildContainerTransform(contentView: View, entering: Boolean) =
//        MaterialContainerTransform(this, entering).apply {
//            addTarget(contentView.id)
//            containerColor = attrColor(com.google.android.material.R.attr.colorSurface)
//            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
//            pathMotion = MaterialArcMotion()
//            interpolator = FastOutSlowInInterpolator()
//            isDrawDebugEnabled = false
//        }

    protected fun getCoordinatorLayout(): CoordinatorLayout? =
        findViewById(R.id.coordinator_layout)

    protected fun getAppBarLayout(): AppBarLayout? =
        findViewById(R.id.appbar_layout)

    protected fun getCollapsingToolbarLayout(): CollapsingToolbarLayout? =
        findViewById(R.id.collapsing_toolbar_layout)

    private fun getTheme4Annotation() =
        javaClass.getAnnotation(Theme::class.java)?.value

    private fun getActivityTransitions4Annotation() =
        javaClass.getAnnotation(ActivityTransitions::class.java)?.value ?: true

    private fun getTitle4Annotation() = when {
        javaClass.isAnnotationPresent(TitleRes::class.java) ->
            resourcesString(javaClass.getAnnotation(TitleRes::class.java)!!.value)
        javaClass.isAnnotationPresent(Title::class.java) ->
            javaClass.getAnnotation(Title::class.java)!!.value
        else -> null
    }

    private fun getEdgeToEdge4Annotation() =
        javaClass.getAnnotation(ActivityEdgeToEdge::class.java)?.value ?: false

    private fun getActivityFitsNavigationBar() =
        javaClass.getAnnotation(ActivityFitsNavigationBar::class.java)?.value ?: true

    private fun isAutoHideIME4Annotation() =
        javaClass.getAnnotation(AutoHideIME::class.java)?.isEnable ?: true

    private fun isShowTopAppBar4Annotation() =
        javaClass.getAnnotation(TopAppBar::class.java)?.isShow ?: false

    private fun isShowTopAppBarDefaultNavigation4Annotation() =
        javaClass.getAnnotation(TopAppBarDefaultNavigation::class.java)?.isShow ?: true

    private fun getTopAppBarGravity4Annotation() =
        javaClass.getAnnotation(TopAppBarGravity::class.java)

    fun clearTransition() {
        contentView.transitionName = null
    }

    override fun onDestroy() {
        super.onDestroy()
        logDTag("BOX --> Activity", "${javaClass.simpleName}(${title}) onDestroy")
    }
}