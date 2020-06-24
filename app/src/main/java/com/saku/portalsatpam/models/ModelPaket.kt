package com.saku.portalsatpam.models

import com.google.gson.annotations.SerializedName

data class ModelPaket(
//	@field:SerializedName("no")
//	val no: String? = null,

	@field:SerializedName("id_paket")
	val idPaket: String? = null,
	@field:SerializedName("no_rumah")
	val noRumah: String? = null,
	@field:SerializedName("nama")
	val penghuni: String? = null,
	@field:SerializedName("nik")
	val nik: String? = null,
	@field:SerializedName("status_ambil")
	val stsAmbil: String? = null,
	@field:SerializedName("no_paket")
	val noPaket: String? = null
	)