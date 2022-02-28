package com.chooongg.core.activity

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.WindowCompat
import androidx.core.view.updateLayoutParams
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
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
import com.google.android.material.color.MaterialColors
import com.google.android.material.transition.platform.MaterialArcMotion
import com.google.android.material.transition.platform.MaterialContainerTransform
import com.google.android.material.transition.platform.MaterialContainerTransformSharedElementCallback

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
            val contentView = contentView
            contentView.transitionName = "box_transitions_content"
            setEnterSharedElementCallback(MaterialContainerTransformSharedElementCallback())
            setExitSharedElementCallback(MaterialContainerTransformSharedElementCallback())
            window.sharedElementEnterTransition = buildContainerTransform(contentView, true)
            window.sharedElementReturnTransition = buildContainerTransform(contentView, false)
            window.sharedElementsUseOverlay = false
            initTransitions()
        }
        super.onCreate(savedInstanceState)
        // 设置屏幕方向
        val orientation4Annotation = getScreenOrientation4Annotation()
        if (orientation4Annotation != null) requestedOrientation = orientation4Annotation
        // 注解标题解析
        val title4Annotation = getTitle4Annotation()
        if (title4Annotation != null) title = title4Annotation
        // Edge To Edge 实现
        val edgeToEdge = getEdgeToEdge4Annotation()
        if (edgeToEdge == true) {
            WindowCompat.setDecorFitsSystemWindows(window, false)
        }
        configTopAppbarLayout()
        logDTag("ACTIVITY", "${javaClass.simpleName}(${title}) onCreated")
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        logDTag("ACTIVITY", "${javaClass.simpleName}(${title}) onPostCreated")
        super.onPostCreate(savedInstanceState)
        contentView.isFocusable = true
        contentView.isFocusableInTouchMode = true
        // 设置自动隐藏软键盘
        if (isAutoHideIME4Annotation()) contentView.setOnClickListener {
            hideIME()
            it.clearFocus()
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
            setAllContainerColors(
                MaterialColors.getColor(
                    contentView, com.google.android.material.R.attr.colorSurface
                )
            )
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
        when (getTopAppBar4Annotation()) {
            TopAppBar.TYPE_SMALL, TopAppBar.TYPE_MEDIUM, TopAppBar.TYPE_LARGE -> {
                val coordinatorLayout = getCoordinatorLayout()
                if (coordinatorLayout == null) {
                    super.setContentView(layoutResID)
                    return
                }
                val view = layoutInflater.inflate(layoutResID, coordinatorLayout)
                view.updateLayoutParams<CoordinatorLayout.LayoutParams> {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                }
            }
            else -> super.setContentView(layoutResID)
        }
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

    private fun getScreenOrientation4Annotation() =
        javaClass.getAnnotation(ActivityScreenOrientation::class.java)?.value

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

    private fun isAutoHideIME4Annotation() =
        javaClass.getAnnotation(AutoHideIME::class.java)?.isEnable ?: true

    private fun getTopAppBar4Annotation() =
        javaClass.getAnnotation(TopAppBar::class.java)?.value ?: TopAppBar.TYPE_SMALL

    private fun isShowTopAppBarDefaultNavigation4Annotation() =
        javaClass.getAnnotation(TopAppBarDefaultNavigation::class.java)?.isShow ?: true

    private fun getTopAppBarGravity4Annotation() =
        javaClass.getAnnotation(TopAppBarGravity::class.java)

    override fun onDestroy() {
        super.onDestroy()
        logDTag("ACTIVITY", "${javaClass.simpleName}(${title}) onDestroy")
    }
}