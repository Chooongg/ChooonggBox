package com.chooongg.core.ext

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.AttrRes
import com.google.android.material.color.MaterialColors

fun View.getMaterialColor(@AttrRes resId: Int) = MaterialColors.getColor(this, resId)

fun TextView.setTextColorMaterial(@AttrRes resId: Int) {
    setTextColor(getMaterialColor(resId))
}

fun TextView.setHintTextColorMaterial(@AttrRes resId: Int) {
    setHintTextColor(getMaterialColor(resId))
}

fun ImageView.setColorFilterMaterial(@AttrRes resId: Int) {
    setColorFilter(getMaterialColor(resId))
}

fun View.setBackgroundMaterial(@AttrRes resId: Int) {
    setBackgroundColor(getMaterialColor(resId))
}