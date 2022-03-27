package com.chooongg.http.download

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Streaming
import retrofit2.http.Url

interface DownloadAPI {
    @GET
    @Streaming
    fun download(@Url url: String): Call<ResponseBody>
}