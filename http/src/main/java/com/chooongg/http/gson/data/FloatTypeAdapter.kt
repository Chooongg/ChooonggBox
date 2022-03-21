package com.chooongg.http.gson.data

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class FloatTypeAdapter : TypeAdapter<Float?>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Float?) {
        out.value(value)
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Float? {
        return when (`in`.peek()) {
            JsonToken.NUMBER -> `in`.nextDouble().toFloat()
            JsonToken.STRING -> {
                val result = `in`.nextString()
                if (result.isNullOrEmpty()) 0f else result.toFloat()
            }
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