package com.saku.portalsatpam.fragments

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saku.portalsatpam.*
import com.saku.portalsatpam.apihelper.UtilsApi
import com.saku.portalsatpam.models.ModelPaket
import com.saku.portalsatpam.models.ModelRumah
import kotlinx.android.synthetic.main.fragment_tujuan.view.*
import kotlinx.android.synthetic.main.fragment_tujuan.view.delete
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_0
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_1
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_2
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_3
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_4
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_5
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_6
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_7
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_8
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_9
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_a
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_b
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_c
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_d
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_e
import kotlinx.android.synthetic.main.fragment_tujuan.view.menu_slash
import kotlinx.android.synthetic.main.fragment_tujuan.view.bs_tujuan
import okhttp3.ResponseBody
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class FragmentTujuan : Fragment() {

    private lateinit var myview: View
    private lateinit var dataPasser: DataPasserKeperluan
    private var preferences  = Preferences()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        myview = inflater.inflate(R.layout.fragment_tujuan, container, false)
        context?.let { preferences.setPreferences(it) }
        return myview
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myview.bs_tujuan.setOnTouchListener { v, event ->
            v.onTouchEvent(event)
            val inputMethod: InputMethodManager = v.context
                .getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
//            if (inputMethod != null) {
            inputMethod.hideSoftInputFromWindow(v.windowToken, 0)
//            }
            true
        }
        if((activity as NeinActivity).tujuan!="-"){
            myview.bs_tujuan.setText((activity as NeinActivity).tujuan)
        }
        myview.delete.setOnClickListener {
            myview.bs_tujuan.text.clear()
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_0.setOnClickListener {
            myview.bs_tujuan.text.append("0")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_1.setOnClickListener {
            myview.bs_tujuan.text.append("1")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_2.setOnClickListener {
            myview.bs_tujuan.text.append("2")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_3.setOnClickListener {
            myview.bs_tujuan.text.append("3")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_4.setOnClickListener {
            myview.bs_tujuan.text.append("4")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_5.setOnClickListener {
            myview.bs_tujuan.text.append("5")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_6.setOnClickListener {
            myview.bs_tujuan.text.append("6")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_7.setOnClickListener {
            myview.bs_tujuan.text.append("7")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_8.setOnClickListener {
            myview.bs_tujuan.text.append("8")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_9.setOnClickListener {
            myview.bs_tujuan.text.append("9")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_slash.setOnClickListener {
            myview.bs_tujuan.text.append("-")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_a.setOnClickListener {
            myview.bs_tujuan.text.append("A")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_b.setOnClickListener {
            myview.bs_tujuan.text.append("B")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_c.setOnClickListener {
            myview.bs_tujuan.text.append("C")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_d.setOnClickListener {
            myview.bs_tujuan.text.append("D")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.menu_e.setOnClickListener {
            myview.bs_tujuan.text.append("E")
            context?.vibrate(longArrayOf(0, 150))
        }
        myview.selanjutnya.setOnClickListener {
            if(myview.bs_tujuan.text.toString()=="" || myview.bs_tujuan.text.toString().isEmpty() || myview.bs_tujuan.text.toString().length<4){
                Toast.makeText(context,"Masukkan tujuan terlebih dahulu!",Toast.LENGTH_LONG).show()
            }else{
                initData()
            }
        }
    }



    private fun initData() {
        myview.warning.visibility = View.INVISIBLE
        val apiservice = context?.let { UtilsApi().getAPIService(it) }
        apiservice?.rumah()?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        try {
                            val obj = JSONObject(response.body()!!.string())
                            val gson = Gson()
                            val type: Type = object :
                                TypeToken<ArrayList<ModelRumah?>?>() {}.type
                            val datapaket: ArrayList<ModelRumah> =
                                gson.fromJson(obj.optString("data"), type)
                            for(i in 0 until datapaket.size){
//                                Log.e(TAG,datapaket[i].kodeRumah)
                                if (datapaket[i].kodeRumah.equals(myview.bs_tujuan.text.toString())){
                                    myview.warning.visibility = View.INVISIBLE
                                    passData(myview.bs_tujuan.text.toString())
                                    (activity as NeinActivity?)?.changeFragment(FragmentPenghuni())
                                    break
                                }else{
                                    myview.warning.visibility = View.VISIBLE
                                }
                            }
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


    override fun onAttach(context: Context) {
        super.onAttach(context)
        dataPasser = context as DataPasserKeperluan
    }

    private fun passData(data: String){
        dataPasser.onDataPasserKeperluan(data,"tujuan")
    }

}
