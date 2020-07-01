package com.saku.portalsatpam.models

import com.google.gson.annotations.SerializedName

data class ModelPaket(
//	@field:SerializedName("no")
//	val no: String? = null,

	@field:SerializedName("id_paket")
	val idPaket: String? = null,
	@field:SerializedName("kode_rumah")
	val kode_rumah: String? = null,
	@field:SerializedName("nama")
	val penghuni: String? = null,
	@field:SerializedName("nik")
	val nik: String? = null,
	@field:SerializedName("status_ambil")
	val stsAmbil: String? = null,
	@field:SerializedName("no_paket")
	val noPaket: String? = null,
	@field:SerializedName("foto")
	val foto: String? = null,
	@field:SerializedName("tanggal")
	val tanggal: String? = null,
	@field:SerializedName("id_satpam")
	val idSatpam: String? = null,
	@field:SerializedName("nama_satpam")
	val namaSatpam: String? = null
	)