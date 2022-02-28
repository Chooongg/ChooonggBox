package com.chooongg.http

import com.chooongg.http.body.ProgressResponseBody
import com.chooongg.http.ext.HttpProgressListener
import com.chooongg.http.ext.getTag
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 用于支持下载进度回调的拦截器
 */
internal class ResponseProgressInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        //获取原始请求
        val request = chain.request()
        //取出进度回调参数
        val responseProgressListener = request.getTag<HttpProgressListener>()
        //获取原始响应
        var response = chain.proceed(request)
        if (responseProgressListener != null && response.body != null) {
            //如果有下载进度回调，并且有响应体，则构建新的响应体以监听进度回调
            val progressResponseBody =
                ProgressResponseBody(response.body!!, responseProgressListener)
            response = response.newBuilder()
                .body(progressResponseBody)
                .build()
        }
        return response
    }
}