package com.chooongg.core.popupMenu

import android.view.View
import androidx.appcompat.widget.PopupMenu
import com.chooongg.core.R

/**
 * Callback to be invoked once the custom item view gets created and bound.
 * It is to be used when some views inside need to be updated once inflated.
 *
 * This class is an extension for closure based callback that provides
 * additional functionality such as dismissing popup.
 *
 * @param callback block of the callback in which you can bind the given view
 */
class ViewBoundCallback(
    private val callback: ViewBoundCallback.(View) -> Unit
) : (View) -> Unit {
    internal var dismissPopupAction: () -> Unit = {
        throw IllegalStateException("Dismiss popup action has not been initialized. Make sure that you invoke dismissPopup function only after the popup has been shown.")
    }

    /**
     * Dismisses the shown popup.
     */
    fun dismissPopup() {
        dismissPopupAction()
        com.google.android.material.R.layout.abc_list_menu_item_icon
    }

    override fun invoke(view: View) {
        callback(view)
    }
}
