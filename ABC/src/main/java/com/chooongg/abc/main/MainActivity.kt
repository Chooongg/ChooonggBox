package com.chooongg.abc.main

import android.os.Bundle
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.viewpager2.widget.ViewPager2
import com.chooongg.abc.R
import com.chooongg.abc.databinding.ActivityMainBinding
import com.chooongg.abc.main.fragment.ColorFragment
import com.chooongg.abc.main.fragment.HomeFragment
import com.chooongg.core.activity.BoxBindingActivity
import com.chooongg.core.adapter.BoxFragmentAdapter
import com.chooongg.core.annotation.TopAppBar
import com.chooongg.core.fragment.BoxFragment

@TopAppBar(TopAppBar.TYPE_NONE)
class MainActivity : BoxBindingActivity<ActivityMainBinding>() {

    private val fragments: MutableList<BoxFragment> = mutableListOf(
        HomeFragment(), ColorFragment()
    )

    override fun initBinding() = ActivityMainBinding.inflate(layoutInflater)

    override fun initConfig(savedInstanceState: Bundle?) {
        ViewCompat.setOnApplyWindowInsetsListener(binding.navigationView) { view, insets ->
            view.updatePadding(bottom = insets.getInsets(WindowInsetsCompat.Type.navigationBars()).bottom)
            insets
        }
        binding.viewPager.isUserInputEnabled = false
        binding.viewPager.offscreenPageLimit = 666
        binding.viewPager.adapter = BoxFragmentAdapter(this, fragments)
        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                when (position) {
                    0 -> binding.navigationView.selectedItemId = R.id.home
                    1 -> binding.navigationView.selectedItemId = R.id.color
                }
            }
        })
        binding.navigationView.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.home -> binding.viewPager.setCurrentItem(0, true)
                R.id.color -> binding.viewPager.setCurrentItem(1, true)
                else -> return@setOnItemSelectedListener false
            }
            true
        }
        binding.navigationView.setOnItemReselectedListener {
            when (it.itemId) {
                R.id.home -> fragments[0].onReselected()
                R.id.color -> fragments[1].onReselected()
            }
        }
    }

    override fun initContent(savedInstanceState: Bundle?) {
    }
}