package com.saku.portalsatpam

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saku.portalsatpam.adapter.PaketAdapter
import com.saku.portalsatpam.apihelper.UtilsApi
import com.saku.portalsatpam.fragments.FragmentEmpty
import com.saku.portalsatpam.fragments.FragmentPaket
import com.saku.portalsatpam.models.ModelPaket
import kotlinx.android.synthetic.main.activity_paket2.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class Paket2Activity : AppCompatActivity() {

    private lateinit var myadapter : PaketAdapter
    var preferences  = Preferences()
    var idPaket : String? = "-"
    var tujuan : String? = null
    var penghuni : String? = null
    var image : String? = null
    var idSatpam : String? = null
    var namaSatpam : String? = null

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if(intent.hasExtra("id_satpam")){
                idPaket = intent.getStringExtra("id_paket")
                tujuan = intent.getStringExtra("tujuan")
                penghuni = intent.getStringExtra("penghuni")
                image = intent.getStringExtra("image")
                idSatpam = intent.getStringExtra("id_satpam")
                namaSatpam = intent.getStringExtra("nama_satpam")
                supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in,0).replace(R.id.frame_layout,
                    FragmentPaket()
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
            .registerReceiver(mMessageReceiver, IntentFilter("paket_trigger"))
    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mMessageReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket2)
        preferences.setPreferences(this)
        emptyFragment()
        back.setOnClickListener { super.onBackPressed() }
        search.setOnClickListener { val intent = Intent(this@Paket2Activity, PaketSearchActivity::class.java)
            intent.putExtra("params","paket")
            startActivity(intent) }
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        initData()
        filter_text.setOnClickListener { resetFilter() }
        btn_tambah.setOnClickListener {
            val intent = Intent(this@Paket2Activity, TambahPaketActivity::class.java)
            startActivity(intent) }
        refreshLayout.setOnRefreshListener { initData() }
    }

    fun emptyFragment(){
        supportFragmentManager.beginTransaction().setCustomAnimations(R.anim.fade_in,0).replace(R.id.frame_layout,
            FragmentEmpty("paket")
        ).commitAllowingStateLoss()
        initData()
    }

    fun resetFilter(){
        myadapter.getFilter()?.filter("")
        filter_text.visibility = View.GONE
    }

    fun initData() {
        if (idPaket.equals("-")){
            refreshLayout.isRefreshing = true
        }
        val apiservice = UtilsApi().getAPIService(this@Paket2Activity)
        apiservice?.paket()?.enqueue(object : Callback<ResponseBody?> {
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
                                TypeToken<ArrayList<ModelPaket?>?>() {}.type
                            val datapaket: ArrayList<ModelPaket> =
                                gson.fromJson(obj.optString("data"), type)
                            myadapter =
                                PaketAdapter(
                                    datapaket,idPaket!!)
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
                        Toast.makeText(this@Paket2Activity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        refreshLayout.isRefreshing = false
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@Paket2Activity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false
                } else if(response.code() == 401){
                    val intent = Intent(this@Paket2Activity, LoginActivity::class.java)
                    startActivity(intent)
                    preferences.preferencesLogout()
                    finish()
                    Toast.makeText(this@Paket2Activity, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@Paket2Activity, "Unauthorized", Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false
                } else if(response.code() == 404){
                    Toast.makeText(this@Paket2Activity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@Paket2Activity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
                refreshLayout.isRefreshing = false
            }
        })
    }
}
