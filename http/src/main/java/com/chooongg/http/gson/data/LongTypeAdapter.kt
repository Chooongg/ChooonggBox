package com.chooongg.http.gson.data

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.math.BigDecimal

class LongTypeAdapter : TypeAdapter<Long?>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Long?) {
        out.value(value)
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Long? {
        return when (`in`.peek()) {
            JsonToken.NUMBER -> {
                try {
                    `in`.nextLong()
                } catch (e: NumberFormatException) {
                    // 如果带小数点则会抛出这个异常
                    BigDecimal(`in`.nextString()).toLong()
                }
            }
            JsonToken.STRING -> {
                val result = `in`.nextString()
                if (result.isNullOrEmpty()) {
                    0L
                } else try {
                    result.toLong()
                } catch (e: NumberFormatException) {
                    // 如果带小数点则会抛出这个异常
                    BigDecimal(result).toLong()
                }
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