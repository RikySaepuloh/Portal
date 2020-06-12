package com.saku.portalsatpam.models

import com.google.gson.annotations.SerializedName

data class ModelPaket(
//	@field:SerializedName("no")
//	val no: String? = null,

	@field:SerializedName("kode")
	val kode: String? = null,
	@field:SerializedName("tujuan")
	val tujuan: String? = null,

	@field:SerializedName("penghuni")
	val penghuni: String? = null

	)