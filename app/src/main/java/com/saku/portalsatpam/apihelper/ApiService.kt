package com.saku.portalsatpam.apihelper

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface ApiService {
    @FormUrlEncoded
    @POST("login")
    fun login(
        @Field("qrcode") nik: String?
//        @Field("password") password: String?
    ): Call<LoginResponse>

    @GET("paket")
    fun paket(
    ): Call<ResponseBody>

    @FormUrlEncoded
    @PUT("paket")
    fun paketAccepted(
        @Field("no_paket") id_paket: String?
    ): Call<ResponseBody>

    @Multipart
    @POST("paket")
    fun addPaket(
        @Part foto: MultipartBody.Part,
        @Part("no_rumah") no_rumah: RequestBody?,
        @Part("blok") blok: RequestBody?,
        @Part("nama") nama: RequestBody?,
        @Part("nik") nik: RequestBody?
    ): Call<ResponseBody>

    @GET("tamu-masuk")
    fun dataTamu(
    ): Call<ResponseBody>

    @Multipart
    @POST("tamu-masuk")
    fun tamuMasuk(
        @Part ktp: MultipartBody.Part,
        @Query ("keperluan") keperluan:String,
        @Part ("nama[]") nama: MutableList<RequestBody>,
        @Part ("nik[]") nik: MutableList<RequestBody>,
        @Part ("no_rumah[]") no_rumah: MutableList<RequestBody>,
        @Part ("blok[]") blok: MutableList<RequestBody>
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("tamu-keluar")
    fun tamuKeluar(
        @Field("qrcode") qrcode:String
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("tamu-keluar")
    fun tamuKeluarSatpam(
        @Field("qrcode") qrcode:String,
        @Field("id_tamu") tamu:String
    ): Call<ResponseBody>

    @GET("satpam")
    fun satpam(
    ): Call<ResponseBody>

    @GET("profile")
    fun satpamAktif(
    ): Call<ResponseBody>

    @GET("logout")
    fun logout(
    ): Call<ResponseBody>

    @GET("warga")
    fun warga(
        @Query("no_rumah") no_rumah:String
    ): Call<ResponseBody>

    @GET("perlu")
    fun perlu(
    ): Call<ResponseBody>

    @GET("rumah")
    fun rumah(
    ): Call<ResponseBody>

    @FormUrlEncoded
    @POST("send-notif-fcm")
    fun sendNotif(
        @Field("token[]") token: String,
        @Field("data[title]") title: String,
        @Field("data[message]") message: String
    ):Call<ResponseBody>

}