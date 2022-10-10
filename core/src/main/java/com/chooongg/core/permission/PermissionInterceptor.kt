package com.chooongg.core.permission

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.os.Build
import android.widget.SimpleAdapter
import com.chooongg.core.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.hjq.permissions.IPermissionInterceptor
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions

class PermissionInterceptor : IPermissionInterceptor {

    override fun requestPermissions(
        activity: Activity?,
        allPermissions: MutableList<String>?,
        callback: OnPermissionCallback?
    ) {
        super.requestPermissions(activity, allPermissions, callback)
    }

    override fun grantedPermissions(
        activity: Activity?,
        permissions: MutableList<String>?,
        grantedPermissions: MutableList<String>?,
        all: Boolean,
        callback: OnPermissionCallback?
    ) {
        super.grantedPermissions(activity, permissions, grantedPermissions, all, callback)
    }

    override fun deniedPermissions(
        activity: Activity?,
        permissions: MutableList<String>?,
        deniedPermissions: MutableList<String>?,
        never: Boolean,
        callback: OnPermissionCallback?
    ) {
        // 回调授权失败的方法
        callback?.onDenied(permissions, never)
        if (never) {
            if (activity == null || permissions == null) return
            showPermissionDialog(activity, permissions)
            return
        }
    }

    /**
     * 显示授权对话框
     */
    private fun showPermissionDialog(activity: Activity, permissions: List<String?>) {
        // 这里的 Dialog 只是示例，没有用 DialogFragment 来处理 Dialog 生命周期
        MaterialAlertDialogBuilder(activity)
            .setIcon(R.drawable.ic_dialog_permission)
            .setTitle(R.string.common_permission_fail_2_list)
            .setAdapter(
                SimpleAdapter(
                    activity,
                    getPermissionHintList(activity, permissions),
                    R.layout.item_dialog_permission,
                    arrayOf("name", "icon"),
                    intArrayOf(R.id.tv_title, R.id.iv_icon)
                ), null
            )
            .setPositiveButton(R.string.common_permission_goto) { dialogInterface: DialogInterface, _: Int ->
                dialogInterface.dismiss()
                XXPermissions.startPermissionActivity(activity, permissions)
            }.show()
    }

    @Suppress("DEPRECATION")
    private fun getPermissionHintList(
        context: Context,
        permissions: List<String?>
    ): MutableList<Map<String, Any>> {
        if (permissions.isEmpty()) {
            return mutableListOf()
        }
        val hints: MutableList<Map<String, Any>> = ArrayList()
        for (permission in permissions) {
            when (permission) {
                Permission.READ_EXTERNAL_STORAGE,
                Permission.WRITE_EXTERNAL_STORAGE,
                Permission.MANAGE_EXTERNAL_STORAGE -> {
                    val hint = context.getString(R.string.common_permission_storage)
                    val drawableRes = R.drawable.ic_permission_storage
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.CAMERA -> {
                    val hint = context.getString(R.string.common_permission_camera)
                    val drawableRes = R.drawable.ic_permission_camera
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.RECORD_AUDIO -> {
                    val hint = context.getString(R.string.common_permission_microphone)
                    val drawableRes = R.drawable.ic_permission_microphone
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.ACCESS_FINE_LOCATION,
                Permission.ACCESS_COARSE_LOCATION,
                Permission.ACCESS_BACKGROUND_LOCATION -> {
                    val hint: String = if (!permissions.contains(Permission.ACCESS_FINE_LOCATION) &&
                        !permissions.contains(Permission.ACCESS_COARSE_LOCATION)
                    ) {
                        context.getString(R.string.common_permission_location_background)
                    } else {
                        context.getString(R.string.common_permission_location)
                    }
                    val drawableRes = R.drawable.ic_permission_location
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.READ_PHONE_STATE,
                Permission.CALL_PHONE,
                Permission.ADD_VOICEMAIL,
                Permission.USE_SIP,
                Permission.READ_PHONE_NUMBERS,
                Permission.ANSWER_PHONE_CALLS -> {
                    val hint = context.getString(R.string.common_permission_phone)
                    val drawableRes = R.drawable.ic_permission_phone
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.GET_ACCOUNTS,
                Permission.READ_CONTACTS,
                Permission.WRITE_CONTACTS -> {
                    val hint = context.getString(R.string.common_permission_contacts)
                    val drawableRes = R.drawable.ic_permission_contact
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.READ_CALENDAR,
                Permission.WRITE_CALENDAR -> {
                    val hint = context.getString(R.string.common_permission_calendar)
                    val drawableRes = R.drawable.ic_permission_calendar
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.READ_CALL_LOG,
                Permission.WRITE_CALL_LOG,
                Permission.PROCESS_OUTGOING_CALLS -> {
                    val hint: String
                    val drawableRes: Int
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                        hint = context.getString(R.string.common_permission_call_log)
                        drawableRes = R.drawable.ic_permission_call_log
                    } else {
                        hint = context.getString(R.string.common_permission_phone)
                        drawableRes = R.drawable.ic_permission_phone
                    }
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.BODY_SENSORS -> {
                    val hint = context.getString(R.string.common_permission_sensors)
                    val drawableRes = R.drawable.ic_permission_sensors
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.ACTIVITY_RECOGNITION -> {
                    val hint = context.getString(R.string.common_permission_activity_recognition)
                    val drawableRes = R.drawable.ic_permission_recognition
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.SEND_SMS,
                Permission.RECEIVE_SMS,
                Permission.READ_SMS,
                Permission.RECEIVE_WAP_PUSH,
                Permission.RECEIVE_MMS -> {
                    val hint = context.getString(R.string.common_permission_sms)
                    val drawableRes = R.drawable.ic_permission_sms
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.REQUEST_INSTALL_PACKAGES -> {
                    val hint = context.getString(R.string.common_permission_install)
                    val drawableRes = R.drawable.ic_permission_install
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.NOTIFICATION_SERVICE -> {
                    val hint = context.getString(R.string.common_permission_notification)
                    val drawableRes = R.drawable.ic_permission_notification
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.SYSTEM_ALERT_WINDOW -> {
                    val hint = context.getString(R.string.common_permission_window)
                    val drawableRes = R.drawable.ic_permission_window
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                Permission.WRITE_SETTINGS -> {
                    val hint = context.getString(R.string.common_permission_setting)
                    val drawableRes = R.drawable.ic_permission_setting
                    val map = mapOf<String, Any>(Pair("name", hint), Pair("icon", drawableRes))
                    if (!isContains(hints, map)) {
                        hints.add(map)
                    }
                }
                else -> Unit
            }
        }
        return hints
    }

    private fun isContains(dataList: List<Map<String, Any>>, data: Map<String, Any>): Boolean {
        dataList.forEach {
            if (it["name"] == data["name"]) {
                return true
            }
        }
        return false
    }
}