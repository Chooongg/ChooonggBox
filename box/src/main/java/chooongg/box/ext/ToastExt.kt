package chooongg.box.ext

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import chooongg.box.manager.AppManager

/**
 * Toast 唯一实例
 */
private var boxToast: Toast? = null



/**
 * 展示 Toast
 */
fun showToast(text: CharSequence?, duration: Int = Toast.LENGTH_SHORT) {
    cancelToast()
    boxToast = Toast.makeText(AppManager.activityTop as? Context ?: APP, text, duration).apply {
        show()
    }
}

/**
 * 展示 Toast
 */
fun showToast(@StringRes resId: Int, duration: Int = Toast.LENGTH_SHORT) {
    cancelToast()
    boxToast = Toast.makeText(AppManager.activityTop as? Context ?: APP, resId, duration).apply {
        show()
    }
}

/**
 * 取消Toast
 */
fun cancelToast() {
    boxToast?.cancel()
    boxToast = null
}