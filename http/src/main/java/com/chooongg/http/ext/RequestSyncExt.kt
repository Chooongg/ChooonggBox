package com.chooongg.http.ext

import com.chooongg.http.ResponseData

suspend fun <DATA> requestSync(api: suspend () -> ResponseData<DATA>): DATA? {
    api()
}

suspend fun requestIntactSync() {

}

suspend fun requestBasicSync() {

}