package com.saku.portalsatpam.models

import com.google.gson.annotations.SerializedName

data class ModelPenghuni(
	@field:SerializedName("kode_pp")
	val kodePP: String? = null,
	@field:SerializedName("nik")
	val nik: String? = null,
	@field:SerializedName("kode_blok")
	val kodeBlok: String? = null,
	@field:SerializedName("no_rumah")
	val noRumah: String? = null,
	@field:SerializedName("no_urut")
	val noUrut: String? = null,
	@field:SerializedName("nama")
	val nama: String? = null,
	@field:SerializedName("no_hp")
	val noHp: String? = null,
	@field:SerializedName("foto")
	val foto: String? = null,
	@field:SerializedName("kode_jk")
	val kodeJk: String? = null,
	@field:SerializedName("kode_agama")
	val kodeAgama: String? = null

	)