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