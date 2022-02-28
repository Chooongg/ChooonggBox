package com.chooongg.core.fragment

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

open class BoxFragmentStateAdapter : FragmentStateAdapter {

    private var fragments: MutableList<BoxFragment>

    constructor(
        fragmentActivity: FragmentActivity,
        fragments: MutableList<BoxFragment>
    ) : super(fragmentActivity) {
        this.fragments = fragments
    }

    constructor(
        fragment: Fragment,
        fragments: MutableList<BoxFragment>
    ) : super(fragment) {
        this.fragments = fragments
    }

    constructor(
        fragmentManager: FragmentManager,
        lifecycle: Lifecycle,
        fragments: MutableList<BoxFragment>
    ) : super(fragmentManager, lifecycle) {
        this.fragments = fragments
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNewInstance(fragments: MutableList<BoxFragment>) {
        this.fragments = fragments
        notifyDataSetChanged()
    }

    override fun createFragment(position: Int) = fragments[position]
    override fun getItemCount() = fragments.size
    open fun getTitle(position: Int) = fragments[position].title
}