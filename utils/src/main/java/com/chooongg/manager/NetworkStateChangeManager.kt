package com.chooongg.manager

import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.chooongg.ext.APPLICATION
import com.chooongg.ext.connectivityManager

object NetworkStateChangeManager {

    private val observers = ArrayList<NetworkStateChangedObserver>()

    init {
        APPLICATION.connectivityManager.registerNetworkCallback(
            NetworkRequest.Builder().build(), object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    observers.forEach { it.onConnect() }
                }

                override fun onLost(network: Network) {
                    observers.forEach { it.onDisconnect() }
                }

                override fun onCapabilitiesChanged(
                    network: Network,
                    networkCapabilities: NetworkCapabilities
                ) {
                    if (networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                        if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                            observers.forEach { it.onWifiConnect() }
                        } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                            observers.forEach { it.onMobileConnect() }
                        }
                    }
                }
            })
    }

    fun registerStateChangeObserver(observer: NetworkStateChangedObserver) {
        observers.add(observer)
    }

    fun unregisterStateChangeObserver(observer: NetworkStateChangedObserver) {
        observers.remove(observer)
    }

    interface NetworkStateChangedObserver {
        fun onDisconnect()
        fun onConnect()
        fun onMobileConnect()
        fun onWifiConnect()
    }
}