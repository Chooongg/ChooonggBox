package com.chooongg.core.ext

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.fragment.app.Fragment
import com.chooongg.ext.getActivity
import kotlin.reflect.KClass

fun Context.startActivity(clazz: KClass<out Any>, block: (Intent.() -> Unit)? = null) {
    startActivity(clazz, getActivityOption(getActivity())?.toBundle(), block)
}

fun Context.startActivity(
    clazz: KClass<out Any>,
    view: View,
    block: (Intent.() -> Unit)? = null
) {
    startActivity(
        clazz, getActivityOption(getActivity(), Pair.create(view, "view"))?.toBundle(), block
    )
}

fun Context.startActivity(
    clazz: KClass<out Any>,
    vararg sharedElements: Pair<View, String>,
    block: (Intent.() -> Unit)? = null
) {
    startActivity(clazz, getActivityOption(getActivity(), *sharedElements)?.toBundle(), block)
}

fun Context.startActivity(
    clazz: KClass<out Any>,
    option: Bundle?,
    block: (Intent.() -> Unit)? = null
) {
    val intent = Intent(this, clazz.java)
    block?.invoke(intent)
    startActivity(intent, option)
}

fun Context.startActivityTransitionPage(
    clazz: KClass<out Any>,
    view: View,
    block: (Intent.() -> Unit)? = null
) {
    val intent = Intent(this, clazz.java)
    block?.invoke(intent)
    startActivity(
        intent,
        getActivityOption(
            getActivity(),
            Pair.create(view, "box_transitions_content")
        )?.toBundle()
    )
}

fun Fragment.startActivity(
    clazz: KClass<out Any>,
    block: (Intent.() -> Unit)? = null
) {
    startActivity(clazz, getActivityOption(activity)?.toBundle(), block)
}

fun Fragment.startActivity(
    clazz: KClass<out Any>,
    view: View,
    block: (Intent.() -> Unit)? = null
) {
    startActivity(clazz, getActivityOption(activity, Pair.create(view, "view"))?.toBundle(), block)
}

fun Fragment.startActivity(
    clazz: KClass<out Any>,
    vararg sharedElements: Pair<View, String>,
    block: (Intent.() -> Unit)? = null
) {
    startActivity(clazz, getActivityOption(activity, *sharedElements)?.toBundle(), block)
}

fun Fragment.startActivity(
    clazz: KClass<out Any>,
    option: Bundle?,
    block: (Intent.() -> Unit)? = null
) {
    val intent = Intent(requireContext(), clazz.java)
    block?.invoke(intent)
    startActivity(intent, option)
}

fun Fragment.startActivityTransitionPage(
    clazz: KClass<out Any>,
    view: View,
    block: (Intent.() -> Unit)? = null
) {
    val intent = Intent(requireContext(), clazz.java)
    block?.invoke(intent)
    startActivity(
        intent,
        getActivityOption(activity, Pair.create(view, "box_transitions_content"))?.toBundle()
    )
}

fun ActivityResultLauncher<Intent>.launch(
    context: Context,
    clazz: KClass<out Any>,
    block: (Intent.() -> Unit)? = null
) {
    launch(context, clazz, getActivityOption(context.getActivity()), block)
}

fun ActivityResultLauncher<Intent>.launch(
    context: Context,
    clazz: KClass<out Any>,
    view: View,
    block: (Intent.() -> Unit)? = null
) {
    launch(
        context,
        clazz,
        getActivityOption(context.getActivity(), Pair.create(view, "view")),
        block
    )
}

fun ActivityResultLauncher<Intent>.launch(
    context: Context,
    clazz: KClass<out Any>,
    vararg sharedElements: Pair<View, String>,
    block: (Intent.() -> Unit)? = null
) {
    launch(context, clazz, getActivityOption(context.getActivity(), *sharedElements), block)
}

fun ActivityResultLauncher<Intent>.launch(
    context: Context,
    clazz: KClass<out Any>,
    option: ActivityOptionsCompat?,
    block: (Intent.() -> Unit)? = null
) {
    val intent = Intent(context, clazz.java)
    block?.invoke(intent)
    launch(intent, option)
}

fun ActivityResultLauncher<Intent>.launchTransitionPage(
    context: Context,
    clazz: KClass<out Any>,
    view: View,
    block: (Intent.() -> Unit)? = null
) {
    val intent = Intent(context, clazz.java)
    block?.invoke(intent)
    launch(
        intent, getActivityOption(
            context.getActivity(), Pair.create(view, "box_transitions_content")
        )
    )
}

private fun getActivityOption(
    activity: Activity?,
    vararg sharedElements: Pair<View, String>
): ActivityOptionsCompat? {
    ActivityOptionsCompat.makeBasic()
    return if (activity != null) {
        if (sharedElements.size == 1) {
            ActivityOptionsCompat.makeSceneTransitionAnimation(
                activity, sharedElements[0].first, sharedElements[0].second
            )
        } else ActivityOptionsCompat.makeSceneTransitionAnimation(activity, *sharedElements)
    } else null
}