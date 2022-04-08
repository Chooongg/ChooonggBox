package com.chooongg.core.adapter

import android.view.View
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.chooongg.core.fragment.BoxFragment
import com.google.android.material.appbar.AppBarLayout

fun ViewPager2.setAdapterBindAppBarLayout(
    adapter: BoxFragmentAdapter<out BoxFragment>,
    appBarLayout: AppBarLayout
) {
    setAdapter(adapter)
    val liftOnScrollSwitchTargetIdCallback = object : ViewPager2.OnPageChangeCallback() {
        override fun onPageSelected(position: Int) {
            adapter.getLiftOnScrollTargetId(position)?.apply {
                appBarLayout.liftOnScrollTargetViewId = this
            }
        }
    }
    if (ViewCompat.isAttachedToWindow(this)) {
        registerOnPageChangeCallback(liftOnScrollSwitchTargetIdCallback)
    }
    addOnAttachStateChangeListener(object : View.OnAttachStateChangeListener {
        override fun onViewAttachedToWindow(view: View) {
            unregisterOnPageChangeCallback(liftOnScrollSwitchTargetIdCallback)
            registerOnPageChangeCallback(liftOnScrollSwitchTargetIdCallback)
        }

        override fun onViewDetachedFromWindow(view: View) {
            unregisterOnPageChangeCallback(liftOnScrollSwitchTargetIdCallback)
        }
    })
}

class BoxFragmentAdapter<T : BoxFragment> : FragmentStateAdapter {

    private val fragments: MutableList<T>

    constructor(fragmentActivity: FragmentActivity, fragments: MutableList<T>) : super(
        fragmentActivity
    ) {
        this.fragments = fragments
    }

    constructor(fragment: Fragment, fragments: MutableList<T>) : super(fragment) {
        this.fragments = fragments
    }

    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        fragments: MutableList<T>
    ) : super(fragmentManager, lifecycle) {
        this.fragments = fragments
    }

    override fun getItemCount() = fragments.size

    override fun createFragment(position: Int) = fragments[position]

    fun getTitle(position: Int) = fragments[position].title

    fun getLiftOnScrollTargetId(position: Int) = fragments[position].getLiftOnScrollTargetId()
}