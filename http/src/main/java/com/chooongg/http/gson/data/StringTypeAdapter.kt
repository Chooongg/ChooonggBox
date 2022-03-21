package com.chooongg.http.gson.data

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class StringTypeAdapter : TypeAdapter<String?>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: String?) {
        out.value(value)
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): String? {
        return when (`in`.peek()) {
            JsonToken.STRING, JsonToken.NUMBER -> `in`.nextString()
            // 对于布尔类型比较特殊，需要做针对性处理
            JsonToken.BOOLEAN -> `in`.nextBoolean().toString()
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