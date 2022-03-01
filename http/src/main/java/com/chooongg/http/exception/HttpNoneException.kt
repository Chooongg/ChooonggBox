package com.chooongg.http.exception

/**
 * 无异常情况，请求过程中使用此异常可以不回调 onError 方法
 */
class HttpNoneException : Exception()