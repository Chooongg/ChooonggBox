package com.chooongg.ext

import android.content.Intent

fun Intent.launchClearTask() {
    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}

fun Intent.launchSingleTop() {
    addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)
}

fun Intent.launchNewTask() {
    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
}

fun Intent.launchNoHistory() {
    addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
}

fun Intent.removeFlags() {
    flags = 0
}