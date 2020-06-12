package com.saku.portalsatpam.apihelper

import com.saku.approval_2.api_service.LoginResponse
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("approval/login")
    fun login(
        @Field("nik") nik: String?,
        @Field("password") password: String?
    ): Call<LoginResponse>

    @FormUrlEncoded
    @POST("approval/app")
    fun approval(
        @Field("modul") modul: String?,
        @Field("status") status: String?,
        @Field("no_aju") no_aju: String?,
        @Field("keterangan") keterangan: String?
    ): Call<ResponseBody>

    @GET("approval/aju")
    fun daftarpengajuan(
    ): Call<ResponseBody>

    @GET("approval/aju_history/all")
    fun daftarhistoryall(
    ): Call<ResponseBody>

    @GET("approval/aju_history/approve")
    fun daftarhistoryapprove(
    ): Call<ResponseBody>

    @GET("approval/aju_history/reject")
    fun daftarhistoryreject(
    ): Call<ResponseBody>

    @GET("approval/ajudet/{no_aju}")
    fun detailpengajuan(
        @Path("no_aju") no_aju: String?
    ): Call<ResponseBody>

    @GET("approval/ajudet_dok/{no_aju}")
    fun detaildokumen(
        @Path("no_aju") no_aju: String?
    ): Call<ResponseBody>

    @GET("approval/ajudet_history/{no_aju}")
    fun detailriwayat(
        @Path("no_aju") no_aju: String?
    ): Call<ResponseBody>

    @GET("approval/ajudet_approval/{no_aju}")
    fun detailflow(
        @Path("no_aju") no_aju: String?
    ): Call<ResponseBody>

    @GET("approval/ajurek/{no_aju}")
    fun detailrekening(
        @Path("no_aju") no_aju: String?
    ): Call<ResponseBody>

    @GET("approval/ajujurnal/{no_aju}")
    fun detailjurnal(
        @Path("no_aju") no_aju: String?
    ): Call<ResponseBody>

    @GET("approval/profile")
    fun user(
    ): Call<ResponseBody>

    @GET
    fun downloadFile(@Url fileUrl: String?): Call<ResponseBody?>?
}