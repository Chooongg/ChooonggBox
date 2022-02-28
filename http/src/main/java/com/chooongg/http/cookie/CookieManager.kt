package com.chooongg.http.cookie

import com.chooongg.ext.APPLICATION
import okhttp3.Cookie
import okhttp3.CookieJar
import okhttp3.HttpUrl


object CookieManager : CookieJar {

    private val cookieStore: PersistentCookieStore = PersistentCookieStore(APPLICATION)

    override fun saveFromResponse(url: HttpUrl, cookies: List<Cookie>) {
        if (cookies.isNotEmpty()) {
            for (item in cookies) {
                cookieStore.add(url, item)
            }
        }
    }

    override fun loadForRequest(url: HttpUrl): List<Cookie> {
        return cookieStore[url]
    }

    fun removeAll() {
        cookieStore.removeAll()
    }
}