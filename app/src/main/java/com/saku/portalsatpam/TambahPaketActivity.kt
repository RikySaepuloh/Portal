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
import androidx.appcompat.widget.SearchView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saku.portalsatpam.adapter.PenghuniPaketAdapter
import com.saku.portalsatpam.adapter.RumahAdapter
import com.saku.portalsatpam.apihelper.UtilsApi
import com.saku.portalsatpam.models.ModelPenghuni
import com.saku.portalsatpam.models.ModelRumah
import kotlinx.android.synthetic.main.activity_tambah_paket.*
import kotlinx.android.synthetic.main.bottom_sheet_list_penghuni.*
import kotlinx.android.synthetic.main.bottom_sheet_list_tujuan.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.lang.reflect.Type

class TambahPaketActivity : AppCompatActivity() {
    lateinit var behaviorTujuan : BottomSheetBehavior<View>
    lateinit var behaviorPenghuni : BottomSheetBehavior<View>
    var namefile :String? = null
    var imagefile:String? = null
    var noRumah:String? = null
    var blok:String? = null
    var kodeRumah:String? = null
    var namapenghuni:String? = null
    var nikpenghuni:String? = null
    var preferences  = Preferences()
    private lateinit var rumahadapter : RumahAdapter
    private lateinit var penghuniPaketAdapter: PenghuniPaketAdapter

    private val mMessageReceiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent) {
            if(intent.hasExtra("imagefile")){
                namefile = intent.getStringExtra("namefile")
                imagefile = intent.getStringExtra("imagefile")
                addimage.text = namefile
//                Toast.makeText(this@TambahPaketActivity,imagefile,Toast.LENGTH_LONG).show()
                Glide.with(this@TambahPaketActivity).load(imagefile).into(image)
            }
            if(intent.hasExtra("kode_rumah")){
                kodeRumah = intent.getStringExtra("kode_rumah")
                noRumah = intent.getStringExtra("no_rumah")
                blok = intent.getStringExtra("blok")
                bs_tujuan.text = kodeRumah
                behaviorTujuan.state = BottomSheetBehavior.STATE_COLLAPSED
                initPenghuni(kodeRumah!!)
                bs_penghuni.isEnabled = true
                Handler().postDelayed({ // Do something after 5s = 5000ms
                    behaviorPenghuni.state = BottomSheetBehavior.STATE_EXPANDED
                }, 500)
            }
            if(intent.hasExtra("nama")){
                namapenghuni = intent.getStringExtra("nama")
                nikpenghuni = intent.getStringExtra("nik")
                bs_penghuni.text = namapenghuni
                behaviorPenghuni.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
    }

//    override fun onResume() {
//        super.onResume()
//        LocalBroadcastManager.getInstance(this@TambahPaketActivity)
//            .registerReceiver(mMessageReceiver, IntentFilter("image_intent"))
//    }
//
//    override fun onPause() {
//        super.onPause()
//        LocalBroadcastManager.getInstance(this@TambahPaketActivity)
//            .unregisterReceiver(mMessageReceiver)
//    }

    override fun onDestroy() {
        super.onDestroy()
        LocalBroadcastManager.getInstance(this@TambahPaketActivity)
            .unregisterReceiver(mMessageReceiver)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_paket)
        preferences.setPreferences(this)
        LocalBroadcastManager.getInstance(this)
            .registerReceiver(mMessageReceiver, IntentFilter("image_intent"))
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerview_tujuan.layoutManager = layoutManager
        val layoutManager2: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerview_penghuni.layoutManager = layoutManager2
        initTujuan()

        searchTujuan(searchview_tujuan)
        searchPenghuni(searchview_penghuni)

        back.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            if(behaviorTujuan.state == BottomSheetBehavior.STATE_EXPANDED||behaviorPenghuni.state == BottomSheetBehavior.STATE_EXPANDED){
                behaviorTujuan.state = BottomSheetBehavior.STATE_COLLAPSED
                behaviorPenghuni.state = BottomSheetBehavior.STATE_COLLAPSED
                overlay.visibility = View.GONE
            }else{
                super.onBackPressed()
            }
        }

        image.setOnClickListener {
            val intent = Intent(this@TambahPaketActivity,IdentitasActivity::class.java)
            intent.putExtra("paket",true)
            startActivity(intent)
        }

        behaviorTujuan = BottomSheetBehavior.from(bottom_sheet_tujuan)
        behaviorPenghuni = BottomSheetBehavior.from(bottom_sheet_penghuni)

        bs_tujuan.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            if(behaviorTujuan.state != BottomSheetBehavior.STATE_EXPANDED){
                behaviorTujuan.state = BottomSheetBehavior.STATE_EXPANDED
                overlay.visibility = View.VISIBLE
            }else{
                behaviorTujuan.state = BottomSheetBehavior.STATE_COLLAPSED
                overlay.visibility = View.GONE
            }
        }

        bs_penghuni.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            if(behaviorPenghuni.state != BottomSheetBehavior.STATE_EXPANDED){
                behaviorPenghuni.state = BottomSheetBehavior.STATE_EXPANDED
                overlay.visibility = View.VISIBLE
            }else{
                behaviorPenghuni.state = BottomSheetBehavior.STATE_COLLAPSED
                overlay.visibility = View.GONE
            }
        }


        behaviorPenghuni.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
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

        behaviorTujuan.addBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback() {
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

        simpan.setOnClickListener {
            //vibrate(longArrayOf(0, 350))
            if(bs_penghuni.text!="Pilih Penghuni"&&bs_tujuan.text!="Pilih Tujuan"&&imagefile!=null){
                addPaket()
            }
        }
    }

    private fun searchTujuan(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                rumahadapter.getFilter()?.filter(newText)
                return true
            }
        })
    }

    private fun searchPenghuni(searchView: SearchView) {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                penghuniPaketAdapter.getFilter()?.filter(newText)
                return true
            }
        })
    }

    private fun addPaket() {
        val file = File(imagefile!!)
        val fileReqBody: RequestBody = file.asRequestBody("multipart/form-data".toMediaTypeOrNull())
        val imagedata: MultipartBody.Part =
            MultipartBody.Part.createFormData("foto", file.name, fileReqBody)
        val apiservice = UtilsApi().getAPIService(this@TambahPaketActivity)
        apiservice?.addPaket(imagedata,toRequestBody(noRumah!!),toRequestBody(blok!!),toRequestBody(namapenghuni!!),toRequestBody(nikpenghuni!!))?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        try {
//                            val obj = JSONObject(response.body()!!.string())
                            val intent = Intent(this@TambahPaketActivity,PaketSuccessActivity::class.java)
                            startActivity(intent)
                            finishAffinity()
                        } catch (e: Exception) {
                            
                        }
                    }else{
                        Toast.makeText(this@TambahPaketActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@TambahPaketActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 401){
                    val intent = Intent(this@TambahPaketActivity, LoginActivity::class.java)
                    startActivity(intent)
                    preferences.preferencesLogout()
                    finish()
                    Toast.makeText(this@TambahPaketActivity, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@TambahPaketActivity, "Unauthorized", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 404){
                    Toast.makeText(this@TambahPaketActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@TambahPaketActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun toRequestBody(value: String): RequestBody {
        return value.toRequestBody("text/plain".toMediaTypeOrNull())
    }

    private fun initPenghuni(kode_rumah:String) {
        val apiservice = UtilsApi().getAPIService(this@TambahPaketActivity)
        kode_rumah.let {
            apiservice?.warga(it)?.enqueue(object : Callback<ResponseBody?> {
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
                                    TypeToken<ArrayList<ModelPenghuni?>?>() {}.type
                                val datapenghuni: ArrayList<ModelPenghuni> =
                                    gson.fromJson(obj.optString("data"), type)
    //                                adapter =
    //                                    PenghuniAdapter(
    //                                        datapenghuni,penghunirumah
    //                                    )
                                penghuniPaketAdapter = PenghuniPaketAdapter(datapenghuni)
                                if(obj.optString("status") == "false"){
                                    empty_view.visibility=View.VISIBLE
                                    recyclerview_penghuni.visibility=View.GONE
                                }else{
                                    empty_view.visibility=View.GONE
                                    recyclerview_penghuni.visibility=View.VISIBLE
                                }
//                                penghuniPaketAdapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
//                                    override fun onChanged() {
//                                        super.onChanged()
//                                        checkEmpty()
//                                    }
//
//                                    override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
//                                        super.onItemRangeInserted(positionStart, itemCount)
//                                        checkEmpty()
//                                    }
//
//                                    override fun onItemRangeRemoved(positionStart: Int, itemCount: Int) {
//                                        super.onItemRangeRemoved(positionStart, itemCount)
//                                        checkEmpty()
//                                    }
//
//                                    fun checkEmpty() {
//                                        if(penghuniPaketAdapter.itemCount==0){
//                                            empty_view.visibility=View.VISIBLE
//                                            recyclerview_penghuni.visibility=View.GONE
//                                        }else{
//                                            empty_view.visibility=View.GONE
//                                            recyclerview_penghuni.visibility=View.VISIBLE
//                                        }
//
//                                    }
//                                })
                                recyclerview_penghuni.adapter = penghuniPaketAdapter
                            } catch (e: Exception) {
                                empty_view.visibility=View.VISIBLE
                                recyclerview_penghuni.visibility=View.GONE
                            }
                        }else{
                            Toast.makeText(this@TambahPaketActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    } else if(response.code() == 422) {
                        Toast.makeText(this@TambahPaketActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    } else if(response.code() == 401){
                        val intent = Intent(this@TambahPaketActivity, LoginActivity::class.java)
                        startActivity(intent)
                        preferences.preferencesLogout()
                        finish()
                        Toast.makeText(this@TambahPaketActivity, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                    } else if(response.code() == 403){
                        Toast.makeText(this@TambahPaketActivity, "Unauthorized", Toast.LENGTH_SHORT).show()
                    } else if(response.code() == 404){
                        Toast.makeText(this@TambahPaketActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                    Toast.makeText(this@TambahPaketActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }


    fun initTujuan() {
        val apiservice = UtilsApi().getAPIService(this@TambahPaketActivity)
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
                            val datarumah: ArrayList<ModelRumah> =
                                gson.fromJson(obj.optString("data"), type)
                            rumahadapter =
                                RumahAdapter(
                                    datarumah)
                            recyclerview_tujuan.adapter = rumahadapter
                        } catch (e: Exception) {
                            recyclerview_tujuan.visibility= View.GONE
                        }
                    }else{
                        Toast.makeText(this@TambahPaketActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@TambahPaketActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 401){
                    val intent = Intent(this@TambahPaketActivity, LoginActivity::class.java)
                    startActivity(intent)
                    preferences.preferencesLogout()
                    finish()
                    Toast.makeText(this@TambahPaketActivity, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@TambahPaketActivity, "Unauthorized", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 404){
                    Toast.makeText(this@TambahPaketActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@TambahPaketActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }



    override fun onBackPressed() {
        if(behaviorTujuan.state == BottomSheetBehavior.STATE_EXPANDED||behaviorPenghuni.state == BottomSheetBehavior.STATE_EXPANDED){
            behaviorTujuan.state = BottomSheetBehavior.STATE_COLLAPSED
            behaviorPenghuni.state = BottomSheetBehavior.STATE_COLLAPSED
            overlay.visibility = View.GONE
        }else{
            super.onBackPressed()
        }
    }
}
