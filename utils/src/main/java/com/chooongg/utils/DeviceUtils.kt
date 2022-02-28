package com.chooongg.utils

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.chooongg.ext.APPLICATION
import com.chooongg.ext.telephonyManager
import java.io.File
import java.util.*


object DeviceUtils {

    /**
     * 设备是否有Root权限
     */
    fun isDeviceRooted(): Boolean {
        val su = "su"
        val locations = arrayOf(
            "/system/bin/", "/system/xbin/", "/sbin/", "/system/sd/xbin/",
            "/system/bin/failsafe/", "/data/local/xbin/", "/data/local/bin/", "/data/local/",
            "/system/sbin/", "/usr/bin/", "/vendor/bin/"
        )
        for (location in locations) {
            if (File(location + su).exists()) return true
        }
        return false
    }

    /**
     * 设备是否启用ADB
     */
    fun isAdbEnabled(): Boolean {
        return Settings.Secure.getInt(
            APPLICATION.contentResolver,
            Settings.Global.ADB_ENABLED,
            0
        ) > 0
    }

    /**
     * 设备系统的版本名称
     */
    fun getSDKVersionName(): String? {
        return Build.VERSION.RELEASE
    }

    /**
     * 设备系统的版本号
     */
    fun getSDKVersionCode(): Int {
        return Build.VERSION.SDK_INT
    }

    /**
     * Return whether device is emulator.
     *
     * @return `true`: yes<br></br>`false`: no
     */
    @SuppressLint("QueryPermissionsNeeded")
    fun isEmulator(): Boolean {
        val checkProperty = (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.lowercase(Locale.getDefault()).contains("vbox")
                || Build.FINGERPRINT.lowercase(Locale.getDefault()).contains("test-keys")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic")
                || "google_sdk" == Build.PRODUCT)
        if (checkProperty) return true
        var operatorName = ""
        val tm = APPLICATION.telephonyManager
        val name = tm.networkOperatorName
        if (name != null) operatorName = name
        val checkOperatorName = operatorName.lowercase(Locale.ROOT) == "android"
        if (checkOperatorName) return true
        val url = "tel:" + "123456"
        val intent = Intent()
        intent.data = Uri.parse(url)
        intent.action = Intent.ACTION_DIAL
        return intent.resolveActivity(APPLICATION.packageManager) == null
    }
}