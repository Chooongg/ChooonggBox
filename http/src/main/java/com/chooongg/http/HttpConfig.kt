package com.chooongg.http

import com.hjq.gson.factory.GsonFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.CallAdapter
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.*
import javax.net.ssl.SSLSocketFactory
import javax.net.ssl.X509TrustManager

data class HttpConfig(
    // 缓存保存时间-秒
    var maxAge: Long = 60L * 60 * 24 * 7,
    // 缓存保存时间-秒
    var maxStale: Long = 60L * 60 * 24 * 7,
    // 缓存大小
    var cacheSize: Long = DEFAULT_HTTP_CACHE_SIZE,
    // 连接超时时间
    var connectTimeout: Long = DEFAULT_TIMEOUT,
    // 写入超时时间
    var writeTimeout: Long = DEFAULT_TIMEOUT,
    // 读取超时时间
    var readTimeout: Long = DEFAULT_TIMEOUT,
    // 拦截器
    val interceptors: LinkedList<Interceptor> = LinkedList<Interceptor>(),

    // 网络层拦截器
    val networkInterceptors: LinkedList<Interceptor> = LinkedList<Interceptor>(),

    // Retrofit 转换器列表
    val converterFactories: ArrayList<Converter.Factory> = arrayListOf(
        ScalarsConverterFactory.create(),
        GsonConverterFactory.create(GsonFactory.getSingletonGson())
    ),
    var sslSocketFactory: SSLSocketFactory? = null,
    var x509TrustManager: X509TrustManager? = null,
    // Retrofit 请求适配器列表
    val callAdapterFactories: ArrayList<CallAdapter.Factory> = arrayListOf(),

    // 扩展方法，可以进行 OkHttpClient 的特殊配置
    var okHttpClientBuilder: (OkHttpClient.Builder.() -> Unit)? = null,

    // 扩展方法，可以进行 Retrofit 的特殊配置
    var retrofitBuilder: (Retrofit.Builder.() -> Unit)? = null,
) {
    companion object {
        const val DEFAULT_TIMEOUT = 30L // 默认超时时间 秒为单位
        const val DEFAULT_HTTP_CACHE_SIZE = 10L * 1048576L // 默认缓存大小
    }
}