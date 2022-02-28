package com.chooongg.http.ext

import okhttp3.HttpUrl
import okhttp3.Request
import okhttp3.ResponseBody
import okhttp3.internal.closeQuietly
import java.io.File
import java.io.RandomAccessFile

typealias HttpProgressListener = (contentLength: Long, progress: Int) -> Unit

/** 从请求实例中获取用[retrofit2.http.Tag]注解标记的参数 */
internal inline fun <reified T> Request.getTag(): T? {
    return tag(T::class.java)
}

/**
 * 使用RandomAccessFile将数据写入到文件
 */
fun ResponseBody.writeToFile(file: File) {
    //使用RandomAccessFile将数据写入到文件
    val outputFile = RandomAccessFile(file, "rws")
    //执行写入操作
    val buffer = ByteArray(DEFAULT_BUFFER_SIZE)
    var readLength = source().read(buffer)
    while (readLength > 0) {
        outputFile.write(buffer, 0, readLength)
        readLength = source().read(buffer)
    }
    outputFile.closeQuietly()
    closeQuietly()
}

/**
 * 从下载地址中获取文件名称
 * 用下载地址的最后一位的pathSegment
 * */
internal fun HttpUrl.getFileName(): String {
    return pathSegments.last()
}