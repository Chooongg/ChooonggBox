package com.chooongg.http.body

import com.chooongg.http.ext.HttpProgressListener
import okhttp3.MediaType
import okhttp3.ResponseBody
import okio.Buffer
import okio.BufferedSource
import okio.ForwardingSource
import okio.buffer

class ProgressResponseBody(
    private val delegate: ResponseBody,
    private val callback: HttpProgressListener
) : ResponseBody() {

    /**
     * 当前已处理的数据大小
     */
    private var currentLength = 0L

    private var contentLength = contentLength()

    private val source = object : ForwardingSource(delegate.source()) {
        override fun read(sink: Buffer, byteCount: Long): Long {
            val bytesRead = super.read(sink, byteCount)
            currentLength += if (bytesRead != -1L) bytesRead else 0
            callback.invoke(contentLength, ((currentLength / 100) / contentLength).toInt())
            return bytesRead
        }
    }.buffer()

    override fun contentLength(): Long = delegate.contentLength()

    override fun contentType(): MediaType? = delegate.contentType()

    override fun source(): BufferedSource = source
}