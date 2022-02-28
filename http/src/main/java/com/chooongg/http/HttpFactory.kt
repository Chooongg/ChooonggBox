package com.chooongg.http

import com.chooongg.ext.APPLICATION
import com.chooongg.http.annotation.BaseUrl
import com.chooongg.http.cookie.CookieManager
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import kotlin.reflect.KClass

object HttpFactory {

    private var defaultConfig = HttpConfig()

    fun setDefaultConfig(block: HttpConfig.() -> Unit) {
        defaultConfig = HttpConfig()
        block.invoke(defaultConfig)
    }

    /**
     * 获取接口类
     * 如需添加 baseUrl , 请在接口类添加[BaseUrl] (非必要)
     */
    fun <T : Any> getAPI(
        clazz: KClass<T>,
        block: (HttpConfig.() -> Unit)? = null
    ): T {
        val config = defaultConfig.copy()
        block?.invoke(config)
        val builder = Retrofit.Builder()
            .client(okHttpClientBuilder(config).build())
        val baseUrl = clazz.java.getAnnotation(BaseUrl::class.java)?.value
        if (!baseUrl.isNullOrEmpty()) builder.baseUrl(baseUrl)
        config.converterFactories.forEach { builder.addConverterFactory(it) }
        config.callAdapterFactories.forEach { builder.addCallAdapterFactory(it) }
        config.retrofitBuilder?.invoke(builder)
        val retrofit = builder.build()
        return retrofit.create(clazz.java)
    }

    /**
     * 获取接口类
     */
    fun <T : Any> getAPI(
        clazz: KClass<T>,
        baseUrl: String?,
        block: (HttpConfig.() -> Unit)? = null
    ): T {
        val config = defaultConfig.copy()
        block?.invoke(config)
        val builder = Retrofit.Builder()
            .client(okHttpClientBuilder(config).build())
        if (!baseUrl.isNullOrEmpty()) builder.baseUrl(baseUrl)
        config.converterFactories.forEach { builder.addConverterFactory(it) }
        config.callAdapterFactories.forEach { builder.addCallAdapterFactory(it) }
        config.retrofitBuilder?.invoke(builder)
        val retrofit = builder.build()
        return retrofit.create(clazz.java)
    }

    private fun okHttpClientBuilder(config: HttpConfig) =
        OkHttpClient.Builder().apply {
            connectTimeout(config.connectTimeout, TimeUnit.SECONDS)
            writeTimeout(config.writeTimeout, TimeUnit.SECONDS)
            readTimeout(config.readTimeout, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            cache(Cache(APPLICATION.cacheDir, config.cacheSize))
            cookieJar(CookieManager)
            if (config.sslSocketFactory != null && config.x509TrustManager != null) {
                sslSocketFactory(config.sslSocketFactory!!, config.x509TrustManager!!)
            }
            config.interceptors.forEach { addInterceptor(it) }
            config.networkInterceptors.forEach { addNetworkInterceptor(it) }
            config.okHttpClientBuilder?.invoke(this)
            addInterceptor(ResponseProgressInterceptor())
            addNetworkInterceptor(StethoInterceptor())
        }
}