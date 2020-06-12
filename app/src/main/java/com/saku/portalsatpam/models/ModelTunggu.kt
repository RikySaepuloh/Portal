package com.saku.portalsatpam.models

import com.google.gson.annotations.SerializedName

data class ModelTunggu(
//	@field:SerializedName("no")
//	val no: String? = null,

	@field:SerializedName("tujuan")
	val tujuan: String? = null,

	@field:SerializedName("penghuni")
	val penghuni: String? = null,

	@field:SerializedName("keperluan")
	val keperluan: String? = null
)