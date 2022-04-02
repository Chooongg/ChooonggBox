package com.chooongg.core.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.chooongg.core.fragment.BoxFragment

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