package com.chooongg.http.gson.element

import com.chooongg.http.gson.GsonFactory.getJsonCallback
import com.google.gson.JsonSyntaxException
import com.google.gson.TypeAdapter
import com.google.gson.internal.ObjectConstructor
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException

class ReflectiveTypeAdapter<T>(
    private val mConstructor: ObjectConstructor<T>,
    private val mBoundFields: Map<String, ReflectiveFieldBound>
) : TypeAdapter<T?>() {
    private var mTypeToken: TypeToken<*>? = null
    private var mFieldName: String? = null
    fun setReflectiveType(typeToken: TypeToken<*>?, fieldName: String?) {
        mTypeToken = typeToken
        mFieldName = fieldName
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): T? {
        val jsonToken = `in`.peek()
        if (jsonToken == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        if (jsonToken != JsonToken.BEGIN_OBJECT) {
            `in`.skipValue()
            val callback = getJsonCallback()
            callback?.invoke(mTypeToken!!, mFieldName!!, jsonToken)
            return null
        }
        val instance = mConstructor.construct()
        `in`.beginObject()
        while (`in`.hasNext()) {
            val name = `in`.nextName()
            val field = mBoundFields[name]
            if (field == null || !field.isDeserialized) {
                `in`.skipValue()
                continue
            }
            val peek = `in`.peek()
            try {
                field.read(`in`, instance)
            } catch (e: IllegalStateException) {
                throw JsonSyntaxException(e)
            } catch (e: IllegalAccessException) {
                throw AssertionError(e)
            } catch (e: IllegalArgumentException) {
                val callback = getJsonCallback()
                callback?.invoke(mTypeToken!!, mFieldName!!, peek)
            }
        }
        `in`.endObject()
        return instance
    }

    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: T?) {
        if (value == null) {
            out.nullValue()
            return
        }
        out.beginObject()
        for (fieldBound in mBoundFields.values) {
            try {
                if (fieldBound.writeField(value)) {
                    out.name(fieldBound.fieldName)
                    fieldBound.write(out, value)
                }
            } catch (e: IllegalAccessException) {
                throw AssertionError(e)
            }
        }
        out.endObject()
    }
}