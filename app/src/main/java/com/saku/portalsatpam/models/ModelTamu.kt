package com.saku.portalsatpam.models

import com.google.gson.annotations.SerializedName

data class ModelTamu(

	@field:SerializedName("tgljam_in")
	val tgljamIn: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("keperluan")
	val keperluan: String? = null,

	@field:SerializedName("id_tamu")
	val idTamu: String? = null,

	@field:SerializedName("tgljam_out")
	val tgljamOut: String? = null,

	@field:SerializedName("selisih")
	val selisih: String? = null,

	@field:SerializedName("no_tamu")
	val noTamu: String? = null,

	@field:SerializedName("rumah")
	val rumah: String? = null
)