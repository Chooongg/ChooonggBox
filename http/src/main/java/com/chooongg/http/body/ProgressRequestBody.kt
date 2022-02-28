package com.chooongg.http.body

import com.chooongg.http.ext.HttpProgressListener
import okhttp3.MediaType
import okhttp3.RequestBody
import okio.Buffer
import okio.BufferedSink
import okio.ForwardingSink
import okio.buffer

class ProgressRequestBody(
    private val delegate: RequestBody,
    private val callback: HttpProgressListener
) : RequestBody() {

    private var currentLength = 0L

    private var contentLength = contentLength()

    override fun isDuplex(): Boolean = delegate.isDuplex()

    override fun isOneShot(): Boolean = delegate.isOneShot()

    override fun contentLength(): Long = delegate.contentLength()

    override fun contentType(): MediaType? = delegate.contentType()

    override fun writeTo(sink: BufferedSink) {
        object : ForwardingSink(sink) {
            override fun write(source: Buffer, byteCount: Long) {
                super.write(source, byteCount)
                currentLength += byteCount
                callback.invoke(contentLength, ((currentLength / 100) / contentLength).toInt())
            }
        }.buffer().let {
            delegate.writeTo(it)
            it.close()
        }
    }

    fun setExistLength(existLength: Long) {
        currentLength = existLength
        contentLength += existLength
    }
}