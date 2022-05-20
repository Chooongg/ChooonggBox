package com.chooongg.core.widget.arcLayout.manager

import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path

class ClipPathManager : ClipManager {

    private val path = Path()
    override val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var createClipPath: ClipPathCreator? = null

    init {
        paint.color = Color.BLACK
        paint.style = Paint.Style.FILL
        paint.isAntiAlias = true
        paint.strokeWidth = 1f
    }

    override fun requiresBitmap() = createClipPath != null && createClipPath!!.requiresBitmap()

    private fun createClipPath(width: Int, height: Int) = if (createClipPath != null) {
        createClipPath!!.createClipPath(width, height)
    } else null

    fun setClipPathCreator(createClipPath: ClipPathCreator?) {
        this.createClipPath = createClipPath
    }

    override fun createMask(width: Int, height: Int): Path {
        return path
    }

    override val shadowConvexPath: Path get() = path

    override fun setupClipLayout(width: Int, height: Int) {
        path.reset()
        val clipPath = createClipPath(width, height)
        if (clipPath != null) {
            path.set(clipPath)
        }
    }

    interface ClipPathCreator {
        fun createClipPath(width: Int, height: Int): Path?
        fun requiresBitmap(): Boolean
    }
}