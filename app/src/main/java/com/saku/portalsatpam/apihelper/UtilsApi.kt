package com.saku.portalsatpam.apihelper

import android.content.Context

class UtilsApi()  {
    val BASE_URL_API = "https://api.simkug.com/api/portal/"

    fun getAPIService(context: Context): ApiService? {
        return ApiBuilder().getClient(BASE_URL_API,context)?.create(ApiService::class.java)
    }
}