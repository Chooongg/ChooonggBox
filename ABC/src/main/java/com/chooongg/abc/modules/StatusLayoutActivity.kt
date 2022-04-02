package com.chooongg.abc.modules

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.chooongg.abc.R
import com.chooongg.abc.databinding.ActivityStatusLayoutBinding
import com.chooongg.core.activity.BoxBindingActivity
import com.chooongg.ext.doOnClick
import com.chooongg.statusLayout.status.ProgressStatus

class StatusLayoutActivity : BoxBindingActivity<ActivityStatusLayoutBinding>() {

    override fun initConfig(savedInstanceState: Bundle?) {
        binding.btnProgress.doOnClick {
            binding.statusLayout.show(ProgressStatus::class)
        }
    }

    override fun initContent(savedInstanceState: Bundle?) {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.status_layout, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == R.id.success) {
            binding.statusLayout.showSuccess()
            true
        } else super.onOptionsItemSelected(item)
    }
}