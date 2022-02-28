package com.chooongg.utils

import android.Manifest
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresPermission
import com.chooongg.ext.APPLICATION
import com.chooongg.ext.connectivityManager

object NetworkUtils {

    /**
     * 当前是否连接网络
     */
    @Suppress("DEPRECATION")
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkConnected(): Boolean {
        val cm = APPLICATION.connectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
            } ?: false
        } else {
            val networkInfo = cm.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected && networkInfo.isAvailable
        }
    }

    /**
     * 当前使用WIFI连接网络
     */
    @Suppress("DEPRECATION")
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun isNetworkWifiConnected(): Boolean {
        val cm = APPLICATION.connectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) &&
                        hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
            } ?: false
        } else {
            val networkInfo = cm.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected && networkInfo.type == ConnectivityManager.TYPE_WIFI
        }
    }

    /**
     * 当前使用蜂窝网络连接网络
     */
    @Suppress("DEPRECATION")
    @RequiresPermission(Manifest.permission.ACCESS_NETWORK_STATE)
    fun currentNetWorkStatusIsCellular(): Boolean {
        val cm = APPLICATION.connectivityManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            cm.getNetworkCapabilities(cm.activeNetwork)?.run {
                hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                        && hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)
                        && hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
            } ?: false
        } else {
            val networkInfo = cm.activeNetworkInfo
            networkInfo != null && networkInfo.isConnected && networkInfo.type == ConnectivityManager.TYPE_MOBILE
        }
    }
}