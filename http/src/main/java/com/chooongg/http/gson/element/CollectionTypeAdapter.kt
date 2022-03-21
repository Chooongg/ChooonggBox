package com.chooongg.http.gson.element

import com.chooongg.http.gson.GsonFactory
import com.google.gson.Gson
import com.google.gson.TypeAdapter
import com.google.gson.internal.ObjectConstructor
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonToken
import com.google.gson.stream.JsonWriter
import java.io.IOException
import java.lang.reflect.Type

class CollectionTypeAdapter<E>(
    gson: Gson,
    elementType: Type?,
    elementTypeAdapter: TypeAdapter<E>?,
    constructor: ObjectConstructor<out MutableCollection<E>>
) : TypeAdapter<Collection<E>?>() {

    private val mElementTypeAdapter: TypeAdapter<E>
    private val mObjectConstructor: ObjectConstructor<out MutableCollection<E>>
    private var mTypeToken: TypeToken<*>? = null
    private var mFieldName: String? = null

    init {
        mElementTypeAdapter = TypeAdapterRuntimeTypeWrapper(gson, elementTypeAdapter, elementType)
        mObjectConstructor = constructor
    }

    fun setReflectiveType(typeToken: TypeToken<*>?, fieldName: String?) {
        mTypeToken = typeToken
        mFieldName = fieldName
    }

    @Throws(IOException::class)
    override fun read(`in`: JsonReader): Collection<E>? {
        val jsonToken = `in`.peek()
        if (jsonToken == JsonToken.NULL) {
            `in`.nextNull()
            return null
        }
        if (jsonToken != JsonToken.BEGIN_ARRAY) {
            `in`.skipValue()
            val callback = GsonFactory.getJsonCallback()
            callback?.invoke(mTypeToken!!, mFieldName!!, jsonToken!!)
            return null
        }
        val collection = mObjectConstructor.construct()
        `in`.beginArray()
        while (`in`.hasNext()) {
            val instance = mElementTypeAdapter.read(`in`)
            collection.add(instance)
        }
        `in`.endArray()
        return collection
    }

    @Throws(IOException::class)
    override fun write(out: JsonWriter, collection: Collection<E>?) {
        if (collection == null) {
            out.nullValue()
            return
        }
        out.beginArray()
        for (element in collection) {
            mElementTypeAdapter.write(out, element)
        }
        out.endArray()
    }
}