package com.saku.portalsatpam

import `in`.aabhasjindal.otptextview.OTPListener
import `in`.aabhasjindal.otptextview.OtpTextView
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.saku.portalsatpam.apihelper.UtilsApi
import com.saku.portalsatpam.models.ModelTamu
import kotlinx.android.synthetic.main.activity_input.*
import kotlinx.android.synthetic.main.popup_ktp.*
import okhttp3.ResponseBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.reflect.Type


class InputActivity : AppCompatActivity() {
    var preferences  = Preferences()
    private var otpTextView: OtpTextView? = null
    private var otpTextView2: OtpTextView? = null
    var qrcodeValue : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        preferences.setPreferences(this)
        otpTextView = findViewById(R.id.et_qrcode1)
        otpTextView2 = findViewById(R.id.et_qrcode2)
        otpTextView?.requestFocusOTP()
        otpTextView?.otpListener = object : OTPListener {
            override fun onInteractionListener() {

            }

            override fun onOTPComplete(otp: String) {
                otpTextView2?.requestFocusOTP()
//                qrcodeValue = otp
            }
        }
//        val otpTextView2: OtpTextView = findViewById(R.id.et_qrcode2)
        otpTextView2?.otpListener = object : OTPListener {
            override fun onInteractionListener() {
            }

            override fun onOTPComplete(otp: String) {
                qrcodeValue=otpTextView?.otp+"."+otp
//                Toast.makeText(this@InputActivity, qrcodeValue, Toast.LENGTH_SHORT).show()
//                qrcodeValue+= ".$otp"
                qrcodeValue?.let { searchTamu(it) }
            }
        }

        back.setOnClickListener { super.onBackPressed() }
    }

    fun mypopup(imagelink:String, context : Context){
        val myDialog = Dialog(context)
        myDialog.setContentView(R.layout.popup_ktp)
//        Glide.with(this).load(imagelink).into(image_popup_ktp)
        myDialog.show()
    }

    private fun searchTamu(qrcode:String) {
        not_valid.visibility = View.INVISIBLE
        val apiservice = UtilsApi().getAPIService(this@InputActivity)
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
                            val datatamu: ArrayList<ModelTamu> =
                                gson.fromJson(obj.optString("data"), type)
                            for(i in 0 until datatamu.size){
//                                Log.e("hey:",datatamu[i].idTamu)
                                if (datatamu[i].idTamu.equals(qrcode)){
                                    not_valid.visibility = View.INVISIBLE
                                    val imm: InputMethodManager =
                                        getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
                                    val view: View? = currentFocus
                                    if (view != null) {
                                        imm.hideSoftInputFromWindow(view.windowToken, 0)
                                    }
                                    showDialog(this@InputActivity,datatamu[i].ktp.toString(),qrcode)

//                                    val myDialog = Dialog(this@InputActivity)
//                                    myDialog.setContentView(R.layout.popup_ktp)
//                                    Glide.with(this@InputActivity).load(datatamu[i].ktp).into(image_popup_ktp)
//                                    myDialog.show()
//                                    datatamu[i].ktp?.let { mypopup(it,this@InputActivity) }
//                                    Toast.makeText(this@InputActivity, datatamu[i].idTamu+" - $qrcode", Toast.LENGTH_LONG).show()
                                    break
                                }else{
                                    not_valid.visibility = View.VISIBLE
                                }
                            }
                        } catch (e: Exception) {
                        }
                    }else{
                        Toast.makeText(this@InputActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@InputActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 401){
                    val intent = Intent(this@InputActivity, LoginActivity::class.java)
                    startActivity(intent)
                    preferences.preferencesLogout()
                    finish()
                    Toast.makeText(this@InputActivity, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@InputActivity, "Unauthorized", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 404){
                    Toast.makeText(this@InputActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@InputActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun showDialog(activity: Activity?,ktp:String,id_tamu:String) {
        val dialog = Dialog(activity!!)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(true)
        dialog.setContentView(R.layout.popup_ktp)
        dialog.id_tamu.text = id_tamu
        Glide.with(this@InputActivity).load(ktp).into(dialog.image_popup_ktp)
        dialog.btn_proses.setOnClickListener { initData(id_tamu) }
        dialog.show()
    }

    private fun initData(qrcode :String) {
        val apiservice = UtilsApi().getAPIService(this@InputActivity)
        apiservice?.tamuKeluar(qrcode)?.enqueue(object : Callback<ResponseBody?> {
            override fun onResponse(
                call: Call<ResponseBody?>,
                response: Response<ResponseBody?>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        try {
                            val obj = JSONObject(response.body()!!.string())
                            val message = obj.optString("message")
                            val noUrut = obj.optString("no_urut")
                            Toast.makeText(this@InputActivity, message, Toast.LENGTH_SHORT).show()
                            val intent = Intent(this@InputActivity,SelesaiKeluarActivity::class.java)
                            intent.putExtra("no_urut",noUrut)
                            startActivity(intent)
                        } catch (e: Exception) {
                        }
                    }else{
                        Toast.makeText(this@InputActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                    }
                } else if(response.code() == 422) {
                    Toast.makeText(this@InputActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 401){
                    val intent = Intent(this@InputActivity, LoginActivity::class.java)
                    startActivity(intent)
                    preferences.preferencesLogout()
                    finish()
                    Toast.makeText(this@InputActivity, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 403){
                    Toast.makeText(this@InputActivity, "Unauthorized", Toast.LENGTH_SHORT).show()
                } else if(response.code() == 404){
                    Toast.makeText(this@InputActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
                Toast.makeText(this@InputActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
            }
        })
    }


//    private fun initData(qrcode :String) {
//        val apiservice = UtilsApi().getAPIService(this@InputActivity)
//        apiservice?.tamuKeluar(qrcode)?.enqueue(object : Callback<ResponseBody?> {
//            override fun onResponse(
//                call: Call<ResponseBody?>,
//                response: Response<ResponseBody?>
//            ) {
//                if (response.isSuccessful) {
//                    if (response.body() != null) {
//                        try {
//                            val obj = JSONObject(response.body()!!.string())
//                            val message = obj.optString("message")
//                            val noUrut = obj.optString("no_urut")
//                            Toast.makeText(this@InputActivity, message, Toast.LENGTH_SHORT).show()
//                            val intent = Intent(this@InputActivity,SelesaiKeluarActivity::class.java)
//                            intent.putExtra("no_urut",noUrut)
//                            startActivity(intent)
//                        } catch (e: Exception) {
//                        }
//                    }else{
//                        Toast.makeText(this@InputActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
//                    }
//                } else if(response.code() == 422) {
//                    Toast.makeText(this@InputActivity, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
//                } else if(response.code() == 401){
//                    val intent = Intent(this@InputActivity, LoginActivity::class.java)
//                    startActivity(intent)
//                    preferences.preferencesLogout()
//                    finish()
//                    Toast.makeText(this@InputActivity, "Sesi telah berakhir, silahkan login kembali", Toast.LENGTH_SHORT).show()
//                } else if(response.code() == 403){
//                    Toast.makeText(this@InputActivity, "Unauthorized", Toast.LENGTH_SHORT).show()
//                } else if(response.code() == 404){
//                    Toast.makeText(this@InputActivity, "Terjadi kesalahan server", Toast.LENGTH_SHORT).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseBody?>, t: Throwable) {
//                Toast.makeText(this@InputActivity, "Koneksi Bermasalah", Toast.LENGTH_SHORT).show()
//            }
//        })
//    }

}
