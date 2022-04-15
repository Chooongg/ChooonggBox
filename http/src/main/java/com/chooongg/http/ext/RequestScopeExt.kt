package com.chooongg.http.ext

import com.chooongg.http.ResponseData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

fun <DATA> CoroutineScope.request(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: RetrofitCoroutinesDsl<ResponseData<DATA>, DATA>.() -> Unit
) = launch(context, start) { request(block) }

fun <RESPONSE : ResponseData<DATA>, DATA> CoroutineScope.requestIntact(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: RetrofitCoroutinesDsl<RESPONSE, DATA>.() -> Unit
) = launch(context, start) { requestIntact(block) }


fun <RESPONSE> CoroutineScope.requestBasic(
    context: CoroutineContext = EmptyCoroutineContext,
    start: CoroutineStart = CoroutineStart.DEFAULT,
    block: RetrofitCoroutinesBaseDsl<RESPONSE>.() -> Unit
) = launch(context, start) { requestBasic(block) }
