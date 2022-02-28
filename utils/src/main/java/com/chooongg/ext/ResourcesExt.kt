package com.chooongg.ext

import android.content.Context
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.annotation.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Context.resourcesString(@StringRes id: Int) =
    resources.getString(id)
fun Context.resourcesString(@StringRes id: Int, vararg format: Any?) =
    resources.getString(id, *format)
fun Context.resourcesText(@StringRes id: Int) =
    resources.getText(id)
fun Context.resourcesTextArray(@ArrayRes id: Int): Array<CharSequence> =
    resources.getTextArray(id)
fun Context.resourcesStringArray(@ArrayRes id: Int): Array<String> =
    resources.getStringArray(id)
fun Context.resourcesIntArray(@ArrayRes id: Int) =
    resources.getIntArray(id)
fun Context.resourcesDimension(@DimenRes id: Int) =
    resources.getDimension(id)
fun Context.resourcesDimensionPixelOffset(@DimenRes id: Int) =
    resources.getDimensionPixelOffset(id)
fun Context.resourcesDimensionPixelSize(@DimenRes id: Int) =
    resources.getDimensionPixelSize(id)
fun Context.resourcesDrawable(@DrawableRes id: Int) =
    ContextCompat.getDrawable(this, id)
fun Context.resourcesColor(@ColorRes id: Int) =
    ContextCompat.getColor(this, id)
fun Context.resourcesColorStateList(@ColorRes id: Int) =
    ContextCompat.getColorStateList(this, id)
fun Context.resourcesBoolean(@BoolRes id: Int) =
    resources.getBoolean(id)
fun Context.resourcesInteger(@IntegerRes id: Int) =
    resources.getInteger(id)
fun Context.resourcesOpenRaw(@RawRes id: Int) =
    resources.openRawResource(id)
fun Context.resourcesAnimation(@AnimRes id: Int): Animation =
    AnimationUtils.loadAnimation(this, id)

fun Fragment.resourcesString(@StringRes id: Int) =
    requireContext().resourcesString(id)
fun Fragment.resourcesString(@StringRes id: Int, vararg format: Any?) =
    requireContext().resourcesString(id, *format)
fun Fragment.resourcesText(@StringRes id: Int) =
    requireContext().resourcesText(id)
fun Fragment.resourcesTextArray(@ArrayRes id: Int) =
    requireContext().resourcesTextArray(id)
fun Fragment.resourcesStringArray(@ArrayRes id: Int) =
    requireContext().resourcesStringArray(id)
fun Fragment.resourcesIntArray(@ArrayRes id: Int) =
    requireContext().resourcesIntArray(id)
fun Fragment.resourcesDimension(@DimenRes id: Int) =
    requireContext().resourcesDimension(id)
fun Fragment.resourcesDimensionPixelOffset(@DimenRes id: Int) =
    requireContext().resourcesDimensionPixelOffset(id)
fun Fragment.resourcesDimensionPixelSize(@DimenRes id: Int) =
    requireContext().resourcesDimensionPixelSize(id)
fun Fragment.resourcesDrawable(@DrawableRes id: Int) =
    requireContext().resourcesDrawable(id)
fun Fragment.resourcesColor(@ColorRes id: Int) =
    requireContext().resourcesColor(id)
fun Fragment.resourcesColorStateList(@ColorRes id: Int) =
    requireContext().resourcesColorStateList(id)
fun Fragment.resourcesBoolean(@BoolRes id: Int) =
    requireContext().resourcesBoolean(id)
fun Fragment.resourcesInteger(@IntegerRes id: Int) =
    requireContext().resourcesInteger(id)
fun Fragment.resourcesOpenRaw(@RawRes id: Int) =
    requireContext().resourcesOpenRaw(id)
fun Fragment.resourcesAnimation(@AnimRes id: Int): Animation =
    requireContext().resourcesAnimation(id)

fun View.resourcesText(@StringRes id: Int) =
    context.resourcesText(id)
fun View.resourcesTextArray(@ArrayRes id: Int) =
    context.resourcesTextArray(id)
fun View.resourcesStringArray(@ArrayRes id: Int) =
    context.resourcesStringArray(id)
fun View.resourcesIntArray(@ArrayRes id: Int) =
    context.resourcesIntArray(id)
fun View.resourcesDimension(@DimenRes id: Int) =
    context.resourcesDimension(id)
fun View.resourcesDimensionPixelOffset(@DimenRes id: Int) =
    context.resourcesDimensionPixelOffset(id)
fun View.resourcesDimensionPixelSize(@DimenRes id: Int) =
    context.resourcesDimensionPixelSize(id)
fun View.resourcesDrawable(@DrawableRes id: Int) =
    context.resourcesDrawable(id)
fun View.resourcesColor(@ColorRes id: Int) =
    context.resourcesColor(id)
fun View.resourcesColorStateList(@ColorRes id: Int) =
    context.resourcesColorStateList(id)
fun View.resourcesBoolean(@BoolRes id: Int) =
    context.resourcesBoolean(id)
fun View.resourcesInteger(@IntegerRes id: Int) =
    context.resourcesInteger(id)
fun View.resourcesOpenRaw(@RawRes id: Int) =
    context.resourcesOpenRaw(id)
fun View.resourcesAnimation(@AnimRes id: Int): Animation =
    context.resourcesAnimation(id)