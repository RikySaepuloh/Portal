package com.saku.portalsatpam

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saku.portalsatpam.adapter.Tamu2Adapter
import com.saku.portalsatpam.apihelper.UtilsApi
import com.saku.portalsatpam.fragments.FragmentEmpty
import com.saku.portalsatpam.fragments.FragmentTamu
import com.saku.portalsatpam.models.ModelTamu
import kotlinx.android.synthetic.main.activity_paket2.back
import kotlinx.android.synthetic.main.activity_paket2.empty_view
import kotlinx.android.synthetic.main.activity_paket2.filter_text
import kotlinx.android.synthetic.main.activity_paket2.recyclerview
import kotlinx.android.synthetic.main.activity_paket2.refreshLayout
import kotlinx.android.synthetic.main.activity_paket2.search
import kotlinx.android.synthetic.main.activity_tamu.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type
import java.util.*

class TamuActivity : AppCompatActivity() {

    private lateinit var myadapter : Tamu2Adapter
    var preferences  = Preferences()
    var idTamu : String? = "-"
    var tujuan : String? = null
    var nama : String? = null
    var ktp : String? = null
    var nik : String? = null
    var keperluan : String? = null
    var tglMasuk : String? = null

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if(intent.hasExtra("id_tamu")){
                idTamu = intent.getStringExtra("id_tamu")
                tujuan = intent.getStringExtra("tujuan")
                nama = intent.getStringExtra("nama")
                ktp = intent.getStringExtra("ktp")
                nik = intent.getStringExtra("nik")
                keperluan = intent.getStringExtra("keperluan")
                tglMasuk = intent.getStringExtra("tanggal_masuk")
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in,0).replace(R.id.frame_layout,
                    FragmentTamu()
                ).commitAllowingStateLoss()
            }
            if(intent.hasExtra("search")){
                val search = intent.getStringExtra("search")
                filter_text.text = search
                filter_text.visibility = View.VISIBLE
                myadapter.getFilter()?.filter(search)
            }
        }
    }


    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mMessageReceiver, IntentFilter("tamu_trigger"))
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mMessageReceiver)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tamu)
        preferences.setPreferences(this)
        emptyFragment()
        back.setOnClickListener { super.onBackPressed() }
        var ascending = true
        sort_by.setOnClickListener {
            if(ascending){
                myadapter.sortAscending()
                ascending=false
            }else{
                myadapter.sortDescending()
                ascending=true
            }
        }
        search.setOnClickListener { val intent = Intent(this@TamuActivity, PaketSearchActivity::class.java)
            intent.putExtra("params","tamu")
            startActivity(intent) }
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        initData()
        filter_text.setOnClickListener { resetFilter() }
        refreshLayout.setOnRefreshListener { initData() }
    }


    fun emptyFragment(){
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in,0).replace(R.id.frame_layout,
            FragmentEmpty("tamu")
        ).commitAllowingStateLoss()
        initData()
    }

    fun resetFilter(){
        myadapter.getFilter()?.filter("")
        filter_text.visibility = View.GONE
    }

    fun initData() {
        if (idTamu.equals("-")){
            refreshLayout.isRefreshing = true
        }
        val apiservice = UtilsApi().getAPIService(this@TamuActivity)
        apiservice?.dataTamu()?.enqueue(object : Callback<ResponseBody?> {
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
                                TypeToken<ArrayList<ModelTamu?>?>() {}.type
                            val datapaket: ArrayList<ModelTamu> =
                                gson.fromJson(obj.optString("data"), type)
                            myadapter =
                                Tamu2Adapter(
                                    datapaket)
                            myadapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
                                override fun onChanged() {
                                    super.onChanged()
                                    checkEmpty()
                                }

                                override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                                    super.onItemRangeInserted(positionStart, itemCount)
                                    checkEmpty()
                                }

                                override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
                                    super.onItemRangeRemoved(positionStart, itemCount)
                                    checkEmpty()
                                }

                                fun checkEmpty() {
                                    if(myadapter.itemCount==0){
                                        empty_view.visibility= View.VISIBLE
                                        recyclerview.visibility= View.GONE
                                    }else{
                                        empty_view.visibility= View.GONE
                                        recyclerview.visibility= View.VISIBLE
                                    }

                                }
                            })
                            recyclerview.adapter = myadapter
                            refreshLayout.isRefreshing = false
                            empty_view.visibility= View.GONE
                        } catch (e: Exception) {
                            empty_view.visibility= View.VISIBLE
                            recyclerview.visibility= View.GONE
                            refreshLayout.isRefreshing = false
                        }
                    }else{
                        Toast.makeText(this@TamuActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        refreshLayout.isRefreshing = false
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@TamuActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false
                } else if(response.code() == 401){
                    val intent = Intent(this@TamuActivity, LoginActivity::class.java)
                    startActivity(intent)
                    preferences.preferencesLogout()
                    finish()
                    Toast.makeText(this@TamuActivity, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@TamuActivity, "Unauthorized", Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false
                } else if(response.code() == 404){
                    Toast.makeText(this@TamuActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@TamuActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
                refreshLayout.isRefreshing = false
            }
        })
    }
}
