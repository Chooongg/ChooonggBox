package com.chooongg.core.eventBus

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStore
import androidx.lifecycle.ViewModelStoreOwner
import com.chooongg.ext.APPLICATION

object ApplicationScopeViewModelProvider : ViewModelStoreOwner {

    override fun getViewModelStore(): ViewModelStore {
        return ViewModelStore()
    }

    fun <T : ViewModel> getApplicationScopeViewModel(modelClass: Class<T>): T {
        return ViewModelProvider(
            ApplicationScopeViewModelProvider,
            ViewModelProvider.AndroidViewModelFactory.getInstance(APPLICATION)
        )[modelClass]
    }
}