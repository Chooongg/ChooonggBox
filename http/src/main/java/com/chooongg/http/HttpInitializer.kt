package com.chooongg.http

import android.content.Context
import android.graphics.Color
import android.os.Build
import androidx.startup.Initializer
import coil.Coil
import coil.ImageLoader
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.decode.SvgDecoder
import coil.decode.VideoFrameDecoder
import com.chooongg.BoxInitializer
import com.chooongg.ext.attrColor

class HttpInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        Coil.setImageLoader(
            ImageLoader.Builder(context)
                .components {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                        add(ImageDecoderDecoder.Factory())
                    } else {
                        add(GifDecoder.Factory())
                    }
                    add(SvgDecoder.Factory())
                    add(VideoFrameDecoder.Factory())
                }.placeholder(
                    context.attrColor(com.google.android.material.R.attr.colorOutline, Color.GRAY)
                ).fallback(
                    context.attrColor(com.google.android.material.R.attr.colorOutline, Color.GRAY)
                ).error(
                    context.attrColor(
                        com.google.android.material.R.attr.colorErrorContainer,
                        Color.GRAY
                    )
                ).build()
        )
    }

    override fun dependencies(): MutableList<Class<out Initializer<*>>> {
        return mutableListOf(BoxInitializer::class.java)
    }
}