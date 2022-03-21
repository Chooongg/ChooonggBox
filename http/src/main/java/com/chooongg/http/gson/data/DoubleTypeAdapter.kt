package com.chooongg.http.gson.data

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class DoubleTypeAdapter : TypeAdapter<Double>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Double?) {
        out.value(value)
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Double? {
        return when (`in`.peek()) {
            JsonToken.NUMBER -> `in`.nextDouble()
            JsonToken.STRING -> {
                val result = `in`.nextString()
                if (result.isNullOrEmpty()) 0.0 else result.toDouble()
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