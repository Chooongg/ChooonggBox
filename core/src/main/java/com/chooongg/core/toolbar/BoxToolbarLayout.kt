package com.chooongg.core.toolbar

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import com.chooongg.core.R
import com.chooongg.core.widget.behavior.AppBarLayoutBehavior
import com.chooongg.ext.attrDimensionPixelSize
import com.chooongg.ext.getActivity
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.appbar.CollapsingToolbarLayout

@SuppressLint("RestrictedApi")
class BoxToolbarLayout @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = com.google.android.material.R.attr.coordinatorLayoutStyle,
) : CoordinatorLayout(context, attrs, defStyleAttr) {

    private val defStyleRes = com.google.android.material.R.style.Widget_Support_CoordinatorLayout

    val appBarLayout: AppBarLayout

    val collapsingToolbarLayout: CollapsingToolbarLayout?

    val boxToolbar: BoxToolbar

    init {
        fitsSystemWindows = true
        val a = context.obtainStyledAttributes(
            attrs, R.styleable.BoxToolbarLayout, defStyleAttr, defStyleRes
        )
        appBarLayout =
            AppBarLayout(context, null, com.google.android.material.R.attr.appBarLayoutStyle)
        appBarLayout.fitsSystemWindows = true
        appBarLayout.id = com.chooongg.R.id.box_appbar_layout
        if (a.hasValue(R.styleable.BoxToolbarLayout_liftOnScroll)) {
            setLiftOnScroll(a.getBoolean(R.styleable.BoxToolbarLayout_liftOnScroll, false))
        }
        if (a.hasValue(R.styleable.BoxToolbarLayout_liftOnScrollTargetViewId)) {
            setLiftOnScrollTargetViewId(
                a.getResourceId(R.styleable.BoxToolbarLayout_liftOnScrollTargetViewId, 0)
            )
        }
        super.addView(appBarLayout, 0, LayoutParams(-1, -2).apply {
            behavior = AppBarLayoutBehavior()
        })
        boxToolbar = BoxToolbar(context, null, com.google.android.material.R.attr.toolbarStyle)
        a.getBoolean(R.styleable.BoxToolbarLayout_autoSetActionBar, true).let {
            val activity = context.getActivity()
            if (activity is AppCompatActivity) activity.setSupportActionBar(boxToolbar)
        }
        if (a.getBoolean(R.styleable.BoxToolbarLayout_defaultNavigation, false)) {
            boxToolbar.setNavigationIcon(R.drawable.ic_app_bar_back)
            boxToolbar.setNavigationOnClickListener { context.getActivity()?.onBackPressed() }
        }
        val style = a.getInt(R.styleable.BoxToolbarLayout_toolbarMode, 0)
        if (style == 0) {
            collapsingToolbarLayout = null
            appBarLayout.addView(boxToolbar, AppBarLayout.LayoutParams(-1, -2).apply {
                scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_NO_SCROLL
            })
        } else {
            if (style == 1) {
                collapsingToolbarLayout = CollapsingToolbarLayout(
                    context, null,
                    com.google.android.material.R.attr.collapsingToolbarLayoutMediumStyle
                )
                collapsingToolbarLayout.fitsSystemWindows = true
                collapsingToolbarLayout.maxLines = 2
                appBarLayout.addView(
                    collapsingToolbarLayout,
                    AppBarLayout.LayoutParams(
                        -1,
                        attrDimensionPixelSize(
                            com.google.android.material.R.attr.collapsingToolbarLayoutMediumSize, -2
                        )
                    ).apply {
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL and
                                AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED and
                                AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
                    })
            } else {
                collapsingToolbarLayout = CollapsingToolbarLayout(
                    context, null,
                    com.google.android.material.R.attr.collapsingToolbarLayoutLargeStyle
                )
                collapsingToolbarLayout.fitsSystemWindows = true
                collapsingToolbarLayout.maxLines = 3
                appBarLayout.addView(
                    collapsingToolbarLayout,
                    AppBarLayout.LayoutParams(
                        -1,
                        attrDimensionPixelSize(
                            com.google.android.material.R.attr.collapsingToolbarLayoutLargeSize, -2
                        )
                    ).apply {
                        scrollFlags = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL and
                                AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED and
                                AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP
                    })
            }
            collapsingToolbarLayout.addView(
                boxToolbar,
                CollapsingToolbarLayout.LayoutParams(-1, -2).apply {
                    this.collapseMode = CollapsingToolbarLayout.LayoutParams.COLLAPSE_MODE_PIN
                })
        }
        a.recycle()
    }

    fun setLiftOnScroll(boolean: Boolean) {
        appBarLayout.isLiftOnScroll = boolean
    }

    fun setLiftOnScrollTargetViewId(@IdRes id: Int) {
        appBarLayout.liftOnScrollTargetViewId = id
    }

    override fun addView(child: View?, index: Int, params: ViewGroup.LayoutParams?) {
        if (child != null) {
            if (params == null) {
                super.addView(child, index, LayoutParams(-1, -1).apply {
                    behavior = AppBarLayout.ScrollingViewBehavior()
                })
            } else if (params is LayoutParams && params.behavior == null) {
                params.behavior = AppBarLayout.ScrollingViewBehavior()
            }
        }
        super.addView(child, index, params)
    }
}