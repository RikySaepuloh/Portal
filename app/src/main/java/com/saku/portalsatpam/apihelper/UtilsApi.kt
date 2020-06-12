package com.saku.approval_2.api_service

import android.content.Context
import com.saku.portalsatpam.apihelper.ApiBuilder
import com.saku.portalsatpam.apihelper.ApiService

class UtilsApi()  {
    val BASE_URL_API = "http://api.simkug.com/api/"

    fun getAPIService(context: Context): ApiService? {
        return ApiBuilder().getClient(BASE_URL_API,context)?.create(ApiService::class.java)
    }
}