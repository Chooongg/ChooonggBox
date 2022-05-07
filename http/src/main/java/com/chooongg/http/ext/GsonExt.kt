package com.chooongg.http.ext

import com.google.gson.JsonElement
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

fun JsonElement.toRequestBody(): RequestBody {
    val mediaType = "application/json; charset=utf-8".toMediaType()
    return toString().toRequestBody(mediaType)
}