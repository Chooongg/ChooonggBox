package com.chooongg.http.ext

import com.google.gson.Gson
import com.google.gson.JsonElement
import com.hjq.gson.factory.GsonFactory
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody

val gson: Gson get() = GsonFactory.getSingletonGson()

fun JsonElement.toRequestBody(): RequestBody {
    val mediaType = "application/json; charset=utf-8".toMediaType()
    return toString().toRequestBody(mediaType)
}