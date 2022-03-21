package com.chooongg.http.gson.data

import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.math.BigDecimal

class BigDecimalTypeAdapter : TypeAdapter<BigDecimal>() {

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: BigDecimal?) {
        out.value(value)
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): BigDecimal? {
        when (`in`.peek()) {
            JsonToken.NUMBER, JsonToken.STRING -> {
                val result = `in`.nextString()
                if (result.isNullOrEmpty()) {
                    return BigDecimal(0)
                }
                return BigDecimal(result)
            }
            else -> {
                `in`.skipValue()
                return null
            }
        }
    }
}