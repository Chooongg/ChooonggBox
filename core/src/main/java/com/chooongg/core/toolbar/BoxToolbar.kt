package com.chooongg.core.toolbar

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.AttributeSet
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import com.chooongg.core.R
import com.chooongg.ext.*
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.divider.MaterialDivider
import com.google.android.material.elevation.ElevationOverlayProvider
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.MaterialShapeUtils

class BoxToolbar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = android.R.attr.toolbarStyle,
    defStyleRes: Int = R.style.BoxWidget_Toolbar_Surface,
) : MaterialToolbar(context, attrs, defStyleAttr) {

    private var actualElevation: Float = 0f

    private val autoSetActionBar: Boolean

    private val dividerView: View

    init {
        val a =
            context.obtainStyledAttributes(attrs, R.styleable.BoxToolbar, defStyleAttr, defStyleRes)
        if (a.getBoolean(R.styleable.BoxToolbar_defaultNavigation, false)) {
            setNavigationIcon(R.drawable.ic_app_bar_back)
            setNavigationOnClickListener { context.getActivity()?.onBackPressed() }
        }
        autoSetActionBar = a.getBoolean(R.styleable.BoxToolbar_autoSetActionBar, true)
        a.recycle()
        dividerView = MaterialDivider(context)
        dividerView.layoutParams =
            LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT).apply {
                gravity = Gravity.BOTTOM
            }
        dividerView.gone()
        addView(dividerView)
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
        dividerView.visible()
    }

    fun hideDivider() {
        dividerView.gone()
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