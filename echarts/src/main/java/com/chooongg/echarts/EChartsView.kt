package com.chooongg.echarts

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient
import com.chooongg.echarts.options.EChartsOption
import com.chooongg.ext.logD
import com.google.gson.Gson

@SuppressLint("SetJavaScriptEnabled")
class EChartsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    private var pageFinished = false

    private val waitingJavascriptInterface: MutableList<String> = ArrayList()

    init {
        settings.apply {
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            displayZoomControls = false
            addJavascriptInterface(EChartsJavascriptInterface, "Android")
            setSupportZoom(false)
            webViewClient = object : WebViewClient() {
                override fun onPageFinished(view: WebView?, url: String?) {
                    pageFinished = true
                    waitingJavascriptInterface.forEach { loadJavascript(it) }
                    waitingJavascriptInterface.clear()
                }
            }
        }
        super.loadUrl("file:///android_asset/ECharts.html")
    }

    override fun loadUrl(url: String) {
        if (pageFinished) {
            super.loadUrl(url)
        } else {
            waitingJavascriptInterface.add(url)
        }
    }

    fun loadJavascript(javascript: String) {
        if (pageFinished) {
            loadUrl("javascript:${javascript};")
        } else {
            waitingJavascriptInterface.add(javascript)
        }
    }

    fun setOptions(option: EChartsOption) {
        loadJavascript("setOption('${Gson().toJson(option)}')")
    }

    object EChartsJavascriptInterface {
        @JavascriptInterface
        fun log(value: String) {
            logD(value)
        }
    }
}