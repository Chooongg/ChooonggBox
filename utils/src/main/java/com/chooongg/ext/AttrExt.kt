package com.chooongg.ext

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.fragment.app.Fragment

fun Context.attrText(@AttrRes id: Int): CharSequence {
    val a = obtainStyledAttributes(intArrayOf(id))
    val text = a.getText(0)
    a.recycle()
    return text
}

fun Context.attrString(@AttrRes id: Int): String? {
    val a = obtainStyledAttributes(intArrayOf(id))
    val string = a.getString(0)
    a.recycle()
    return string
}

fun Context.attrBoolean(@AttrRes id: Int, defValue: Boolean): Boolean {
    val a = obtainStyledAttributes(intArrayOf(id))
    val boolean = a.getBoolean(0, defValue)
    a.recycle()
    return boolean
}

fun Context.attrInt(@AttrRes id: Int, defValue: Int): Int {
    val a = obtainStyledAttributes(intArrayOf(id))
    val int = a.getInt(0, defValue)
    a.recycle()
    return int
}

fun Context.attrFloat(@AttrRes id: Int, defValue: Float): Float {
    val a = obtainStyledAttributes(intArrayOf(id))
    val float = a.getFloat(0, defValue)
    a.recycle()
    return float
}

fun Context.attrColor(@AttrRes id: Int, @ColorInt defValue: Int): Int {
    val a = obtainStyledAttributes(intArrayOf(id))
    val color = a.getColor(0, defValue)
    a.recycle()
    return color
}

fun Context.attrColorStateList(@AttrRes id: Int): ColorStateList? {
    val a = obtainStyledAttributes(intArrayOf(id))
    val colorStateList = a.getColorStateList(0)
    a.recycle()
    return colorStateList
}

fun Context.attrInteger(@AttrRes id: Int, defValue: Int): Int {
    val a = obtainStyledAttributes(intArrayOf(id))
    val integer = a.getInteger(0, defValue)
    a.recycle()
    return integer
}

fun Context.attrDimension(@AttrRes id: Int, defValue: Float): Float {
    val a = obtainStyledAttributes(intArrayOf(id))
    val dimension = a.getDimension(0, defValue)
    a.recycle()
    return dimension
}

fun Context.attrDimensionPixelOffset(@AttrRes id: Int, defValue: Int): Int {
    val a = obtainStyledAttributes(intArrayOf(id))
    val dimension = a.getDimensionPixelOffset(0, defValue)
    a.recycle()
    return dimension
}


fun Context.attrDimensionPixelSize(@AttrRes id: Int, defValue: Int): Int {
    val a = obtainStyledAttributes(intArrayOf(id))
    val dimension = a.getDimensionPixelSize(0, defValue)
    a.recycle()
    return dimension
}

fun Context.attrResourcesId(@AttrRes id: Int, defValue: Int): Int {
    val a = obtainStyledAttributes(intArrayOf(id))
    val resourcesId = a.getResourceId(0, defValue)
    a.recycle()
    return resourcesId
}

fun Context.attrDrawable(@AttrRes id: Int): Drawable? {
    val a = obtainStyledAttributes(intArrayOf(id))
    val drawable = a.getDrawable(0)
    a.recycle()
    return drawable
}


fun Fragment.attrText(@AttrRes id: Int) =
    requireContext().attrText(id)

fun Fragment.attrString(@AttrRes id: Int) =
    requireContext().attrString(id)

fun Fragment.attrBoolean(@AttrRes id: Int, defValue: Boolean) =
    requireContext().attrBoolean(id, defValue)

fun Fragment.attrInt(@AttrRes id: Int, defValue: Int) =
    requireContext().attrInt(id, defValue)

fun Fragment.attrFloat(@AttrRes id: Int, defValue: Float) =
    requireContext().attrFloat(id, defValue)

fun Fragment.attrColor(@AttrRes id: Int, @ColorInt defValue: Int) =
    requireContext().attrColor(id, defValue)

fun Fragment.attrColorStateList(@AttrRes id: Int) =
    requireContext().attrColorStateList(id)

fun Fragment.attrInteger(@AttrRes id: Int, defValue: Int) =
    requireContext().attrInteger(id, defValue)

fun Fragment.attrDimension(@AttrRes id: Int, defValue: Float) =
    requireContext().attrDimension(id, defValue)

fun Fragment.attrDimensionPixelOffset(@AttrRes id: Int, defValue: Int) =
    requireContext().attrDimensionPixelOffset(id, defValue)

fun Fragment.attrDimensionPixelSize(@AttrRes id: Int, defValue: Int) =
    requireContext().attrDimensionPixelSize(id, defValue)

fun Fragment.attrResourcesId(@AttrRes id: Int, defValue: Int) =
    requireContext().attrResourcesId(id, defValue)

fun Fragment.attrDrawable(@AttrRes id: Int) =
    requireContext().attrDrawable(id)

fun View.attrText(@AttrRes id: Int) =
    context.attrText(id)

fun View.attrString(@AttrRes id: Int) =
    context.attrString(id)

fun View.attrBoolean(@AttrRes id: Int, defValue: Boolean) =
    context.attrBoolean(id, defValue)

fun View.attrInt(@AttrRes id: Int, defValue: Int) =
    context.attrInt(id, defValue)

fun View.attrFloat(@AttrRes id: Int, defValue: Float) =
    context.attrFloat(id, defValue)

fun View.attrColor(@AttrRes id: Int, @ColorInt defValue: Int) =
    context.attrColor(id, defValue)

fun View.attrColorStateList(@AttrRes id: Int) =
    context.attrColorStateList(id)

fun View.attrInteger(@AttrRes id: Int, defValue: Int) =
    context.attrInteger(id, defValue)

fun View.attrDimension(@AttrRes id: Int, defValue: Float) =
    context.attrDimension(id, defValue)

fun View.attrDimensionPixelOffset(@AttrRes id: Int, defValue: Int) =
    context.attrDimensionPixelOffset(id, defValue)

fun View.attrDimensionPixelSize(@AttrRes id: Int, defValue: Int) =
    context.attrDimensionPixelSize(id, defValue)

fun View.attrResourcesId(@AttrRes id: Int, defValue: Int) =
    context.attrResourcesId(id, defValue)

fun View.attrDrawable(@AttrRes id: Int) =
    context.attrDrawable(id)


fun Context.attrChildText(@AttrRes id: Int, @AttrRes childId: Int): CharSequence {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val attribute = intArrayOf(childId)
    val a = obtainStyledAttributes(typedValue.resourceId, attribute)
    val text = a.getText(0)
    a.recycle()
    return text
}

fun Context.attrChildString(@AttrRes id: Int, @AttrRes childId: Int): String? {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val attribute = intArrayOf(childId)
    val a = obtainStyledAttributes(typedValue.resourceId, attribute)
    val string = a.getString(0)
    a.recycle()
    return string
}

fun Context.attrChildBoolean(@AttrRes id: Int, @AttrRes childId: Int, defValue: Boolean): Boolean {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val attribute = intArrayOf(childId)
    val a = obtainStyledAttributes(typedValue.resourceId, attribute)
    val boolean = a.getBoolean(0, defValue)
    a.recycle()
    return boolean
}

fun Context.attrChildInt(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val attribute = intArrayOf(childId)
    val a = obtainStyledAttributes(typedValue.resourceId, attribute)
    val int = a.getInt(0, defValue)
    a.recycle()
    return int
}

fun Context.attrChildFloat(@AttrRes id: Int, @AttrRes childId: Int, defValue: Float): Float {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val attribute = intArrayOf(childId)
    val a = obtainStyledAttributes(typedValue.resourceId, attribute)
    val float = a.getFloat(0, defValue)
    a.recycle()
    return float
}

fun Context.attrChildColor(@AttrRes id: Int, @AttrRes childId: Int, @ColorInt defValue: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val attribute = intArrayOf(childId)
    val a = obtainStyledAttributes(typedValue.resourceId, attribute)
    val color = a.getColor(0, defValue)
    a.recycle()
    return color
}

fun Context.attrChildColorStateList(@AttrRes id: Int, @AttrRes childId: Int): ColorStateList? {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val attribute = intArrayOf(childId)
    val a = obtainStyledAttributes(typedValue.resourceId, attribute)
    val colorStateList = a.getColorStateList(0)
    a.recycle()
    return colorStateList
}

fun Context.attrChildInteger(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val attribute = intArrayOf(childId)
    val a = obtainStyledAttributes(typedValue.resourceId, attribute)
    val integer = a.getInteger(0, defValue)
    a.recycle()
    return integer
}

fun Context.attrChildDimension(@AttrRes id: Int, @AttrRes childId: Int, defValue: Float): Float {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val attribute = intArrayOf(childId)
    val a = obtainStyledAttributes(typedValue.resourceId, attribute)
    val dimension = a.getDimension(0, defValue)
    a.recycle()
    return dimension
}

fun Context.attrChildDimensionPixelOffset(
    @AttrRes id: Int,
    @AttrRes childId: Int,
    defValue: Int
): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val attribute = intArrayOf(childId)
    val a = obtainStyledAttributes(typedValue.resourceId, attribute)
    val dimension = a.getDimensionPixelOffset(0, defValue)
    a.recycle()
    return dimension
}


fun Context.attrChildDimensionPixelSize(
    @AttrRes id: Int,
    @AttrRes childId: Int,
    defValue: Int
): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val attribute = intArrayOf(childId)
    val a = obtainStyledAttributes(typedValue.resourceId, attribute)
    val dimension = a.getDimensionPixelSize(0, defValue)
    a.recycle()
    return dimension
}

fun Context.attrChildResourcesId(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int): Int {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val attribute = intArrayOf(childId)
    val a = obtainStyledAttributes(typedValue.resourceId, attribute)
    val resourcesId = a.getResourceId(0, defValue)
    a.recycle()
    return resourcesId
}

fun Context.attrChildDrawable(@AttrRes id: Int, @AttrRes childId: Int): Drawable? {
    val typedValue = TypedValue()
    theme.resolveAttribute(id, typedValue, true)
    val attribute = intArrayOf(childId)
    val a = obtainStyledAttributes(typedValue.resourceId, attribute)
    val drawable = a.getDrawable(0)
    a.recycle()
    return drawable
}

fun Fragment.attrChildText(@AttrRes id: Int, @AttrRes childId: Int) =
    requireContext().attrChildText(id, childId)

fun Fragment.attrChildString(@AttrRes id: Int, @AttrRes childId: Int) =
    requireContext().attrChildString(id, childId)

fun Fragment.attrChildBoolean(@AttrRes id: Int, @AttrRes childId: Int, defValue: Boolean) =
    requireContext().attrChildBoolean(id, childId, defValue)

fun Fragment.attrChildInt(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    requireContext().attrChildInt(id, childId, defValue)

fun Fragment.attrChildFloat(@AttrRes id: Int, @AttrRes childId: Int, defValue: Float) =
    requireContext().attrChildFloat(id, childId, defValue)

fun Fragment.attrChildColor(@AttrRes id: Int, @AttrRes childId: Int, @ColorInt defValue: Int) =
    requireContext().attrChildColor(id, childId, defValue)

fun Fragment.attrChildColorStateList(@AttrRes id: Int, @AttrRes childId: Int) =
    requireContext().attrChildColorStateList(id, childId)

fun Fragment.attrChildInteger(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    requireContext().attrChildInteger(id, childId, defValue)

fun Fragment.attrChildDimension(@AttrRes id: Int, @AttrRes childId: Int, defValue: Float) =
    requireContext().attrChildDimension(id, childId, defValue)

fun Fragment.attrChildDimensionPixelOffset(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    requireContext().attrChildDimensionPixelOffset(id, childId, defValue)

fun Fragment.attrChildDimensionPixelSize(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    requireContext().attrChildDimensionPixelSize(id, childId, defValue)

fun Fragment.attrChildResourcesId(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    requireContext().attrChildResourcesId(id, childId, defValue)

fun Fragment.attrChildDrawable(@AttrRes id: Int, @AttrRes childId: Int) =
    requireContext().attrChildDrawable(id, childId)

fun View.attrChildText(@AttrRes id: Int, @AttrRes childId: Int) =
    context.attrChildText(id, childId)

fun View.attrChildString(@AttrRes id: Int, @AttrRes childId: Int) =
    context.attrChildString(id, childId)

fun View.attrChildBoolean(@AttrRes id: Int, @AttrRes childId: Int, defValue: Boolean) =
    context.attrChildBoolean(id, childId, defValue)

fun View.attrChildInt(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    context.attrChildInt(id, childId, defValue)

fun View.attrChildFloat(@AttrRes id: Int, @AttrRes childId: Int, defValue: Float) =
    context.attrChildFloat(id, childId, defValue)

fun View.attrChildColor(@AttrRes id: Int, @AttrRes childId: Int, @ColorInt defValue: Int) =
    context.attrChildColor(id, childId, defValue)

fun View.attrChildColorStateList(@AttrRes id: Int, @AttrRes childId: Int) =
    context.attrChildColorStateList(id, childId)

fun View.attrChildInteger(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    context.attrChildInteger(id, childId, defValue)

fun View.attrChildDimension(@AttrRes id: Int, @AttrRes childId: Int, defValue: Float) =
    context.attrChildDimension(id, childId, defValue)

fun View.attrChildDimensionPixelOffset(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    context.attrChildDimensionPixelOffset(id, childId, defValue)

fun View.attrChildDimensionPixelSize(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    context.attrChildDimensionPixelSize(id, childId, defValue)

fun View.attrChildResourcesId(@AttrRes id: Int, @AttrRes childId: Int, defValue: Int) =
    context.attrChildResourcesId(id, childId, defValue)

fun View.attrChildDrawable(@AttrRes id: Int, @AttrRes childId: Int) =
    context.attrChildDrawable(id, childId)