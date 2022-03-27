package com.chooongg.http.download

import com.chooongg.http.ResponseProgressInterceptor
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.HttpUrl.Companion.toHttpUrl
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

object DownloadUtils {

    fun download(url: String) {
        val urlTemp = url.toHttpUrl()
        val okHttpClient = OkHttpClient.Builder().apply {
            connectTimeout(30L, TimeUnit.SECONDS)
            writeTimeout(30L, TimeUnit.SECONDS)
            readTimeout(30L, TimeUnit.SECONDS)
            addInterceptor(ResponseProgressInterceptor())
            addNetworkInterceptor(StethoInterceptor())
        }.build()
        val api = Retrofit.Builder().client(okHttpClient)
            .baseUrl("${urlTemp.scheme}://${urlTemp.host}")
            .build().create(DownloadAPI::class.java)
        val download = api.download(url)
        download.enqueue(object : Callback<ResponseBody> {
            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {

            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
            }
        })
    }
}