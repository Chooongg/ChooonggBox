package com.chooongg.http.gson.data

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.math.BigDecimal

class IntegerTypeAdapter : TypeAdapter<Int?>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Int?) {
        out.value(value)
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Int? {
        return when (`in`.peek()) {
            JsonToken.NUMBER -> try {
                `in`.nextInt()
            } catch (e: NumberFormatException) {
                // 如果带小数点则会抛出这个异常
                `in`.nextDouble().toInt()
            }
            JsonToken.STRING -> {
                val result = `in`.nextString()
                if (result.isNullOrEmpty()) {
                    0
                } else try {
                    result.toInt()
                } catch (e: NumberFormatException) {
                    // 如果带小数点则会抛出这个异常
                    BigDecimal(result).toFloat().toInt()
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