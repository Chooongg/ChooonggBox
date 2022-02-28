package com.chooongg.ext

import java.lang.reflect.ParameterizedType

/**
 * 获取类的第 N 项泛型
 * @param index 泛型指数
 * @return if Test<T,V>
 *     index = 0 return T
 *     index = 1 return V
 */
fun Class<*>.getTClass(index: Int = 0) =
    (genericSuperclass as ParameterizedType).actualTypeArguments[index] as Class<*>