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
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saku.portalsatpam.adapter.PaketAdapter
import com.saku.portalsatpam.apihelper.UtilsApi
import com.saku.portalsatpam.models.ModelPaket
import kotlinx.android.synthetic.main.activity_paket.*
import kotlinx.android.synthetic.main.bottom_sheet_paket.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


class PaketActivity : AppCompatActivity() {
    lateinit var behavior : BottomSheetBehavior<View>
//    private var data : ArrayList<ModelPaket> = ArrayList()
    private lateinit var myadapter : PaketAdapter
    var datakode :String? = null
    var datatujuan :String? = null
    var datapenghuni:String? = null
    var image:String? = null
    var preferences  = Preferences()

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if(intent.hasExtra("trigger")){
                datakode = ": "+intent.getStringExtra("kode")
                datatujuan = ": "+intent.getStringExtra("tujuan")
                datapenghuni = ": "+intent.getStringExtra("penghuni")
                image = intent.getStringExtra("image")
                bs_kode.text = datakode
                bs_tujuan.text = datatujuan
                bs_penghuni.text = datapenghuni
                Glide.with(this@PaketActivity).load(image).into(paket_img)
                if(behavior.state != BottomSheetBehavior.STATE_EXPANDED){
                    behavior.state = BottomSheetBehavior.STATE_EXPANDED
                    overlay.visibility = View.VISIBLE
                }else{
                    behavior.state = BottomSheetBehavior.STATE_COLLAPSED
                    overlay.visibility = View.GONE
                    bs_kode.text = ": -"
                    bs_tujuan.text = ": -"
                    bs_penghuni.text = ": -"
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mMessageReceiver, IntentFilter("bottom_sheet_trigger"))
    }

    override fun onPause() {
        super.onPause()
        LocalBroadcastManager.getInstance(this)
            .unregisterReceiver(mMessageReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_paket)
        preferences.setPreferences(this)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
//        data.add(ModelPaket("86","D4/7","Riky Saepuloh"))
//        data.add(ModelPaket("22","B5/1","Tatang Miharja"))
//        data.add(ModelPaket("19","A3/3","Laudya Chyntia Bella"))
//        data.add(ModelPaket("54","C5/2","Roni"))
//        myadapter = PaketAdapter(data)
//        recyclerview.adapter = myadapter
        initData()

//        search.setOnFocusChangeListener { v, hasFocus ->
//            if(hasFocus){
//                myadapter.add(data,clickable = false)
//            }else{
//                myadapter.add(data,clickable = true)
//            }
//        }


//        search.setOnQueryTextFocusChangeListener { v, hasFocus ->
//            if (hasFocus){
//                search.setOnSearchClickListener {myadapter.add(data,clickable = false) }
////                Toast.makeText(this,"Barang telah diterima",Toast.LENGTH_LONG).show()
//            }else{
//                search.setOnSearchClickListener {myadapter.add(data,clickable = true) }
////                Toast.makeText(this,"asasasa",Toast.LENGTH_LONG).show()
//            }
//        }

        refreshLayout.setOnRefreshListener {
            initData()
        }

        back.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            super.onBackPressed()
        }
        behavior = BottomSheetBehavior.from(bottom_sheet_tunggu)
        tambah.setOnClickListener {
            val intent = Intent(this,TambahPaketActivity::class.java)
            startActivity(intent)
//            if(behavior.state != BottomSheetBehavior.STATE_EXPANDED){
//                behavior.state = BottomSheetBehavior.STATE_EXPANDED
//            }else{
//                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
//            }
        }

        search.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            if(behavior.state != BottomSheetBehavior.STATE_EXPANDED){
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }else{
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }



        behavior.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                // React to state change
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        overlay.visibility = View.GONE
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> {
                    }
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // React to dragging events
            }
        })

        terima.setOnClickListener {
//            TODO : ADD FUNCTION IN HERE
            //vibrate(longArrayOf(0, 350))
            if(behavior.state != BottomSheetBehavior.STATE_EXPANDED){
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }else{
                behavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
            paketAccepted(datakode)
//            Toast.makeText(this,"Barang telah diterima",Toast.LENGTH_LONG).show()
        }
    }

    private fun initData() {
        refreshLayout.isRefreshing = true
        val apiservice = UtilsApi().getAPIService(this@PaketActivity)
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
//                            myadapter =
//                                PaketAdapter(
//                                    datapaket
//                                )
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
                                        empty_view.visibility=View.VISIBLE
                                        recyclerview.visibility=View.GONE
                                    }else{
                                        empty_view.visibility=View.GONE
                                        recyclerview.visibility=View.VISIBLE
                                    }

                                }
                            })
                            recyclerview.adapter = myadapter
                            refreshLayout.isRefreshing = false
                            empty_view.visibility=View.GONE
                        } catch (e: Exception) {
                            empty_view.visibility=View.VISIBLE
                            recyclerview.visibility=View.GONE
                            refreshLayout.isRefreshing = false
                        }
                    }else{
                        Toast.makeText(this@PaketActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        refreshLayout.isRefreshing = false
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@PaketActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false
                } else if(response.code() == 401){
                    val intent = Intent(this@PaketActivity, LoginActivity::class.java)
                    startActivity(intent)
                    preferences.preferencesLogout()
                    finish()
                    Toast.makeText(this@PaketActivity, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@PaketActivity, "Unauthorized", Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false
                } else if(response.code() == 404){
                    Toast.makeText(this@PaketActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@PaketActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
                refreshLayout.isRefreshing = false
            }
        })
    }

    private fun paketAccepted(id_paket:String?) {
        val apiservice = UtilsApi().getAPIService(this@PaketActivity)
        apiservice?.paketAccepted(id_paket)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        try {
                            val obj = JSONObject(response.body()!!.string())
                            try {
                                val message = obj.optString("message")
//                                Toast.makeText(this@PaketActivity, message, Toast.LENGTH_SHORT).show()
                            } catch (e: Exception) {
                            }

                        } catch (e: Exception) {

                        }
                    }else{
                        Toast.makeText(this@PaketActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@PaketActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 401){
                    val intent = Intent(this@PaketActivity, LoginActivity::class.java)
                    startActivity(intent)
                    preferences.preferencesLogout()
                    finish()
                    Toast.makeText(this@PaketActivity, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@PaketActivity, "Unauthorized", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 404){
                    Toast.makeText(this@PaketActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@PaketActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }

    override fun onBackPressed() {
        if(behavior.state == BottomSheetBehavior.STATE_EXPANDED){
            behavior.state = BottomSheetBehavior.STATE_COLLAPSED
        }else{
            super.onBackPressed()
        }
    }
}