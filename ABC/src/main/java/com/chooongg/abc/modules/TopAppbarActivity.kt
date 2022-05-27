package com.chooongg.abc.modules

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatDelegate
import com.chooongg.abc.R
import com.chooongg.abc.databinding.ActivityTopAppbarBinding
import com.chooongg.core.activity.BoxBindingActivity
import com.chooongg.core.annotation.TopAppBar
import com.chooongg.ext.setNightMode

@TopAppBar
class TopAppbarActivity : BoxBindingActivity<ActivityTopAppbarBinding>() {
    override fun initBinding() = ActivityTopAppbarBinding.inflate(layoutInflater)

    override fun initConfig(savedInstanceState: Bundle?) {
    }

    override fun initContent(savedInstanceState: Bundle?) {
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.night_mode,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
            R.id.light -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                true
            }
            R.id.dark -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                true
            }
            R.id.system -> {
                setNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}