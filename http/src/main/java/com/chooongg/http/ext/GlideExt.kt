package com.chooongg.http.ext

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.annotation.DrawableRes
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.chooongg.http.BoxGlide
import com.chooongg.http.GlideRequest
import java.io.File

fun ImageView.load(bitmap: Bitmap?) = BoxGlide.with(this).load(bitmap)
fun ImageView.load(drawable: Drawable?) = BoxGlide.with(this).load(drawable)
fun ImageView.load(url: String?) = BoxGlide.with(this).load(url)
fun ImageView.load(uri: Uri?) = BoxGlide.with(this).load(uri)
fun ImageView.load(file: File?) = BoxGlide.with(this).load(file)
fun ImageView.load(@DrawableRes resId: Int?) = BoxGlide.with(this).load(resId)
fun ImageView.load(byte: ByteArray?) = BoxGlide.with(this).load(byte)

fun ImageView.loadGif(bitmap: Bitmap?) = BoxGlide.with(this).asGif().load(bitmap)
fun ImageView.loadGif(drawable: Drawable?) = BoxGlide.with(this).asGif().load(drawable)
fun ImageView.loadGif(url: String?) = BoxGlide.with(this).asGif().load(url)
fun ImageView.loadGif(uri: Uri?) = BoxGlide.with(this).asGif().load(uri)
fun ImageView.loadGif(file: File?) = BoxGlide.with(this).asGif().load(file)
fun ImageView.loadGif(@DrawableRes resId: Int?) = BoxGlide.with(this).asGif().load(resId)
fun ImageView.loadGif(byte: ByteArray?) = BoxGlide.with(this).asGif().load(byte)

fun <T> GlideRequest<T>.finishedListener(onLoadFinished: () -> Unit) =
    listener(object : RequestListener<T> {
        override fun onLoadFailed(
            e: GlideException?,
            model: Any?,
            target: Target<T>?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadFinished()
            return false
        }

        override fun onResourceReady(
            resource: T,
            model: Any?,
            target: Target<T>?,
            dataSource: DataSource?,
            isFirstResource: Boolean
        ): Boolean {
            onLoadFinished()
            return false
        }
    })