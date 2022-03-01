package com.chooongg.http.ext

import android.util.Log
import com.chooongg.ext.withIO
import com.chooongg.ext.withMain
import com.chooongg.http.ResponseData
import com.chooongg.http.exception.HttpException
import com.chooongg.http.exception.HttpNoneException

/**
 * 常规请求 DSL
 */
suspend fun <DATA> request(block: RetrofitCoroutinesDsl<ResponseData<DATA>, DATA>.() -> Unit) {
    RetrofitCoroutinesDsl<ResponseData<DATA>, DATA>().apply {
        block(this)
        executeRequest()
    }
}

/**
 * 完整的请求 DSL
 */
suspend fun <RESPONSE : ResponseData<DATA>, DATA> requestIntact(block: RetrofitCoroutinesDsl<RESPONSE, DATA>.() -> Unit) {
    RetrofitCoroutinesDsl<RESPONSE, DATA>().apply {
        block(this)
        executeRequest()
    }
}

/**
 * 基础的请求 DSL
 */
suspend fun <RESPONSE> requestBasic(block: RetrofitCoroutinesBaseDsl<RESPONSE>.() -> Unit) {
    RetrofitCoroutinesBaseDsl<RESPONSE>().apply {
        block(this)
        executeRequest()
    }
}

/**
 * 处理返回信息的网络请求封装
 */
open class RetrofitCoroutinesDsl<RESPONSE : ResponseData<DATA>, DATA> :
    RetrofitCoroutinesBaseDsl<RESPONSE>() {

    private var onSuccessMessage: (suspend (String?) -> Unit)? = null

    private var onSuccess: (suspend (DATA?) -> Unit)? = null

    fun onSuccessMessage(block: suspend (String?) -> Unit) {
        this.onSuccessMessage = block
    }

    fun onSuccess(block: suspend (data: DATA?) -> Unit) {
        this.onSuccess = block
    }

    override suspend fun processData(response: RESPONSE) {
        response.setOnReExecuteRequestListener { executeRequest() }
        val data = response.checkData()
        response.removeOnExecuteRequestListener()
        withMain {
            onSuccessMessage?.invoke(response.getMessage())
            onSuccess?.invoke(data)
        }
    }
}

/**
 * 默认网络请求封装
 */
open class RetrofitCoroutinesBaseDsl<RESPONSE> {

    private var api: (suspend () -> RESPONSE)? = null

    private var onStart: (suspend () -> Unit)? = null

    private var onResponse: (suspend (RESPONSE) -> Unit)? = null

    private var onFailed: (suspend (HttpException) -> Unit)? = null

    private var onEnd: (suspend (Boolean) -> Unit)? = null

    fun api(block: suspend () -> RESPONSE) {
        this.api = block
    }

    fun onStart(block: suspend () -> Unit) {
        this.onStart = block
    }

    fun onResponse(block: suspend (RESPONSE) -> Unit) {
        this.onResponse = block
    }

    fun onFailed(block: suspend (error: HttpException) -> Unit) {
        this.onFailed = block
    }

    fun onEnd(block: suspend (isSuccess: Boolean) -> Unit) {
        this.onEnd = block
    }

    /**
     * 处理数据信息 不通过时抛出异常即可
     */
    protected open suspend fun processData(response: RESPONSE) = Unit

    /**
     * 重新执行请求
     */
    suspend fun reExecuteRequest() {
        executeRequest()
    }

    /**
     * 执行网络请求
     */
    internal suspend fun executeRequest() {
        if (api == null) {
            Log.e("HttpRequest", "Not implemented api method, cancel operation.")
            return
        }
        withMain { onStart?.invoke() }
        withIO {
            try {
                val response = api!!.invoke()
                withMain { onResponse?.invoke(response) }
                processData(response)
                withMain { onEnd?.invoke(true) }
            } catch (e: Exception) {
                if (e is HttpNoneException) {
                    return@withIO
                }
                if (onFailed != null) {
                    val httpException = HttpException(e)
                    withMain { onFailed!!.invoke(httpException) }
                }
                withMain { onEnd?.invoke(false) }
            }
        }
    }
}