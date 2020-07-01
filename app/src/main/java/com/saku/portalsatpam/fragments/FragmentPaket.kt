package com.saku.portalsatpam.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saku.portalsatpam.*
import com.saku.portalsatpam.adapter.PaketAdapter
import com.saku.portalsatpam.apihelper.UtilsApi
import com.saku.portalsatpam.models.ModelPaket
import kotlinx.android.synthetic.main.fragment_paket.*
import kotlinx.android.synthetic.main.fragment_paket.view.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

/**
 * A simple [Fragment] subclass.
 */
class FragmentPaket : Fragment() {
    private lateinit var myview: View
    var preferences  = Preferences()
    var idpaket : String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        myview = inflater.inflate(R.layout.fragment_paket, container, false)
        preferences.setPreferences(context!!)
        val image = (activity as Paket2Activity).image.toString()
        idpaket = (activity as Paket2Activity).idPaket.toString()
        val namaSatpam = (activity as Paket2Activity).namaSatpam.toString()
        myview.id_paket.text = idpaket
        myview.penerima.text = "Penerima : $namaSatpam"
        myview.terima.setOnClickListener {
            accept(idpaket!!) }
        Glide.with(this).load(image).into(myview.paket_img)
        return myview
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
                            (activity as Paket2Activity).idPaket = "-"
                            (activity as Paket2Activity).emptyFragment()
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
