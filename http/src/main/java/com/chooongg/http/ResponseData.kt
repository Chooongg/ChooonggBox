package com.chooongg.http

abstract class ResponseData<DATA> {

    private var onReExecuteRequestListener: (suspend () -> Unit)? = null

    internal fun setOnReExecuteRequestListener(block: suspend () -> Unit) {
        onReExecuteRequestListener = block
    }

    internal fun removeOnExecuteRequestListener(){
        onReExecuteRequestListener = null
    }

    suspend fun executeRequest() {
        onReExecuteRequestListener?.invoke()
    }

    abstract fun getCode(): String?
    abstract fun getMessage(): String?
    abstract suspend fun checkData(): DATA?
}