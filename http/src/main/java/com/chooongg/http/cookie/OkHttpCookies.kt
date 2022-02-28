package com.chooongg.http.cookie

import okhttp3.Cookie
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

class OkHttpCookies(private val cookies: Cookie?) : Serializable {

    private var clientCookies: Cookie? = null

    fun getCookies(): Cookie? {
        var bestCookies = cookies
        if (clientCookies != null) {
            bestCookies = clientCookies
        }
        return bestCookies
    }

    @Throws(IOException::class)
    private fun writeObject(out: ObjectOutputStream) {
        out.writeObject(cookies!!.name)
        out.writeObject(cookies.value)
        out.writeLong(cookies.expiresAt)
        out.writeObject(cookies.domain)
        out.writeObject(cookies.path)
        out.writeBoolean(cookies.secure)
        out.writeBoolean(cookies.httpOnly)
        out.writeBoolean(cookies.hostOnly)
        out.writeBoolean(cookies.persistent)
    }

    @Throws(IOException::class, ClassNotFoundException::class)
    private fun readObject(`in`: ObjectInputStream) {
        val name = `in`.readObject() as String
        val value = `in`.readObject() as String
        val expiresAt: Long = `in`.readLong()
        val domain = `in`.readObject() as String
        val path = `in`.readObject() as String
        val secure: Boolean = `in`.readBoolean()
        val httpOnly: Boolean = `in`.readBoolean()
        val hostOnly: Boolean = `in`.readBoolean()
        val builder = Cookie.Builder()
            .name(name)
            .value(value)
            .expiresAt(expiresAt)
        if (hostOnly) builder.hostOnlyDomain(domain) else builder.domain(domain)
        if (hostOnly) builder.hostOnlyDomain(domain) else builder.domain(domain)
        builder.path(path)
        if (secure) builder.secure()
        if (httpOnly) builder.httpOnly()
        clientCookies = builder.build()
    }
}