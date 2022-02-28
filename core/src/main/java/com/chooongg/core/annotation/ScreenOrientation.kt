package com.chooongg.core.annotation

import android.content.pm.ActivityInfo
import androidx.annotation.IntDef

@IntDef(
    ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED, // 未指定
    ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE, // 保持横屏
    ActivityInfo.SCREEN_ORIENTATION_PORTRAIT, // 保持竖屏
    ActivityInfo.SCREEN_ORIENTATION_USER, // 用户当前设置的 orientation 值
    ActivityInfo.SCREEN_ORIENTATION_BEHIND, // 保持和上一个 Activity 的方向一致
    ActivityInfo.SCREEN_ORIENTATION_SENSOR, // 完全根据物理传感器的方向来决定。注意用这个值时会忽略用户在系统设置中的旋转开关状态。（注意一般机器即使用这个值也不会支持竖屏旋转180度）
    ActivityInfo.SCREEN_ORIENTATION_NOSENSOR, // 忽略物理传感器的方向。这将导致用户旋转手机时不会切换横竖屏。
    ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE, // 保持横屏，但可以根据物理传感器来决定横屏的方向。
    ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT, // 保持竖屏，但可以根据物理传感器来决定竖屏的方向。
    ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE, // 保持横屏，但方向与使用 landscape 时相反。
    ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT, // 保持竖屏，但方向与使用 portrait 时相反。
    ActivityInfo.SCREEN_ORIENTATION_FULL_SENSOR, // 与 sensor 大致相同，区别在于这个属性会允许4个方向都可以旋转。
    ActivityInfo.SCREEN_ORIENTATION_USER_LANDSCAPE, // 保持横屏，但可以在用户允许旋转的情况下，根据物理传感器来决定横屏的方向。（注意与 sensorLandscape 对比）
    ActivityInfo.SCREEN_ORIENTATION_USER_PORTRAIT, // 保持竖屏，但可以在用户允许旋转的情况下，根据物理传感器来决定竖屏的方向。（注意与 sensorPortrait 对比）
    ActivityInfo.SCREEN_ORIENTATION_FULL_USER, // 与 user 大致相同，区别在于如果用户允许旋转，这个属性允许4个方向都可以旋转。
    ActivityInfo.SCREEN_ORIENTATION_LOCKED // 屏幕方向会锁定在当前方向，不能再旋转。
)
annotation class ScreenOrientation
