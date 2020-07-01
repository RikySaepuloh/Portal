package com.saku.portalsatpam

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saku.portalsatpam.adapter.TamuAdapter
import com.saku.portalsatpam.apihelper.UtilsApi
import com.saku.portalsatpam.models.ModelTamu
import kotlinx.android.synthetic.main.activity_pantau.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type

class PantauActivity : AppCompatActivity() {
    var preferences  = Preferences()
    private var data : ArrayList<ModelTamu> = ArrayList()
    private lateinit var myadapter : TamuAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pantau)
        preferences.setPreferences(this)
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerview.layoutManager = layoutManager
        refreshLayout.setOnRefreshListener {
            initData()
        }
        initData()
        back.setOnClickListener {
            super.onBackPressed()
        }
    }

    private fun initData() {
        refreshLayout.isRefreshing = true
        val apiservice = UtilsApi().getAPIService(this@PantauActivity)
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
                                TamuAdapter(
                                    datapaket
                                )
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
                        Toast.makeText(this@PantauActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        refreshLayout.isRefreshing = false
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@PantauActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false
                } else if(response.code() == 401){
                    val intent = Intent(this@PantauActivity, LoginActivity::class.java)
                    startActivity(intent)
                    preferences.preferencesLogout()
                    finish()
                    Toast.makeText(this@PantauActivity, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@PantauActivity, "Unauthorized", Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false
                } else if(response.code() == 404){
                    Toast.makeText(this@PantauActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                    refreshLayout.isRefreshing = false
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@PantauActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
                refreshLayout.isRefreshing = false
            }
        })
    }

}
