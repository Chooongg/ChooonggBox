package com.chooongg.core.ext

import com.google.android.material.appbar.AppBarLayout
import kotlin.math.abs

enum class State {
    EXPANDED, COLLAPSED, INTERMEDIATE
}

fun AppBarLayout.addOnStateChangedListener(block: (appBarLayout: AppBarLayout, state: State) -> Unit) {
    var state: State = State.EXPANDED
    addOnOffsetChangedListener(AppBarLayout.OnOffsetChangedListener { appBarLayout, verticalOffset ->
        if (verticalOffset == 0) {
            if (state != State.EXPANDED) {
                state = State.EXPANDED
                block(appBarLayout, state)
            }
        } else if (abs(verticalOffset) >= totalScrollRange) {
            if (state != State.COLLAPSED) {
                state = State.COLLAPSED
                block(appBarLayout, state)
            }
        } else {
            if (state != State.INTERMEDIATE) {
                state = State.INTERMEDIATE
                block(appBarLayout, state)
            }
        }
    })
}

