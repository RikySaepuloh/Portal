package com.saku.portalsatpam.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.saku.portalsatpam.*
import com.saku.portalsatpam.apihelper.UtilsApi
import kotlinx.android.synthetic.main.fragment_paket.view.*
import kotlinx.android.synthetic.main.fragment_paket.view.id_paket
import kotlinx.android.synthetic.main.fragment_paket.view.penerima
import kotlinx.android.synthetic.main.fragment_tamu.view.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * A simple [Fragment] subclass.
 */
class FragmentTamu : Fragment() {
    private lateinit var myview: View
    var preferences  = Preferences()
    var idtamu : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_tamu, container, false)
        preferences.setPreferences(context!!)

        return myview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val ktp = (activity as TamuActivity).ktp.toString()
        idtamu = (activity as TamuActivity).idTamu.toString()
        val tglMasuk = (activity as TamuActivity).tglMasuk.toString()
        myview.id_paket.text = idtamu
        myview.penerima.text = "Tanggal Masuk $tglMasuk"
//        (activity as TamuActivity).initData()
        myview.keluar.setOnClickListener {
            val intent = Intent(context, LoginScanActivity::class.java)
            intent.putExtra("params","tamu")
            intent.putExtra("id_tamu",idtamu)
            activity?.startActivity(intent)
//            accept(idtamu!!)
        }
        Glide.with(this).load(ktp).into(myview.ktp_img)
    }

    fun accept(id_paket : String){
        val apiservice = UtilsApi().getAPIService(context!!)
        apiservice?.paketAccepted(id_paket)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        try {
                            val obj = JSONObject(response.body()!!.string())
                            Toast.makeText(context, obj.optString("message"), Toast.LENGTH_SHORT).show()
                            (activity as TamuActivity).idTamu = "-"
                            (activity as TamuActivity).emptyFragment()
//                            val gson = Gson()
//                            val type: Type = object :
//                                TypeToken<ArrayList<ModelPaket?>?>() {}.type
//                            val datapaket: ArrayList<ModelPaket> =
//                                gson.fromJson(obj.optString("data"), type)
                            
                        } catch (e: Exception) {
                            
                        }
                    }else{
                        Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(context, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 401){
                    val intent = Intent(context, LoginActivity::class.java)
                    startActivity(intent)
                    preferences.preferencesLogout()
                    activity?.finish()
                    Toast.makeText(context, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(context, "Unauthorized", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 404){
                    Toast.makeText(context, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(context, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
