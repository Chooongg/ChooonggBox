package com.chooongg.utils

import android.content.ClipData
import android.content.ClipboardManager
import com.chooongg.ext.APPLICATION
import com.chooongg.ext.clipboardManager


object ClipboardUtils {
    /**
     * Copy the text to clipboard.
     * The label equals name of package.
     * @param text The text.
     */
    fun copyText(text: CharSequence?) {
        APPLICATION.clipboardManager.setPrimaryClip(
            ClipData.newPlainText(
                APPLICATION.packageName,
                text
            )
        )
    }

    /**
     * Copy the text to clipboard.
     * @param label The label.
     * @param text  The text.
     */
    fun copyText(label: CharSequence?, text: CharSequence?) {
        APPLICATION.clipboardManager.setPrimaryClip(ClipData.newPlainText(label, text))
    }

    /**
     * Clear the clipboard.
     */
    fun clear() {
        APPLICATION.clipboardManager.setPrimaryClip(ClipData.newPlainText(null, ""))
    }

    /**
     * Return the label for clipboard.
     * @return the label for clipboard
     */
    fun getLabel(): CharSequence {
        return APPLICATION.clipboardManager.primaryClipDescription?.label ?: return ""
    }

    /**
     * Return the text for clipboard.
     * @return the text for clipboard
     */
    fun getText(): CharSequence {
        val cm: ClipboardManager = APPLICATION.clipboardManager
        val clip: ClipData? = cm.primaryClip
        if (clip != null && clip.itemCount > 0) {
            val text = clip.getItemAt(0).coerceToText(APPLICATION)
            if (text != null) return text
        }
        return ""
    }

    /**
     * Add the clipboard changed listener.
     */
    fun addChangedListener(listener: ClipboardManager.OnPrimaryClipChangedListener?) {
        APPLICATION.clipboardManager.addPrimaryClipChangedListener(listener)
    }

    /**
     * Remove the clipboard changed listener.
     */
    fun removeChangedListener(listener: ClipboardManager.OnPrimaryClipChangedListener?) {
        APPLICATION.clipboardManager.removePrimaryClipChangedListener(listener)
    }
}