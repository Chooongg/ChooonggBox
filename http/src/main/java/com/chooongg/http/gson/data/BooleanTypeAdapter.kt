package com.chooongg.http.gson.data

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class BooleanTypeAdapter : TypeAdapter<Boolean>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Boolean?) {
        out.value(value)
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Boolean? {
        return when (`in`.peek()) {
            JsonToken.BOOLEAN -> `in`.nextBoolean()
            // 如果后台返回 "true" 或者 "TRUE"，则处理为 true，否则为 false
            JsonToken.STRING -> `in`.nextString().toBoolean()
            // 如果后台返回的是非 0 的数值则处理为 true，否则为 false
            JsonToken.NUMBER -> `in`.nextInt() != 0
            JsonToken.NULL -> {
                `in`.nextNull()
                null
            }
            else -> {
                `in`.skipValue()
                throw IllegalArgumentException()
            }
        }
    }
}