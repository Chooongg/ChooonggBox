package com.chooongg.core.activity

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.chooongg.core.R
import com.chooongg.core.action.InitAction
import com.chooongg.core.annotation.*
import com.chooongg.core.ext.getMaterialColor
import com.chooongg.core.toolbar.BoxToolbar
import com.chooongg.ext.*
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback
import com.google.android.material.transition.platform.MaterialSharedAxis

@ActivityEdgeToEdge
abstract class BoxActivity : AppCompatActivity(), InitAction {

    inline val activity: BoxActivity get() = this

    inline val context: Context get() = this

    @IdRes
    protected open fun getLiftOnScrollTargetId(): Int? = null

    protected open fun initTransitions() = Unit

    override fun initContentByLazy() = Unit

    open fun onRefresh(any: Any? = null) = Unit

    override fun onCreate(savedInstanceState: Bundle?) {
        // 设置主题
        val theme4Annotation = getTheme4Annotation()
        if (theme4Annotation != null) setTheme(theme4Annotation)
        // 如果开启 Activity 过渡动画则初始化 Material 效果
        if (getActivityTransitions4Annotation()) {
            window.requestFeature(Window.FEATURE_ACTIVITY_TRANSITIONS)
            window.requestFeature(Window.FEATURE_CONTENT_TRANSITIONS)
            val contentView = contentView
            contentView.transitionName = "box_transitions_content"
            window.enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, true)
            window.exitTransition = null
            window.returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, false)
            window.reenterTransition = null
            setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
            setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
            window.sharedElementEnterTransition = buildContainerTransform(contentView, true)
            window.sharedElementReturnTransition = buildContainerTransform(contentView, false)
            window.sharedElementsUseOverlay = false
            initTransitions()
        }
        super.onCreate(savedInstanceState)
        // 注解标题解析
        val title4Annotation = getTitle4Annotation()
        if (title4Annotation != null) title = title4Annotation
        // Edge To Edge 实现
        if (getEdgeToEdge4Annotation() == true) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
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
        configTopAppbarLayout()
        logDTag("BOX --> Activity", "${javaClass.simpleName}(${title}) onCreated")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        logDTag("BOX --> Activity", "${javaClass.simpleName}(${title}) onPostCreated")
        super.onPostCreate(savedInstanceState)
        val contentView = contentView
        if (contentView.background == null) {
            contentView.setBackgroundColor(attrColor(android.R.attr.colorBackground))
        }
        contentView.isFocusable = true
        contentView.isFocusableInTouchMode = true
        // 设置自动隐藏软键盘
        if (isAutoHideIME4Annotation()) contentView.setOnClickListener {
            it.clearFocus()
            hideIME()
        }
        initConfig(savedInstanceState)
        initContent(savedInstanceState)
        initContentByLazy()
    }

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

    private fun buildContainerTransform(contentView: View, entering: Boolean) =
        MaterialContainerTransform(this, entering).apply {
            setAllContainerColors(contentView.getMaterialColor(com.google.android.material.R.attr.colorSurface))
            addTarget(contentView.id)
            fadeMode = MaterialContainerTransform.FADE_MODE_THROUGH
            interpolator = FastOutSlowInInterpolator()
            pathMotion = MaterialArcMotion()
            isDrawDebugEnabled = false
        }

    /**
     * 配置 TopAppbar 布局
     */
    private fun configTopAppbarLayout() {
        when (getTopAppBar4Annotation()) {
            TopAppBar.TYPE_SMALL -> super.setContentView(R.layout.box_activity_root_small)
            TopAppBar.TYPE_MEDIUM -> super.setContentView(R.layout.box_activity_root_medium)
            TopAppBar.TYPE_LARGE -> super.setContentView(R.layout.box_activity_root_large)
            else -> return
        }
        if (supportActionBar == null) setSupportActionBar(findViewById<BoxToolbar>(R.id.toolbar))
        val appBarLayout = findViewById<AppBarLayout>(R.id.appbar_layout)
        val liftOnScrollTargetId = getLiftOnScrollTargetId()
        if (liftOnScrollTargetId != null) {
            appBarLayout.liftOnScrollTargetViewId = liftOnScrollTargetId
        }
        val collapsingLayout = findViewById<CollapsingToolbarLayout>(R.id.collapsing_toolbar_layout)
            ?: return
        val topAppBarGravity = getTopAppBarGravity4Annotation() ?: return
        if (topAppBarGravity.collapsedTitleGravity != Gravity.NO_GRAVITY) {
            collapsingLayout.collapsedTitleGravity = topAppBarGravity.collapsedTitleGravity
        }
        if (topAppBarGravity.expandedTitleGravity != Gravity.NO_GRAVITY) {
            collapsingLayout.expandedTitleGravity = topAppBarGravity.expandedTitleGravity
        }
    }

    override fun setContentView(layoutResID: Int) {
        setContentView(layoutInflater.inflate(layoutResID, null))
    }

    override fun setContentView(view: View?) {
        when (getTopAppBar4Annotation()) {
            TopAppBar.TYPE_SMALL, TopAppBar.TYPE_MEDIUM, TopAppBar.TYPE_LARGE -> {
                val coordinatorLayout = getCoordinatorLayout()
                if (coordinatorLayout == null) {
                    super.setContentView(view)
                    return
                }
                if (view == null) return
                view.id = com.chooongg.R.id.content_view
                coordinatorLayout.addView(view, CoordinatorLayout.LayoutParams(-1, -1).apply {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                })
            }
            else -> super.setContentView(view)
        }
    }

    override fun setContentView(view: View?, params: ViewGroup.LayoutParams?) {
        when (getTopAppBar4Annotation()) {
            TopAppBar.TYPE_SMALL, TopAppBar.TYPE_MEDIUM, TopAppBar.TYPE_LARGE -> {
                val coordinatorLayout = getCoordinatorLayout()
                if (coordinatorLayout == null) {
                    super.setContentView(view)
                    return
                }
                if (view == null) return
                if (params is CoordinatorLayout.LayoutParams && params.behavior == null) {
                    params.behavior = AppBarLayout.ScrollingViewBehavior()
                }
                coordinatorLayout.addView(view, params)
            }
            else -> super.setContentView(view)
        }
    }

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
        javaClass.getAnnotation(ActivityEdgeToEdge::class.java)?.value

    private fun getActivityFitsNavigationBar() =
        javaClass.getAnnotation(ActivityFitsNavigationBar::class.java)?.value ?: true

    private fun isAutoHideIME4Annotation() =
        javaClass.getAnnotation(AutoHideIME::class.java)?.isEnable ?: true

    private fun getTopAppBar4Annotation() =
        javaClass.getAnnotation(TopAppBar::class.java)?.value ?: TopAppBar.TYPE_SMALL

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