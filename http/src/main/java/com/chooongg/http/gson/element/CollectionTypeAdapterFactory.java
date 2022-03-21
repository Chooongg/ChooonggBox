package com.chooongg.http.gson.element;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.Collection;

public class CollectionTypeAdapterFactory implements TypeAdapterFactory {

    private final ConstructorConstructor mConstructorConstructor;

    public CollectionTypeAdapterFactory(ConstructorConstructor constructor) {
        mConstructorConstructor = constructor;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();
        Class<? super T> rawType = typeToken.getRawType();
        // 判断是否包含这种类型
        if (ReflectiveTypeUtils.containsClass(rawType)) {
            return null;
        }
        if (typeToken.getType() instanceof GenericArrayType ||
                typeToken.getType() instanceof Class &&
                ((Class<?>) typeToken.getType()).isArray()) {
            return null;
        }

        if (!Collection.class.isAssignableFrom(rawType)) {
            return null;
        }

        Type elementType = $Gson$Types.getCollectionElementType(type, rawType);
        TypeAdapter<?> elementTypeAdapter = gson.getAdapter(TypeToken.get(elementType));
        ObjectConstructor<T> constructor = mConstructorConstructor.get(typeToken);

        // create() doesn't define a type parameter
        @SuppressWarnings({"unchecked", "rawtypes"})
        TypeAdapter<T> result = new CollectionTypeAdapter(gson, elementType, elementTypeAdapter, constructor);
        return result;
    }
}