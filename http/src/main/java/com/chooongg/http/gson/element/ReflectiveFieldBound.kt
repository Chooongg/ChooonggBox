package com.chooongg.http.gson.element

import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException

abstract class ReflectiveFieldBound(
    /** 字段名称  */
    val fieldName: String,
    /** 序列化标记  */
    val isSerialized: Boolean,
    /** 反序列化标记  */
    val isDeserialized: Boolean
) {

    @Throws(IOException::class, IllegalAccessException::class)
    abstract fun write(writer: JsonWriter?, value: Any?)
    @Throws(IOException::class, IllegalAccessException::class)
    abstract fun read(reader: JsonReader?, value: Any?)
    @Throws(IOException::class, IllegalAccessException::class)
    abstract fun writeField(value: Any?): Boolean
}