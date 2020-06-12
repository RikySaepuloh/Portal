package com.saku.portalsatpam.models

import com.google.gson.annotations.SerializedName

data class ModelTamu(
//	@field:SerializedName("no")
//	val no: String? = null,

	@field:SerializedName("kode")
	val kode: String? = null,
	@field:SerializedName("tujuan")
	val tujuan: String? = null,


	@field:SerializedName("keperluan")
	val keperluan: String? = null,

	@field:SerializedName("durasi")
	val durasi: String? = null
)