package com.chooongg.http.exception

import android.net.ParseException
import android.util.MalformedJsonException
import com.chooongg.utils.NetworkUtils
import com.google.gson.JsonIOException
import com.google.gson.JsonParseException
import com.google.gson.JsonSyntaxException
import org.json.JSONException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.net.ssl.SSLHandshakeException

class HttpException : RuntimeException {

    object Converter {
        private var converter: HttpErrorConverter = DefaultErrorConverter()

        fun convert(type: Type): String = converter.convert(type)

        /**
         * 修改转换器
         */
        fun changeConverter(converter: HttpErrorConverter) {
            Converter.converter = converter
        }
    }

    var type: Type
        private set(value) {
            field = value
            if (code == "") code = type.value.toString()
            if (messageCopy == "") messageCopy = Converter.convert(type)

        }
    var code: String = ""
        private set

    private var messageCopy: String = ""

    constructor() : super() {
        this.type = Type.UN_KNOWN
    }

    constructor(type: Type) : super() {
        this.type = type
    }

    constructor(code: Int) : super() {
        var tempType = Type.UN_KNOWN
        for (i in Type.values().indices) {
            if (Type.values()[i].value == code) {
                tempType = Type.values()[i]
                break
            }
        }
        this.type = tempType
    }

    constructor(code: String, message: String) : super(message) {
        this.code = code
        this.messageCopy = message
        this.type = Type.CUSTOM
    }

    constructor(message: String) : super(message) {
        this.messageCopy = message
        this.type = Type.CUSTOM
    }

    constructor(message: String, cause: Throwable) : super(message, cause) {
        this.messageCopy = message
        this.type = Type.CUSTOM
    }

    constructor(e: Throwable) : super(e.toString(), e) {
        if (e is HttpException) {
            this.type = e.type
            this.code = e.code
            this.messageCopy = e.messageCopy
        } else {
            this.type = when {
                e is NullPointerException -> Type.EMPTY
                !NetworkUtils.isNetworkConnected() -> Type.NETWORK
                e is ConnectException || e is UnknownHostException -> Type.CONNECT
                e is SocketTimeoutException -> Type.TIMEOUT
                e is retrofit2.HttpException -> {
                    var tempType = Type.UN_KNOWN
                    for (i in Type.values().indices) {
                        if (Type.values()[i].value == e.code()) {
                            tempType = Type.values()[i]
                            break
                        }
                    }
                    if (tempType == Type.UN_KNOWN) {
                        this.code = e.code().toString()
                        this.messageCopy = e.message()
                    }
                    tempType
                }
                e is JSONException
                        || e is JsonIOException
                        || e is JsonParseException
                        || e is JsonSyntaxException
                        || e is ParseException
                        || e is NullPointerException
                        || e is MalformedJsonException -> Type.PARSE
                e is SSLHandshakeException -> Type.SSL
                else -> Type.UN_KNOWN
            }
        }
    }

    override val message: String
        get() = messageCopy

    enum class Type(val value: Int) {
        UN_KNOWN(-1),
        CUSTOM(-2),
        NETWORK(-3),
        TIMEOUT(-4),
        PARSE(-5),
        SSL(-6),
        EMPTY(-7),
        CONNECT(-8),
        HTTP302(302),
        HTTP400(400),
        HTTP401(401),
        HTTP403(403),
        HTTP404(404),
        HTTP405(405),
        HTTP406(406),
        HTTP407(407),
        HTTP408(408),
        HTTP409(409),
        HTTP410(410),
        HTTP411(411),
        HTTP412(412),
        HTTP413(413),
        HTTP414(414),
        HTTP415(415),
        HTTP416(416),
        HTTP417(417),
        HTTP500(500),
        HTTP501(501),
        HTTP502(502),
        HTTP503(503),
        HTTP504(504),
        HTTP505(505);
    }
}