package com.chooongg.echarts

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import android.webkit.JavascriptInterface
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.chooongg.echarts.options.EChartsOption
import com.google.gson.Gson
import org.json.JSONObject

/**
 * ECharts WebView 展示
 * 基于echarts 5.1.3 min
 */
@SuppressLint("SetJavaScriptEnabled")
class EChartsView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : WebView(context, attrs, defStyleAttr) {

    private var pageFinished = false

    private val waitingJavascriptInterface: MutableList<String> = ArrayList()

    init {
        setBackgroundColor(0)
        background?.alpha = 0
        scrollBarStyle = View.SCROLLBARS_OUTSIDE_OVERLAY
        settings.apply {
            cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
            javaScriptEnabled = true
            javaScriptCanOpenWindowsAutomatically = true
            allowFileAccess = false
            builtInZoomControls = true
            displayZoomControls = false
            setSupportZoom(false)
            addJavascriptInterface(EChartsJavascriptInterface, "Android")
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

    fun setOption(option: EChartsOption) {
        loadJavascript("setOption('${Gson().toJson(option)}')")
    }

    fun setOption(block: EChartsOption.() -> Unit) {
        val option = EChartsOption()
        block.invoke(option)
        setOption(option)
    }

    fun setOptionJson(option: JSONObject) {
        if (!option.has("darkMode")){
            option.put("darkMode",false)
        }
        loadJavascript("setOption('$option')")
    }

    fun setOptionJson(block: JSONObject.() -> Unit) {
        val option = JSONObject()
        block.invoke(option)
        setOptionJson(option)
    }

    object EChartsJavascriptInterface {
        @JavascriptInterface
        fun log(value: String) {
            Log.d("ECharts", value)
        }
    }
}