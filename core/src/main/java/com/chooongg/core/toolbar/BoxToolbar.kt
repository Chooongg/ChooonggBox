package com.chooongg.core.toolbar

import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.updatePaddingRelative
import com.chooongg.core.R
import com.chooongg.core.annotation.ActivityEdgeToEdge
import com.chooongg.ext.getActivity
import com.chooongg.ext.getStatusBarHeight
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.divider.MaterialDivider
import com.google.android.material.shape.MaterialShapeUtils

class BoxToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.toolbarStyle,
    defStyleRes: Int = R.style.BoxWidget_Toolbar_Surface,
) : MaterialToolbar(context, attrs, defStyleAttr) {

    private var actualElevation: Float = 0f

    private val autoSetActionBar: Boolean

    private var dividerView: View? = null

    init {
        val a =
            context.obtainStyledAttributes(attrs, R.styleable.BoxToolbar, defStyleAttr, defStyleRes)
        if (a.getBoolean(R.styleable.BoxToolbar_defaultNavigation, false)) {
            setNavigationIcon(R.drawable.ic_app_bar_back)
            setNavigationOnClickListener { context.getActivity()?.onBackPressed() }
        }
        autoSetActionBar = a.getBoolean(R.styleable.BoxToolbar_autoSetActionBar, true)
        val statusBarPadding = a.getBoolean(R.styleable.BoxToolbar_statusBarEdgePadding, false)
        val isEdgeToEdge =
            context.getActivity()?.javaClass?.getAnnotation(ActivityEdgeToEdge::class.java)?.value
        if (statusBarPadding && isEdgeToEdge == true) {
            updatePaddingRelative(top = paddingTop + getStatusBarHeight())
        }
        a.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        val activity = context.getActivity()
        if (autoSetActionBar) {
            if (activity is AppCompatActivity) {
                activity.setSupportActionBar(this)
            }
        }
    }

    override fun setElevation(elevation: Float) {
        super.setElevation(elevation)
        actualElevation = elevation
    }

    fun showDivider() {
        if (dividerView == null) {
            dividerView = MaterialDivider(context).apply {
                layoutParams = LayoutParams(-1, -2).apply {
                    gravity = Gravity.BOTTOM
                }
                addView(this)
            }
        }
    }

    fun hideDivider() {
        if (dividerView != null) {
            removeView(dividerView)
        }
    }

    /**
     * 适配黑夜模式的高度覆盖层模式
     */
    fun setElevationOverlayMode(isVirtualElevation: Boolean = true) {
        if (isVirtualElevation) {
            elevation = 0f
            MaterialShapeUtils.setElevation(this, actualElevation)
        } else {
            elevation = actualElevation
        }
    }
}