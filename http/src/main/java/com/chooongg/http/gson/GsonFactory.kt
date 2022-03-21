package com.chooongg.http.gson

import com.chooongg.http.gson.data.*
import com.chooongg.http.gson.element.CollectionTypeAdapterFactory
import com.chooongg.http.gson.element.ReflectiveTypeAdapterFactory
import com.google.gson.*
import com.google.gson.internal.ConstructorConstructor
import com.google.gson.internal.Excluder
import com.google.gson.internal.bind.TypeAdapters
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonToken
import java.lang.reflect.Type
import java.math.BigDecimal

object GsonFactory {

    private val instanceCreators = HashMap<Type, InstanceCreator<*>>()
    private val typeAdapterFactories = ArrayList<TypeAdapterFactory>()
    private var jsonCallback: ((typeToken: TypeToken<*>, fieldName: String, jsonToken: JsonToken) -> Unit)? =
        null

    @Volatile
    private var gson: Gson? = null

    fun getSingletonGson(): Gson {
        if (gson == null) {
            synchronized(GsonFactory::class.java) {
                if (gson == null) {
                    gson = newGsonBuilder().create()
                }
            }
        }
        return gson!!
    }

    fun setSingletonGson(gson: Gson) {
        this.gson = gson
    }

    fun registerTypeAdapterFactory(factory: TypeAdapterFactory) {
        this.typeAdapterFactories.add(factory)
    }

    fun setJsonCallback(callback: (typeToken: TypeToken<*>, fieldName: String, jsonToken: JsonToken) -> Unit) {
        this.jsonCallback = callback
    }

    fun getJsonCallback() = jsonCallback

    fun registerInstanceCreator(type: Type, creator: InstanceCreator<*>) {
        instanceCreators[type] = creator
    }

    fun newGsonBuilder(): GsonBuilder {
        val gsonBuilder = GsonBuilder()
        typeAdapterFactories.forEach {
            gsonBuilder.registerTypeAdapterFactory(it)
        }
        val constructor = ConstructorConstructor(instanceCreators, true)
        return gsonBuilder.registerTypeAdapterFactory(
            TypeAdapters.newFactory(
                String::class.java, StringTypeAdapter()
            )
        ).registerTypeAdapterFactory(
            TypeAdapters.newFactory(
                Boolean::class.javaPrimitiveType, Boolean::class.java, BooleanTypeAdapter()
            )
        ).registerTypeAdapterFactory(
            TypeAdapters.newFactory(
                Int::class.javaPrimitiveType, Int::class.java, IntegerTypeAdapter()
            )
        ).registerTypeAdapterFactory(
            TypeAdapters.newFactory(
                Long::class.javaPrimitiveType, Long::class.java, LongTypeAdapter()
            )
        ).registerTypeAdapterFactory(
            TypeAdapters.newFactory(
                Float::class.javaPrimitiveType, Float::class.java, FloatTypeAdapter()
            )
        ).registerTypeAdapterFactory(
            TypeAdapters.newFactory(
                Double::class.javaPrimitiveType, Double::class.java, DoubleTypeAdapter()
            )
        ).registerTypeAdapterFactory(
            TypeAdapters.newFactory(
                BigDecimal::class.java, BigDecimalTypeAdapter()
            )
        ).registerTypeAdapterFactory(CollectionTypeAdapterFactory(constructor))
            .registerTypeAdapterFactory(
                ReflectiveTypeAdapterFactory(
                    constructor, FieldNamingPolicy.IDENTITY, Excluder.DEFAULT
                )
            )
    }
}